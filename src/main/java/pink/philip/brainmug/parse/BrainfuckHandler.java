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
package pink.philip.brainmug.parse;

import pink.philip.brainmug.api.Program;
import pink.philip.brainmug.api.instructions.Instruction;
import pink.philip.brainmug.api.instructions.bf.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Create a brainfuck program from a stream of instructions.
 */
public class BrainfuckHandler implements TokenSequenceHandler<BrainfuckToken> {
    /**
     * The list of instructions.
     */
    private final Stack<List<Instruction>> stack = new Stack<>();

    @Override
    public void handleStart() {
        stack.push(new ArrayList<>());
    }

    @Override
    public void handleToken(BrainfuckToken token) {
        if (stack.isEmpty()) {
            throw new RuntimeException("Program not started correctly.");
        }
        List<Instruction> top = stack.peek();
        switch (token) {
            case LEFT:
                top.add(new PositionChangeInstruction(-1));
                break;
            case RIGHT:
                top.add(new PositionChangeInstruction(1));
                break;
            case DECREMENT:
                top.add(new ValueChangeInstruction((byte) -1));
                break;
            case INCREMENT:
                top.add(new ValueChangeInstruction((byte) 1));
                break;
            case READ:
                top.add(new ReadInstruction());
                break;
            case PRINT:
                top.add(new PrintInstruction());
                break;
            case LOOP_START:
                stack.push(new ArrayList<>());
                break;
            case LOOP_END:
                if (stack.size() <= 1) {
                    throw new RuntimeException("No loop started.");
                }
                List<Instruction> loop = stack.pop();
                stack.peek().add(
                        new LoopInstruction(loop.toArray(new Instruction[0])));
                break;
        }
    }

    @Override
    public void handleEnd() {
        if (stack.isEmpty()) {
            throw new RuntimeException("Program parser not initialized " +
                    "correctly.");
        } else if (stack.size() > 1) {
            throw new RuntimeException("Some loops were not closed.");
        }
    }

    /**
     * Get the final program.
     *
     * @return The program.
     */
    public Program getProgram() {
        if (stack.size() != 1) {
            throw new RuntimeException("Program not parsed correctly.");
        }
        List<Instruction> instructions = stack.peek();
        return new Program(instructions.toArray(new Instruction[0]));
    }
}
