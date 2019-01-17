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
package pink.philip.brainmug.testutils;

import pink.philip.brainmug.api.BrainmugContext;
import pink.philip.brainmug.api.instructions.Instruction;

/**
 * An instruction doing nothing.
 */
public class EmptyInstruction implements Instruction {
    @Override
    public void execute(BrainmugContext context) {
    }
}