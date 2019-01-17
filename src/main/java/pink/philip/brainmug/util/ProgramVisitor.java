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

/**
 * Handles visits of a {@link ProgramWalker} on some instruction types.
 *
 * @see ProgramWalker
 */
public interface ProgramVisitor {
    /**
     * Visit the start of a {@link CompositeInstruction}.
     * Components of this instruction will be visited afterwards.
     *
     * @param instruction The instruction.
     */
    void visitCompositeStart(CompositeInstruction instruction);

    /**
     * Visit the end of a {@link CompositeInstruction}.
     */
    void visitCompositeEnd();

    /**
     * Visit an instruction.
     *
     * @param instruction The instruction.
     */
    void visitInstruction(Instruction instruction);
}
