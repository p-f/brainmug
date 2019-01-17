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
package pink.philip.brainmug.util;

import pink.philip.brainmug.api.instructions.CompositeInstruction;
import pink.philip.brainmug.api.instructions.Instruction;

import java.util.Objects;

/**
 * Iterates over the structure of a program and executes functions of a
 * {@link ProgramVisitor} when certain kinds of instructions are reached.
 *
 * @see ProgramVisitor for kinds of instruction.
 */
public class ProgramWalker {
    /**
     * Start iteration of a program structure on an instruction.
     *
     * @param instruction The start instruction or program.
     * @param visitor The visitor.
     */
    public static void walk(Instruction instruction, ProgramVisitor visitor) {
        walk0(Objects.requireNonNull(instruction),
                Objects.requireNonNull(visitor));
    }

    /**
     * Execute the walker further.
     *
     * @param i The current instruction.
     * @param v The visitor.
     */
    private static void walk0(Instruction i, ProgramVisitor v) {
        if (i instanceof CompositeInstruction) {
            final CompositeInstruction ci = (CompositeInstruction) i;
            v.visitCompositeStart(ci);
            for (Instruction component : ci.getComponents()) {
                walk0(component, v);
            }
            v.visitCompositeEnd();
        } else {
            v.visitInstruction(i);
        }
    }
}
