package pink.philip.brainmug.api.instructions.bf;

import pink.philip.brainmug.api.BrainmugContext;

/**
 * Print the value of the current position in the memory as a character.
 */
public class PrintInstruction implements BrainfuckInstruction {

    @Override
    public void execute(BrainmugContext context) {
        context.getIO().print(context.getMemory().get());
    }
}
