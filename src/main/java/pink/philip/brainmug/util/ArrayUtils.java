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

/**
 * Provides some common functions for arrays.
 */
public final class ArrayUtils {
    /**
     * Checks if two arrays are equal. Two arrays are equals, if the
     * first {@code n} elements of both arrays are equals, where {@code n}
     * is the length of the shorter array and the latter elements of
     * the longer array are all {@code 0}.
     *
     * @param first  The first array.
     * @param second The second array.
     * @return True, iff both array are considered equal.
     */
    public static boolean equalsByData(byte[] first, byte[] second) {
        // For convenience: Make sure the first array is the shorter one.
        if (first.length > second.length) {
            return equalsByData(second, first);
        }
        final int shorterLength = first.length;
        for (int i = 0; i < shorterLength; i++) {
            if (first[i] != second[i]) {
                return false;
            }
        }
        for (int i = shorterLength; i < second.length; i++) {
            if (second[i] != 0) {
                return false;
            }
        }
        return true;
    }
}
