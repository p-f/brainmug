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

import pink.philip.brainmug.api.Memory;
import pink.philip.brainmug.util.ArrayUtils;

import java.util.Arrays;
import java.util.Objects;

/**
 * A Memory implementation using a byte array.
 */
public class ArrayBasedMemory implements Memory {
    /**
     * The initial size of the band.
     */
    public static final int INITIAL_SIZE = 2048;

    /**
     * The size increment.
     */
    public static final int SIZE_INCREMENT = 1024;

    /**
     * The actual byte array.
     */
    private byte[] data;

    /**
     * The position on the band/array.
     */
    private int position;

    /**
     * Constructor, initialize with an empty band at position 0.
     */
    public ArrayBasedMemory() {
        data = new byte[INITIAL_SIZE];
        position = 0;
    }

    /**
     * Construction, initialize the band with some contents at position 0.
     *
     * @param initial The inital contents.
     */
    public ArrayBasedMemory(byte[] initial) {
        data = Objects.requireNonNull(initial);
        position = 0;
    }

    @Override
    public byte get() {
        return data[position];
    }

    @Override
    public void rightBy(int steps) {
        position += steps;
        if (position > data.length) {
            final int newLength = SIZE_INCREMENT *
                    (Math.floorDiv(position, SIZE_INCREMENT) + 1);
            if (position >= newLength) {
                throw new IllegalStateException(
                        "Failed to calculate new memory size.");
            }
            data = Arrays.copyOf(data, newLength);
        } else if (position < 0) {
            position = 0;
        }
    }

    @Override
    public void decrementBy(byte delta) {
        data[position] -= delta;
    }

    @Override
    public void incrementBy(byte delta) {
        data[position] += delta;
    }

    @Override
    public void set(byte value) {
        data[position] = value;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof ArrayBasedMemory)) {
            return false;
        }
        ArrayBasedMemory other = (ArrayBasedMemory) obj;
        return ArrayUtils.equalsByData(this.data, other.data);
    }
}
