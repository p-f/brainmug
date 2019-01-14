package pink.philip.brainmug.api;

/**
 * Provides a way to output or to read a byte.
 */
public interface RuntimeIO {
    /**
     * Print a byte.
     *
     * @param data The byte.
     */
    void print(byte data);

    /**
     * Read a byte.
     *
     * @return The byte.
     */
    byte read();
}
