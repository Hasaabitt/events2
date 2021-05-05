package net.theprism.events2;

import java.lang.reflect.Method;
import java.util.HashMap;

/**
 * EventSender is a version of Sender meant for sending classes that extend {@link Event}. Event has the ability to cancel events.
 *
 * @author Hasaabitt
 * @see Event
 * @see Sender
 */
public class EventSender extends Sender {
    private final HashMap<Class<?>, EventMethodStore> methodMap;

    /**
     * Creates a default event sender.
     */
    public EventSender() {
        this("", true);
    }

    /**
     * Creates an event sender with a name. Receivers without a matching source will not receive the event.
     *
     * @param senderName sender name
     */
    public EventSender(String senderName) {
        this(senderName, false);
    }

    /**
     * Creates an event sender with a name.
     *
     * @param senderName  sender name
     * @param allowGlobal if true then receivers of the default sender names will also receive from this sender
     */
    public EventSender(String senderName, boolean allowGlobal) {
        super(senderName, allowGlobal);
        this.methodMap = new HashMap<>();
    }

    /**
     * Add annotated methods within the class.
     *
     * @param clazz class containing methods
     */
    @Override
    public void addReceiverMethods(Class<?> clazz) {
        for (Method m : clazz.getDeclaredMethods()) {
            if (m.getParameterCount() == 0 || !m.isAnnotationPresent(Receiver.class)) continue;
            if (this.methodMap.containsKey(m.getParameterTypes()[0])) {
                EventMethodStore store = this.methodMap.get(m.getParameterTypes()[0]);
                store.addMethod(m);
            } else {
                EventMethodStore store = new EventMethodStore(this.senderName, this.allowGlobal);
                String[] sources = m.getAnnotation(Receiver.class).source();
                for (String source : sources) {
                    if (source.equals(this.senderName) || (this.allowGlobal && source.isEmpty())) {
                        store.addMethod(m);
                        this.methodMap.put(m.getParameterTypes()[0], store);
                    }
                }
            }
        }
        methodMap.forEach((k, v) -> {
            v.sort();
        });
    }

    /**
     * Remove receivers from sender.
     *
     * @param clazz class containing receivers
     */
    @Override
    public void removeReceiverMethods(Class<?> clazz) {
        for (Method m : clazz.getDeclaredMethods()) {
            if (m.getParameterCount() == 0 || !m.isAnnotationPresent(Receiver.class) || !methodMap.containsKey(m.getParameterTypes()[0])) {
                continue;
            }
            Class<?> pt = m.getParameterTypes()[0];
            methodMap.get(pt).removeMethod(m);
            if (methodMap.get(pt).getMethodCount() == 0) {
                methodMap.remove(pt);
            }
        }
    }

    /**
     * Remove all registered receivers.
     */
    @Override
    public void removeAllReceivers() {
        methodMap.clear();
    }

    /**
     * Returns an array of the methods held.
     *
     * @param type method parameter type to search for
     * @return array of methods that will receive the supplied type
     */
    @Override
    public Method[] getMethods(Class<?> type) {
        if (!methodMap.containsKey(type)) return new Method[0];
        return methodMap.get(type).getReceivers();
    }

    /**
     * Send an event.
     *
     * @param event event to send
     * @throws NullPointerException thrown if null is provided
     * @see Event
     */
    public void send(Event event) {
        if (event == null) throw new NullPointerException("Event is null");
        if (!this.methodMap.containsKey(event.getClass()) || this.methodMap.get(event.getClass()) == null) return;
        EventMethodStore store = this.methodMap.get(event.getClass());
        store.invoke(event);
    }

    /**
     * Send an event, Overrides AbstractSender#send(Object object)
     *
     * @param event event to send
     * @throws IllegalArgumentException thrown if not an event
     * @see Event
     */
    @Override
    public void send(Object event) {
        if (!(event instanceof Event)) throw new IllegalArgumentException("Not an event");
        send((Event) event);
    }

    @Override
    public String toString() {
        return "EventSender{" +
                "methodMap=" + this.methodMap +
                ", senderName='" + this.senderName + '\'' +
                ", allowGlobal=" + this.allowGlobal +
                '}';
    }
}
