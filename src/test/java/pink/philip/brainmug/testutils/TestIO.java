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

import pink.philip.brainmug.api.RuntimeIO;

import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * An IO implementation used for testing.
 */
public class TestIO implements RuntimeIO {
    /**
     * A byte supplier used as a dummy input.
     */
    private Supplier<Byte> byteSupplier;

    /**
     * A byte consumer used as a dummy output.
     */
    private Consumer<Byte> byteConsumer;

    public TestIO(Supplier<Byte> byteSupplier, Consumer<Byte> byteConsumer) {
        this.byteSupplier = byteSupplier;
        this.byteConsumer = byteConsumer;
    }

    @Override
    public void print(byte data) {
        byteConsumer.accept(data);
    }

    @Override
    public byte read() {
        return byteSupplier.get();
    }
}
