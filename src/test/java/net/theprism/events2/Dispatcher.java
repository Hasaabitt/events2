package net.theprism.events2;

public class Dispatcher {
    public static void main(String[] args) {
        regularTest();
        eventTest();
    }

    public static void regularTest() {
        System.out.println("==== Normal Sender ====");
        final Sender sender = new Sender();
        sender.addReceiverMethods(TestEvents.class);
        System.out.println("-- Sending integer --");
        sender.send(21);
    }

    public static void eventTest() {
        System.out.println("==== Event Sender ====");
        System.out.println("-- Default event sender --");
        final EventSender sender = new EventSender();
        sender.addReceiverMethods(MethodStoreTest.class);
        System.out.println("Dispatching SomeEvent");
        sender.send(new SomeEvent(100));
        System.out.println("Dispatching AnotherEvent");
        sender.send(new AnotherEvent(200));
        System.out.println();

        System.out.println("-- Named event sender --");
        final EventSender nonGlobal = new EventSender("NG");
        nonGlobal.addReceiverMethods(MethodStoreTest.class);
        System.out.println("Dispatching SomeEvent");
        nonGlobal.send(new SomeEvent(300));
        System.out.println("Dispatching AnotherEvent");
        nonGlobal.send(new AnotherEvent(400));
        System.out.println("(There should be none)");
        System.out.println();

        System.out.println("-- Named event sender with default access --");
        final EventSender nonGlobalWithInherit = new EventSender("NG", true);
        nonGlobalWithInherit.addReceiverMethods(MethodStoreTest.class);
        System.out.println("Dispatching SomeEvent");
        nonGlobalWithInherit.send(new SomeEvent(500));
        System.out.println("Dispatching AnotherEvent");
        nonGlobalWithInherit.send(new AnotherEvent(600));
        System.out.println();
    }
}