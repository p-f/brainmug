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
package pink.philip.brainmug.runtime.impl.io;

import pink.philip.brainmug.api.RuntimeIO;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.Reader;

/**
 * An IO implemention using the {@link System#in I}{@link System#out O}.
 */
public class SystemIO implements RuntimeIO {
    /**
     * The systems output stream.
     */
    private final PrintStream out;

    /**
     * A reader reading the systems input stream.
     */
    private final Reader in;

    /**
     * Constructor.
     */
    public SystemIO() {
        out = System.out;
        in = new InputStreamReader(System.in);
    }

    @Override
    public void print(byte data) {
        out.print((char) data);
    }

    @Override
    public byte read() {
        try {
            return (byte) (in.read() & 0xFF);
        } catch (IOException e) {
            return 0;
        }
    }
}
