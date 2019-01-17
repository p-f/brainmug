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
package pink.philip.brainmug.compiler.bf;

import pink.philip.brainmug.api.Program;
import pink.philip.brainmug.api.instructions.Instruction;
import pink.philip.brainmug.api.instructions.bf.LoopInstruction;
import pink.philip.brainmug.api.instructions.bf.PositionChangeInstruction;
import pink.philip.brainmug.api.instructions.bf.ValueChangeInstruction;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * A builder used to create brainfuck {@link Program programs.}
 */
public class BrainfuckBuilder {
    /**
     * The current instructions.
     * The top is a list of instructions at the current scope (a loop or the
     * main program).
     */
    private Stack<List<Instruction>> instructions;

    /**
     * Initialize this builder.
     */
    public BrainfuckBuilder() {
        instructions = new Stack<>();
        instructions.push(new ArrayList<>());
    }

    /**
     * Add a decrement instruction.
     *
     * @return This builder.
     */
    public BrainfuckBuilder decrement() {
        instructions.peek().add(new ValueChangeInstruction((byte) -1));
        return this;
    }

    /**
     * Add an increment instruction.
     *
     * @return This builder.
     */
    public BrainfuckBuilder increment() {
        instructions.peek().add(new ValueChangeInstruction((byte) 1));
        return this;
    }

    /**
     * Add a {@code <} instruction.
     *
     * @return This builder.
     */
    public BrainfuckBuilder left() {
        instructions.peek().add(new PositionChangeInstruction(-1));
        return this;
    }

    /**
     * Add a {@code >} instruction.
     *
     * @return This builder.
     */
    public BrainfuckBuilder right() {
        instructions.peek().add(new PositionChangeInstruction(1));
        return this;
    }

    /**
     * Start a new loop.
     *
     * @return This builder.
     */
    public BrainfuckBuilder startLoop() {
        instructions.push(new ArrayList<>());
        return this;
    }

    /**
     * End a loop.
     *
     * @return This builder.
     * @throws IllegalStateException when no loop was started before.
     */
    public BrainfuckBuilder endLoop() {
        if (instructions.size() > 1) {
            List<Instruction> body = instructions.pop();
            instructions.peek().add(
                    new LoopInstruction(body.toArray(new Instruction[0])));
        } else {
            throw new IllegalStateException("No loop started.");
        }
        return this;
    }

    /**
     * Add a custom instruction.
     *
     * @param instruction The instruction.
     * @return This builder.
     */
    public BrainfuckBuilder addCustom(Instruction instruction) {
        instructions.peek().add(instruction);
        return this;
    }

    /**
     * Build the final program.
     *
     * @return The program.
     * @throws IllegalStateException when loops are not correctly opened or
     *                               closed.
     */
    public Program build() {
        if (instructions.size() != 1) {
            throw new IllegalStateException("Failed to build." +
                    "Unexpected depth: " + instructions.size());
        }
        return new Program(instructions.peek().toArray(new Instruction[0]));
    }
}
