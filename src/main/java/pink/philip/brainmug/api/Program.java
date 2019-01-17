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
package pink.philip.brainmug.api;

import pink.philip.brainmug.api.instructions.CompositeInstruction;
import pink.philip.brainmug.api.instructions.Instruction;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * A program that can be executed using the brainmug runtime.
 */
public class Program implements Instruction, CompositeInstruction {

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

    @Override
    public List<Instruction> getComponents() {
        return Arrays.asList(instructions);
    }
}
