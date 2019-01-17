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
 * Changes the value of the memory at the current position by a certain value.
 */
public class ValueChangeInstruction implements BrainfuckInstruction {
    /**
     * The value
     */
    private final byte value;

    /**
     * Create a new operation changing a memory value.
     *
     * @param delta The delta to apply to the current value of the memory.
     */
    public ValueChangeInstruction(byte delta) {
        this.value = delta;
    }

    /**
     * Get the value delta.
     *
     * @return The value.
     */
    public byte getValueDelta() {
        return value;
    }

    @Override
    public void execute(BrainmugContext context) {
        context.getMemory().incrementBy(value);
    }

    @Override
    public String toString() {
        if (value == 0) {
            return " ";
        }
        final boolean positive = value > 0;
        final byte absValue = positive ? value : (byte) -value;
        return (absValue > 1 ? "@" + absValue : "") +
                (positive ? "+" : "-");
    }
}
