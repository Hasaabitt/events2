package net.theprism.events2;

public class TestEvents {
    @Receiver
    public void receiverA(Integer integer) {
        System.out.println("Here is an integer as byte: " + integer.byteValue());
    }

    @Receiver
    public void onAnotherEvent(String string) {
        System.out.println("Here is a string, all caps: " + string.toUpperCase());
    }
}