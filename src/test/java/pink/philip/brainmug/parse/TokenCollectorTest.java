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
package pink.philip.brainmug.parse;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Test for {@link TokenCollector}.
 */
public class TokenCollectorTest {
    @Test(expected = NullPointerException.class)
    public void testConstructor() {
        TokenCollector<Integer> c = new TokenCollector<>(null);
    }

    @Test
    public void testHandler() {
        ArrayList<Integer> ints = new ArrayList<>();
        TokenCollector<Integer> c = new TokenCollector<>(ints);
        c.handleStart();
        List<Integer> expected = Arrays.asList(1, 2, 3, 4);
        expected.forEach(c::handleToken);
        c.handleEnd();
        c.verify();
        assertEquals(expected, ints);
    }

    @Test(expected = IllegalStateException.class)
    public void testCheckStart() {
        TokenCollector<Integer> c = new TokenCollector<>(new ArrayList<>());
        c.handleToken(1);
    }

    @Test(expected = IllegalStateException.class)
    public void testWithoutEnd() {
        TokenCollector<Integer> c = new TokenCollector<>(new ArrayList<>());
        c.handleStart();
        c.handleToken(1);
        c.verify();
    }

    @Test(expected = IllegalStateException.class)
    public void testOrder() {
        TokenCollector<Integer> c = new TokenCollector<>(new ArrayList<>());
        c.handleEnd();
    }

    @Test(expected = IllegalStateException.class)
    public void testStartTwice() {
        TokenCollector<Integer> c = new TokenCollector<>(new ArrayList<>());
        c.handleStart();
        c.handleStart();
    }

    @Test(expected = IllegalStateException.class)
    public void testEndTwice() {
        TokenCollector<Integer> c = new TokenCollector<>(new ArrayList<>());
        c.handleStart();
        c.handleEnd();
        c.handleEnd();
    }
}
