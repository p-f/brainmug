package pink.philip.brainmug.api.instructions.bf;

import pink.philip.brainmug.api.BrainmugContext;
import pink.philip.brainmug.api.instructions.Instruction;

import java.util.Objects;

/**
 * A loop. Executes some instructions while the byte at the current position
 * in the memory is not {@code 0}.
 */
public class LoopInstruction implements BrainfuckInstruction {

    /**
     * Instructions to be executed as part of this loop.
     */
    private final Instruction[] instructions;

    /**
     * Creates a new loop.
     *
     * @param instructions The body of the loop.
     */
    public LoopInstruction(Instruction... instructions) {
        this.instructions = Objects.requireNonNull(instructions);
    }

    @Override
    public void execute(BrainmugContext context) {
        final boolean emptyLoop = instructions.length == 0;
        // TODO: Exception when indefinite loop is detected.
        while (context.getMemory().isNotZero()) {
            for (Instruction instruction : instructions) {
                instruction.execute(context);
            }
        }
    }
}
