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
package pink.philip.brainmug.api.instructions;

import pink.philip.brainmug.api.BrainmugContext;

/**
 * An executable brainmug instruction.
 */
public interface Instruction {
    /**
     * Run this instruction in a certain context.
     *
     * @param context The runtime context.
     */
    void execute(BrainmugContext context);

    /**
     * Add the String representation of this instruction to a
     * {@link StringBuilder}.
     *
     * @param code The builder.
     */
    default void addTo(StringBuilder code) {
        code.append(toString());
    }
}
