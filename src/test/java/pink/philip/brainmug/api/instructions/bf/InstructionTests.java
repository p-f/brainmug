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

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pink.philip.brainmug.api.BrainmugContext;
import pink.philip.brainmug.api.Program;
import pink.philip.brainmug.api.exceptions.BrainmugRuntimeException;
import pink.philip.brainmug.api.instructions.Instruction;
import pink.philip.brainmug.runtime.impl.BrainfuckContext;
import pink.philip.brainmug.runtime.impl.memory.ArrayBasedMemory;
import pink.philip.brainmug.testutils.TestIO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

import static org.junit.Assert.assertEquals;

/**
 * Tests for various brainfuck instructions.
 */
public class InstructionTests {
    /**
     * A byte used as a test input value.
     */
    private static byte TESTVALUE = (byte) 't';

    /**
     * The test context.
     */
    private BrainmugContext context;

    /**
     * A supplier providing the character {@code t} as a byte.
     */
    private Supplier<Byte> inputByte = () -> TESTVALUE;

    /**
     * A list storing bytes that were supposed to be printed.
     */
    private List<Byte> outputBuffer;

    /**
     * The instruction to test.
     */
    private Instruction toTest;

    /**
     * Set up each test.
     */
    @Before
    public void setUp() {
        outputBuffer = new ArrayList<>();
        context = new BrainfuckContext(new ArrayBasedMemory(),
                new TestIO(inputByte, outputBuffer::add));
    }

    /**
     * Clean up after each run.
     */
    @After
    public void tearDown() {
        toTest = null;
    }

    /**
     * Test the loop instruction using a simple loop.
     */
    @Test
    public void testLoopInstruction() {
        // This loop is counting down from 5, should execute 5 times.
        context.getMemory().set((byte) 5);
        toTest = new LoopInstruction(new ValueChangeInstruction((byte) -1),
                new PrintInstruction()); // Used to count iterations.
        toTest.execute(context);
        assertEquals(5, outputBuffer.size());
        // No additional executions here.
        toTest.execute(context);
        assertEquals(5, outputBuffer.size());
        // Empty loops should be executed here.
        new LoopInstruction().execute(context);
    }

    /**
     * Test if the loop instruction detects empty loops.
     */
    @Test(expected = BrainmugRuntimeException.class)
    public void testLoopInstructionWithEmpty() {
        toTest = new LoopInstruction();
        context.getMemory().set((byte) 1);
        toTest.execute(context);
    }

    /**
     * Test the position change instruction.
     */
    @Test
    public void testPositionChangeInstruction() {
        toTest = new PositionChangeInstruction(2);
        toTest.execute(context);
        context.getMemory().set((byte) 3);
        toTest = new PositionChangeInstruction(-1);
        toTest.execute(context);
        context.getMemory().set((byte) 2);
        toTest = new PositionChangeInstruction(-1000);
        toTest.execute(context);
        context.getMemory().set((byte) 1);
        assertEquals(new ArrayBasedMemory(new byte[]{1, 2, 3}),
                context.getMemory());
    }

    /**
     * Test the print instruction.
     */
    @Test
    public void testPrintInstruction() {
        toTest = new PrintInstruction();
        toTest.execute(context);
        context.getMemory().set(TESTVALUE);
        toTest.execute(context);
        assertEquals(Arrays.asList((byte) 0, TESTVALUE), outputBuffer);
    }

    /**
     * Test the read instruction.
     */
    @Test
    public void testReadInstruction() {
        toTest = new ReadInstruction();
        toTest.execute(context);
        context.getMemory().rightBy(1);
        // Test if values are overwritten.
        context.getMemory().set((byte) -1);
        toTest.execute(context);
        assertEquals(new ArrayBasedMemory(new byte[]{TESTVALUE, TESTVALUE}),
                context.getMemory());
    }

    /**
     * Test the value change instruction.
     */
    @Test
    public void testValueChangeInstruction() {
        toTest = new ValueChangeInstruction((byte) 2);
        toTest.execute(context);
        context.getMemory().rightBy(1);
        toTest = new ValueChangeInstruction((byte) -3);
        toTest.execute(context);
        context.getMemory().rightBy(1);
        toTest = new ValueChangeInstruction((byte) 0);
        toTest.execute(context);
        assertEquals(new ArrayBasedMemory(new byte[]{2, -3, 0}),
                context.getMemory());
    }

    /**
     * Test {@link Instruction#toString()} for all instructions.
     */
    @Test
    public void testToStringMethods() {
        // -- Simple instructions. --
        // Position change.
        assertEquals(">", new PositionChangeInstruction(1).toString());
        assertEquals("<", new PositionChangeInstruction(-1).toString());
        assertEquals(" ", new PositionChangeInstruction(0).toString());
        assertEquals("@4>", new PositionChangeInstruction(4).toString());
        assertEquals("@8<", new PositionChangeInstruction(-8).toString());
        // Print.
        assertEquals(".", new PrintInstruction().toString());
        // Read.
        assertEquals(",", new ReadInstruction().toString());
        // Value change.
        assertEquals("+", new ValueChangeInstruction((byte) 1).toString());
        assertEquals("-", new ValueChangeInstruction((byte) -1).toString());
        assertEquals(" ", new ValueChangeInstruction((byte) 0).toString());
        assertEquals("@5+", new ValueChangeInstruction((byte) 5).toString());
        assertEquals("@3-", new ValueChangeInstruction((byte) -3).toString());
        // -- Composite instructions. --
        assertEquals("[]", new LoopInstruction().toString());
        assertEquals("[[]]", new LoopInstruction(
                new LoopInstruction()).toString());
        assertEquals("[@4> [- <]-].", new Program(
                new LoopInstruction(new PositionChangeInstruction(4),
                        new PositionChangeInstruction(0),
                        new LoopInstruction(
                                new ValueChangeInstruction((byte) -1),
                                new ValueChangeInstruction((byte) 0),
                                new PositionChangeInstruction(-1)),
                        new ValueChangeInstruction((byte) -1)),
                new PrintInstruction()).toString());
    }
}
