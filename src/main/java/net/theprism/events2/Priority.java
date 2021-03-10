package net.theprism.events2;

/**
 * Enum representing receiver priority. Receivers with a higher priority will run before receivers with a lower priority.
 *
 * @author Hasaabitt
 */
public enum Priority {
    /**
     * Priority 0
     */
    LOWEST(0),

    /**
     * Priority 1
     */
    LOW(1),

    /**
     * Priority 2
     */
    MEDIUM(2),

    /**
     * Priority 3
     */
    HIGH(3),

    /**
     * Priority 4
     */
    HIGHEST(4),

    /**
     * Priority 5 (Receivers should not use slow methods)
     */
    MONITOR(5);

    private final int value;

    Priority(int value) {
        this.value = value;
    }

    /**
     * Returns an integer representation of the priority.
     *
     * @return integer representation
     */
    public int asInt() {
        return value;
    }
}
