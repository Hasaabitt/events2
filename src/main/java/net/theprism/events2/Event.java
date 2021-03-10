package net.theprism.events2;

/**
 * Contains base methods for events.
 *
 * @author Hasaabitt
 */
public abstract class Event {
    private boolean cancelled;

    protected Event() {
        this.cancelled = false;
    }

    /**
     * Set whether the event should be cancelled.
     *
     * @param cancelled cancelled
     */
    public final void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    /**
     * Returns whether the event is cancelled.
     *
     * @return true if cancelled
     */
    public final boolean isCancelled() {
        return this.cancelled;
    }
}
