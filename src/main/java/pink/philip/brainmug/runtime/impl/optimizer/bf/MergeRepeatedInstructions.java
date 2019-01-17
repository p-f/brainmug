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
package pink.philip.brainmug.runtime.impl.optimizer.bf;

import pink.philip.brainmug.api.Program;
import pink.philip.brainmug.api.instructions.CompositeInstruction;
import pink.philip.brainmug.api.instructions.Instruction;
import pink.philip.brainmug.api.instructions.bf.LoopInstruction;
import pink.philip.brainmug.api.instructions.bf.PositionChangeInstruction;
import pink.philip.brainmug.api.instructions.bf.ValueChangeInstruction;
import pink.philip.brainmug.api.optimizer.Optimizer;
import pink.philip.brainmug.compiler.bf.BrainfuckBuilder;
import pink.philip.brainmug.util.ProgramVisitor;
import pink.philip.brainmug.util.ProgramWalker;

import java.util.Stack;

/**
 * Removes repeated instructions that can be replaced with a single
 * instruction.
 */
public class MergeRepeatedInstructions implements Optimizer {
    /**
     * The visitor detecting optimizable instructions.
     */
    private class Visitor implements ProgramVisitor {

        /**
         * The builder used to create an optimized program.
         */
        private BrainfuckBuilder builder = new BrainfuckBuilder();

        /**
         * Should the body at the current depth be optimized?
         */
        private Stack<Boolean> state = new Stack<>();

        /**
         * The last optimizable instruction to be added to the output program.
         * (The Queue.)
         */
        private Instruction last = null;

        @Override
        public void visitCompositeStart(CompositeInstruction instruction) {
            clearQueue();
            if (instruction instanceof LoopInstruction) {
                builder.startLoop();
                state.push(true);
            } else if (instruction instanceof Instruction) {
                builder.addCustom((Instruction) instruction);
                state.push(false);
            } else {
                throw new IllegalStateException("Unknown instruction type: " +
                        instruction);
            }
        }

        @Override
        public void visitCompositeEnd() {
            clearQueue();
            if (state.pop()) {
                builder.endLoop();
            }
        }

        @Override
        public void visitInstruction(Instruction instruction) {
            // We are inside an unknown composite instruction, we don't
            // want to change its components.
            if (!state.peek()) {
                return;
            }
            // Only consider supported instructions.
            if (!isSupported(instruction)) {
                builder.addCustom(instruction);
                return;
            }
            // Add an instruction to the queue.
            if (last == null) {
                last = instruction;
            } else if (last instanceof ValueChangeInstruction &&
                    instruction instanceof ValueChangeInstruction) {
                // Merge 2 +/- instructions.
                byte deltaQueue = ((ValueChangeInstruction) last)
                        .getValueDelta();
                byte deltaCurrent = ((ValueChangeInstruction) instruction)
                        .getValueDelta();
                byte newDelta = (byte) (deltaCurrent + deltaQueue);
                if (newDelta == 0) {
                    last = null;
                } else {
                    last = new ValueChangeInstruction(newDelta);
                }
            } else if (last instanceof PositionChangeInstruction &&
                    instruction instanceof PositionChangeInstruction) {
                // Merge 2 </> instructions.
                int deltaQueue = ((PositionChangeInstruction) last)
                        .getPositionDelta();
                int deltaCurrent = ((PositionChangeInstruction) instruction)
                        .getPositionDelta();
                int newDelta = deltaQueue + deltaCurrent;
                if (newDelta == 0) {
                    last = null;
                } else {
                    last = new PositionChangeInstruction(newDelta);
                }
            } else {
                // Replace instruction in queue.
                builder.addCustom(last);
                last = instruction;
            }
        }

        /**
         * Clear the queue and add all operations to the output program.
         */
        private void clearQueue() {
            if (last != null) {
                builder.addCustom(last);
                last = null;
            }
        }

        /**
         * Check if an instruction can be optimized.
         *
         * @param kind The instruction.
         * @return True, iff the type is supported.
         */
        private boolean isSupported(Instruction kind) {
            return kind instanceof ValueChangeInstruction ||
                    kind instanceof PositionChangeInstruction;
        }

        /**
         * Get the final program.
         *
         * @return The program.
         * @throws IllegalStateException from {@link BrainfuckBuilder#build()}.
         */
        private Program getFinalProgram() {
            return builder.build();
        }
    }

    @Override
    public Program execute(Program source) {
        Visitor visitor = new Visitor();
        ProgramWalker.walk(source, visitor);
        return visitor.getFinalProgram();
    }
}
