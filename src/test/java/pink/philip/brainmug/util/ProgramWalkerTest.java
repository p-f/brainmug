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

import org.junit.Before;
import org.junit.Test;
import pink.philip.brainmug.api.Program;
import pink.philip.brainmug.api.instructions.CompositeInstruction;
import pink.philip.brainmug.api.instructions.Instruction;
import pink.philip.brainmug.api.instructions.bf.LoopInstruction;
import pink.philip.brainmug.testutils.EmptyInstruction;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertArrayEquals;

/**
 * Test for the program walker.
 */
public class ProgramWalkerTest {
    /**
     * A dummy used to mark an end of a composite instruction.
     */
    private static final class End {
        @Override
        public boolean equals(Object obj) {
            // All markers all considered equal.
            return obj instanceof End;
        }
    }

    /**
     * A test visitor collecting instructions.
     */
    private static class TestVisitor implements ProgramVisitor {
        /**
         * A list of visited instructions, in order of visitation.
         */
        private List<Object> visited = new ArrayList<>();

        @Override
        public void visitCompositeStart(CompositeInstruction instruction) {
            visited.add(instruction);
        }

        @Override
        public void visitCompositeEnd() {
            visited.add(new End());
        }

        @Override
        public void visitInstruction(Instruction instruction) {
            visited.add(instruction);
        }

        /**
         * Get the list of visited instructions, in order of visitation.
         */
        public List<Object> getVisited() {
            return visited;
        }
    }

    /**
     * The visitor used for testing.
     */
    private TestVisitor visitor;

    /**
     * Initialize this test.
     */
    @Before
    public void setUp() {
        visitor = new TestVisitor();
    }

    /**
     * Test using one simple instruction.
     */
    @Test
    public void testSimple() {
        Instruction instruction = new EmptyInstruction();
        ProgramWalker.walk(instruction, visitor);
        Object[] visits = visitor.getVisited().toArray();
        assertArrayEquals(new Object[]{instruction}, visits);
    }

    /**
     * Test using a simple composite instruction.
     */
    @Test
    public void testComposite() {
        Instruction[] body = {new EmptyInstruction(), new EmptyInstruction()};
        Instruction loop = new LoopInstruction(body);
        ProgramWalker.walk(loop, visitor);
        Object[] visits = visitor.getVisited().toArray();
        assertArrayEquals(new Object[]{loop, body[0], body[1], new End()},
                visits);
    }

    /**
     * Test some more complex cases.
     */
    @Test
    public void testComplex() {
        Instruction start = new EmptyInstruction();
        Instruction[] body1 = {new EmptyInstruction(), new EmptyInstruction()};
        Instruction[] body2 = {new LoopInstruction(body1),
                new EmptyInstruction()};
        Instruction loop = new LoopInstruction(body2);
        Instruction end = new EmptyInstruction();
        Instruction test = new Program(start, loop, end);
        Object[] expected = {test, start, loop, body2[0], body1[0], body1[1],
                new End(), body2[1], new End(), end, new End()};
        ProgramWalker.walk(test, visitor);
        assertArrayEquals(expected, visitor.getVisited().toArray());
    }
}
