package pink.philip.brainmug.api.instructions.bf;

import pink.philip.brainmug.api.BrainmugContext;

/**
 * Changes to position of the memory pointer.
 */
public class PositionChangeInstruction implements BrainfuckInstruction {
    /**
     * The number of positions to change.
     */
    private final int value;

    /**
     * Creates a new instruction moving the memory pointer.
     *
     * @param positions The number of positions to move (to the right).
     */
    public PositionChangeInstruction(int positions) {
        this.value = positions;
    }

    @Override
    public void execute(BrainmugContext context) {
        context.getMemory().rightBy(value);
    }
}
