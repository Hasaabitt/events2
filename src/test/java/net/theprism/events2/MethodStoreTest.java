package net.theprism.events2;

public class MethodStoreTest {
    @Receiver
    public void onStuff(SomeEvent event) {
        System.out.println("Some Event: " + event.getValue());
    }

    @Receiver(source = {"NG"})
    public void onOtherStuff(SomeEvent event) {
        System.out.println("Some Event (Non-global): " + event.getValue());
    }

    @Receiver(ignoreCancelled = true)
    public void onAnotherStuff(AnotherEvent event) {
        System.out.println("Another Event (Default): " + event.getValue());
    }

    @Receiver(priority = Priority.HIGHEST)
    public void onYetAnotherStuff(AnotherEvent event) {
        event.setCancelled(true);
        System.out.println("Another Event (High): " + event.getValue());
    }
}
