package net.theprism.events2;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Hasaabitt
 */
class MethodStore {
    protected final List<Method> methods;
    protected final boolean allowGlobal;
    protected final String senderName;

    protected MethodStore(String senderName, boolean allowGlobal) {
        methods = new ArrayList<>();
        this.senderName = senderName;
        this.allowGlobal = allowGlobal;
    }

    protected void addMethod(Method method) {
        if (!method.isAnnotationPresent(Receiver.class)) return;
        String[] sources = method.getAnnotation(Receiver.class).source();
        for (String source : sources) {
            if (method.getParameterCount() == 0)
                continue;
            if (source.equals(senderName) || (allowGlobal && source.isEmpty())) {
                if (!this.methods.contains(method)) this.methods.add(method);
            }
        }
    }

    protected void sort() {
        this.methods.sort((m1, m2) -> Integer.compare(m2.getAnnotation(Receiver.class).priority().asInt(), m1.getAnnotation(Receiver.class).priority().asInt()));
    }

    protected void invoke(Object instance) {
        for (Method method : this.methods) {
            try {
                Object o = method.getDeclaringClass().newInstance();
                method.invoke(o, instance);
            } catch (IllegalAccessException | IllegalArgumentException | InstantiationException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

    protected Method[] getReceivers() {
        return methods.toArray(new Method[0]);
    }

    protected void removeMethod(Method m) {
        methods.remove(m);
    }

    protected int getMethodCount() {
        return methods.size();
    }

    @Override
    public String toString() {
        return "MethodStore{" +
                "methods=" + this.methods +
                ", senderName='" + this.senderName + '\'' +
                ", allowGlobal=" + this.allowGlobal +
                '}';
    }
}
