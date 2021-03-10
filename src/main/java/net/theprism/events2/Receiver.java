package net.theprism.events2;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Receivers can be defined with sources and a priority. The source parameter takes a string array containing the names of senders that the method can receive from. By default it will receive from the default sender alone. The priority parameter allows receivers to be ran in a specific order when there are multiple for one sender type.
 *
 * @author Hasaabitt
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface Receiver {
    /**
     * Names of senders that a receiver can receive from.
     *
     * @return sender names
     */
    String[] source() default {""};

    /**
     * Execution priority, default is Priority.MEDIUM.
     *
     * @return receiver priority
     */
    Priority priority() default Priority.MEDIUM;

    /**
     * Sets whether the reciever should ignore the event being cancelled
     *
     * @return true if it ignores cancellation
     */
    boolean ignoreCancelled() default false;
}
