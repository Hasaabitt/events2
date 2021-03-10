package net.theprism.events2;

import net.theprism.events2.Event;

public class SomeEvent extends Event {
    private final int value;

    SomeEvent(int value) {
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
