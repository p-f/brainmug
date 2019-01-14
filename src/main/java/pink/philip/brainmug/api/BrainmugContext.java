package pink.philip.brainmug.api;

/**
 * The context of a brainmug execution.
 * Used to access components of the runtime.
 */
public interface BrainmugContext {
    /**
     * Get the memory of the context.
     *
     * @return The memory implementation.
     */
    Memory getMemory();

    /**
     * Get the in- and output implementation of the context.
     *
     * @return The IO implementation.
     */
    RuntimeIO getIO();
}
