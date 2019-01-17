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

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static pink.philip.brainmug.util.ArrayUtils.equalsByData;

/**
 * Tests for {@link ArrayUtils}.
 */
public class ArrayUtilsTest {
    /**
     * Test a simple equals case.
     */
    @Test
    public void testEquals() {
        byte[] first = {1, 2, 3};
        byte[] second = {1, 2, 3};
        assertTrue(equalsByData(first, second));
        assertTrue(equalsByData(second, first));
    }

    /**
     * Test a case where two arrays of different size are considered equal.
     */
    @Test
    public void testEqualsWithDifferentSize() {
        byte[] first = {1, 2, 3};
        byte[] second = {1, 2, 3, 0, 0, 0};
        assertTrue(equalsByData(first, second));
        assertTrue(equalsByData(second, first));
    }

    /**
     * Test cases with empty arrays.
     */
    @Test
    public void testWithEmpty() {
        byte[] first = {};
        byte[] second = {0, 0, 0};
        assertTrue(equalsByData(first, first));
        assertTrue(equalsByData(first, second));
        assertTrue(equalsByData(second, first));
    }

    /**
     * Test a simple not equals case.
     */
    @Test
    public void testNotEquals() {
        byte[] first = {1, 2, 3};
        byte[] second = {1, 2, 4};
        assertFalse(equalsByData(first, second));
        assertFalse(equalsByData(second, first));
    }

    /**
     * Test a case where two arrays of different size are not considered equal.
     */
    @Test
    public void testNotEqualsWithDifferentSize() {
        byte[] first = {1, 2, 3};
        byte[] second = {1, 2, 3, 4};
        assertFalse(equalsByData(first, second));
        assertFalse(equalsByData(second, first));
    }

    /**
     * Test a cases with empty arrays (not equal).
     */
    @Test
    public void testNotEqualsWithEmpty() {
        byte[] first = {};
        byte[] second = {1, 2, 3};
        assertFalse(equalsByData(first, second));
        assertFalse(equalsByData(second, first));
    }
}
