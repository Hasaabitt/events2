package net.theprism.events2;

import net.theprism.events2.Event;

public class AnotherEvent extends Event {
    private final int value;

    AnotherEvent(int value) {
        this.value = value;
    }

    /**
     * Get event value
     *
     * @return value
     */
    public int getValue() {
        return value;
    }
}
