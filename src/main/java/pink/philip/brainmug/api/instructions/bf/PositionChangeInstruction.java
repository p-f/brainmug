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

/**
 * Changes to position of the memory pointer.
 */
public class PositionChangeInstruction implements BrainfuckInstruction {
    /**
     * The number of positions to change.
     */
    private final int value;

    /**
     * Creates a new instruction moving the memory pointer.
     *
     * @param positions The number of positions to move (to the right).
     */
    public PositionChangeInstruction(int positions) {
        this.value = positions;
    }

    /**
     * Get the position delta.
     *
     * @return The value.
     */
    public int getPositionDelta() {
        return value;
    }

    @Override
    public void execute(BrainmugContext context) {
        context.getMemory().rightBy(value);
    }

    @Override
    public String toString() {
        if (value == 0) {
            return " ";
        }
        final boolean positive = value > 0;
        final int absValue = positive ? value : -value;
        return (absValue > 1 ? "@" + absValue : "") +
                (positive ? ">" : "<");
    }
}
