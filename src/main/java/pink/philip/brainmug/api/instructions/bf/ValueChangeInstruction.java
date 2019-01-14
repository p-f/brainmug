package pink.philip.brainmug.api.instructions.bf;

import pink.philip.brainmug.api.BrainmugContext;

/**
 * Changes the value of the memory at the current position by a certain value.
 */
public class ValueChangeInstruction implements BrainfuckInstruction {
    /**
     * The value
     */
    private final byte value;

    /**
     * Create a new operation changing a memory value.
     *
     * @param delta The delta to apply to the current value of the memory.
     */
    public ValueChangeInstruction(byte delta) {
        this.value = delta;
    }

    @Override
    public void execute(BrainmugContext context) {
        context.getMemory().incrementBy(value);
    }
}
