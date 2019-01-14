package pink.philip.brainmug.api.instructions.bf;

import pink.philip.brainmug.api.BrainmugContext;

/**
 * Reads from the input and stores the value in the memory.
 */
public class ReadInstruction implements BrainfuckInstruction {

    @Override
    public void execute(BrainmugContext context) {
        context.getMemory().set(context.getIO().read());
    }
}
