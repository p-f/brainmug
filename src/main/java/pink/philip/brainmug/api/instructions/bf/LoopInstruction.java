/*
 * Copyright 2018-2019 Philip Fritzsche <p-f@users.noreply.github.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package pink.philip.brainmug.api.instructions.bf;

import pink.philip.brainmug.api.BrainmugContext;
import pink.philip.brainmug.api.exceptions.BrainmugRuntimeException;
import pink.philip.brainmug.api.instructions.CompositeInstruction;
import pink.philip.brainmug.api.instructions.Instruction;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * A loop. Executes some instructions while the byte at the current position
 * in the memory is not {@code 0}.
 */
public class LoopInstruction implements BrainfuckInstruction,
        CompositeInstruction {

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
        while (context.getMemory().isNotZero()) {
            if (emptyLoop) {
                throw new BrainmugRuntimeException(
                        "Stuck in an indefinite loop.");
            }
            for (Instruction instruction : instructions) {
                instruction.execute(context);
            }
        }
    }

    @Override
    public List<Instruction> getComponents() {
        return Arrays.asList(instructions);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder().append("[");
        for (Instruction component : instructions) {
            sb.append(component.toString());
        }
        return sb.append("]").toString();
    }
}
