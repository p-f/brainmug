package pink.philip.brainmug.api;

/**
 * Provides a memory, i.e. a band of bytes.
 */
public interface Memory {
    /**
     * Get the byte at the current position of the band.
     *
     * @return The byte.
     */
    byte get();

    /**
     * Go left by a number of steps.
     *
     * @param steps The number of steps.
     */
    void leftBy(int steps);

    /**
     * Go left by 1 step.
     */
    default void left() {
        leftBy(1);
    }

    /**
     * Go right by a number of steps.
     *
     * @param steps The number of steps.
     */
    void rightBy(int steps);

    /**
     * Subtract a value from the value at the current cell.
     *
     * @param delta The value to subtract.
     */
    void decrementBy(byte delta);

    /**
     * Decrement the value of the current cell by 1.
     */
    default void decrement() {
        decrementBy((byte) 1);
    }

    /**
     * Add a value to the value at the current cell.
     *
     * @param delta The value to add.
     */
    void incrementBy(byte delta);

    /**
     * Increment the value at the current cell by 1.
     */
    default void increment() {
        incrementBy((byte) 1);
    }

    /**
     * Check if the value of the current cell is not zero.
     *
     * @return True, if the value is not zero.
     */
    default boolean isNotZero() {
        return get() != 0;
    }

    /**
     * Set the value at the current position of the band.
     *
     * @param value The new value.
     */
    void set(byte value);
}
