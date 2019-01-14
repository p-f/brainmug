package pink.philip.brainmug.api.instructions;

import pink.philip.brainmug.api.BrainmugContext;

/**
 * An executable brainmug instruction.
 */
public interface Instruction {
    /**
     * Run this instruction in a certain context.
     *
     * @param context The runtime context.
     */
    void execute(BrainmugContext context);
}
