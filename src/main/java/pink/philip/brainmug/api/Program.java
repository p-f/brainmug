package pink.philip.brainmug.api;

import pink.philip.brainmug.api.instructions.Instruction;

import java.util.Objects;

/**
 * A programm that can be executed using the brainmug runtime.
 */
public class Program implements Instruction {

    /**
     * The instructions to be executed as part of this program.
     */
    private final Instruction[] instructions;

    /**
     * Creates a new program from a list of instructions.
     *
     * @param instructions The list of instructions.
     */
    public Program(Instruction... instructions) {
        this.instructions = Objects.requireNonNull(instructions);
    }

    @Override
    public void execute(BrainmugContext context) {
        for (Instruction instruction : instructions) {
            instruction.execute(context);
        }
    }
}
