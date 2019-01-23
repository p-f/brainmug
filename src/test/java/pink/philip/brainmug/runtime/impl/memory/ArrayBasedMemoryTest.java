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
package pink.philip.brainmug.runtime.impl.memory;

import org.junit.Test;
import pink.philip.brainmug.api.BrainmugContext;
import pink.philip.brainmug.api.Program;
import pink.philip.brainmug.api.instructions.bf.PositionChangeInstruction;
import pink.philip.brainmug.api.instructions.bf.ValueChangeInstruction;
import pink.philip.brainmug.runtime.impl.BrainfuckContext;
import pink.philip.brainmug.testutils.TestIO;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

/**
 * Test for {@link ArrayBasedMemory}.
 */
public class ArrayBasedMemoryTest {
    /**
     * Test if resizing works for larger steps.
     */
    @Test
    public void testLargeStep() {
        ArrayBasedMemory memory = new ArrayBasedMemory();
        BrainmugContext context = new BrainfuckContext(memory,
                new TestIO(() -> (byte) 0, a -> {
                }));
        byte testValue = (byte) 't';
        int testPosition = ArrayBasedMemory.INITIAL_SIZE +
                ArrayBasedMemory.SIZE_INCREMENT * 2 + 1;
        Program testProgram = new Program(
                new PositionChangeInstruction(testPosition),
                new ValueChangeInstruction(testValue));
        testProgram.execute(context);
        assertEquals(testValue, memory.get());
        byte[] expectedData = new byte[testPosition + 1];
        Arrays.fill(expectedData, (byte) 0);
        expectedData[testPosition] = testValue;
        assertEquals(new ArrayBasedMemory(expectedData), memory);
    }
}
