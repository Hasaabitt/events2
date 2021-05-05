package net.theprism.events2;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author Hasaabitt
 */
class EventMethodStore extends MethodStore {
    EventMethodStore(String senderName, boolean allowGlobal) {
        super(senderName, allowGlobal);
    }

    @Override
    protected void addMethod(Method method) {
        if (method.isAnnotationPresent(Receiver.class)) return;
        String[] sources = method.getAnnotation(Receiver.class).source();
        for (String source : sources) {
            if (method.getParameterCount() == 0 || !Event.class.isAssignableFrom(method.getParameterTypes()[0]))
                continue;
            if (source.equals(this.senderName) || (this.allowGlobal && source.isEmpty())) {
                if (!this.methods.contains(method)) this.methods.add(method);
            }
        }
        //sort();
    }

    @Override
    protected void invoke(Object object) {
        if (!(object instanceof Event)) throw new IllegalArgumentException("Not an event");
        invoke((Event) object);
    }

    protected void invoke(Event event) {
        for (Method method : this.methods) {
            if (event.isCancelled() && !method.getAnnotation(Receiver.class).ignoreCancelled()) continue;
            try {
                Object o = method.getDeclaringClass().newInstance();
                method.invoke(o, event);
            } catch (IllegalAccessException | IllegalArgumentException | InstantiationException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }
}
