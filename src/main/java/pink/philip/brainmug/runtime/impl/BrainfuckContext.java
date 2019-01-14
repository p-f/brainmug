package pink.philip.brainmug.runtime.impl;

import pink.philip.brainmug.api.BrainmugContext;
import pink.philip.brainmug.api.Memory;
import pink.philip.brainmug.api.RuntimeIO;

import java.util.Objects;

/**
 * The default context implementation.
 */
public class BrainfuckContext implements BrainmugContext {
    /**
     * The memory implementation.
     */
    private final Memory memoryImpl;

    /**
     * The IO implementation.
     */
    private final RuntimeIO ioImpl;

    /**
     * Create a new context.
     *
     * @param memoryImpl The memory implementation to use.
     * @param ioImpl The IO implementation to use.
     */
    public BrainfuckContext(Memory memoryImpl, RuntimeIO ioImpl) {
        this.memoryImpl = Objects.requireNonNull(memoryImpl);
        this.ioImpl = Objects.requireNonNull(ioImpl);
    }

    @Override
    public Memory getMemory() {
        return memoryImpl;
    }

    @Override
    public RuntimeIO getIO() {
        return ioImpl;
    }
}
