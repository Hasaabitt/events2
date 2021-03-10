package net.theprism.events2;

import java.lang.reflect.Method;
import java.util.HashMap;

/**
 * Generic sender. Receivers with the same type parameter will receive from the sender. While this can be used similarly to {@link EventSender} it does not contain functionality like event cancelling.
 *
 * @author Hasaabitt
 */
public class Sender {
    protected final String senderName;
    protected final boolean allowGlobal;
    protected final HashMap<Class<?>, MethodStore> methodMap;

    /**
     * Creates a default sender.
     */
    public Sender() {
        this("", true);
    }

    /**
     * Creates a named default sender.
     *
     * @param senderName sender name
     */
    public Sender(String senderName) {
        this(senderName, false);
    }

    /**
     * Creates a named sender.
     *
     * @param senderName  sender name
     * @param allowGlobal allow access from default receiver
     */
    public Sender(String senderName, boolean allowGlobal) {
        this.senderName = senderName;
        this.allowGlobal = allowGlobal;
        methodMap = new HashMap<>();
    }

    /**
     * Add annotated methods from class.
     *
     * @param clazz class to search for annotated methods from
     * @see Receiver
     */
    public void addReceiverMethods(Class<?> clazz) {
        for (Method m : clazz.getDeclaredMethods()) {
            if (m.getParameterCount() == 0) continue;
            if (this.methodMap.containsKey(m.getParameterTypes()[0])) {
                MethodStore store = this.methodMap.get(m.getParameterTypes()[0]);
                store.addMethod(m);
            } else {
                MethodStore store = new MethodStore(senderName, allowGlobal);
                store.addMethod(m);
                this.methodMap.put(m.getParameterTypes()[0], store);
            }
        }
    }

    /**
     * Remove receivers from sender.
     *
     * @param clazz class containing receivers
     * @see Receiver
     */
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
     *
     * @see Receiver
     */
    public void removeAllReceivers() {
        methodMap.clear();
    }

    /**
     * Returns an array of the methods held by their type.
     *
     * @param type method parameter type to search for
     * @return array of methods that will receive the supplied type
     */
    public Method[] getMethods(Class<?> type) {
        if (!methodMap.containsKey(type)) return new Method[0];
        return methodMap.get(type).getReceivers();
    }

    /**
     * Send an object.
     *
     * @param object instance of an object to send
     * @throws NullPointerException thrown if null is provided
     */
    public void send(Object object) {
        if (object == null) throw new NullPointerException("Object is null");
        if (!this.methodMap.containsKey(object.getClass()) || this.methodMap.get(object.getClass()) == null) return;
        MethodStore store = this.methodMap.get(object.getClass());
        store.invoke(object);
    }

    /**
     * Retrieve name of sender.
     *
     * @return sender name
     */
    public String getSenderName() {
        return senderName;
    }

    /**
     * Returns whether receivers can receive from the default sender
     *
     * @return true if receivers without a source can receive from this sender
     */
    public boolean allowsDefault() {
        return allowGlobal;
    }

    @Override
    public String toString() {
        return "Sender{" +
                "methodMap=" + this.methodMap +
                ", senderName='" + this.senderName + '\'' +
                ", allowGlobal=" + this.allowGlobal +
                '}';
    }
}
