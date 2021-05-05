package net.theprism.events2;

public class TestEvents {
    @Receiver
    public void onEvent(Integer integer) {
        System.out.println(integer + " Medium");
    }

    @Receiver(priority = Priority.HIGHEST)
    public void onAnotherEvent(Integer integer) {
        System.out.println(integer + " Highest");
    }

    @Receiver(priority = Priority.LOWEST)
    public void onYetAnotherEvent(Integer integer) {
        System.out.println(integer + " Lowest");
    }
}