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

import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;
import pink.philip.brainmug.api.RuntimeIO;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.Objects;

/**
 * IO implementation based on JLine3.
 */
public class JLineIO implements RuntimeIO {
    /**
     * The terminal.
     */
    private final Terminal terminal;

    /**
     * A reader for accessing the terminal input.
     */
    private final Reader terminalInput;

    /**
     * A writer for accessing the terminal output.
     */
    private final Writer terminalOutput;

    /**
     * Setup this implementation using a default configuration.
     */
    public JLineIO() {
        this(defaultTerminal());
    }

    /**
     * Setup this implementation.
     *
     * @param terminal The terminal to use.
     */
    public JLineIO(Terminal terminal) {
        this.terminal = Objects.requireNonNull(terminal);
        terminalInput = terminal.reader();
        terminalOutput = terminal.writer();
    }

    @Override
    public void print(byte data) {
        try {
            terminalOutput.write(data);
            terminalOutput.flush();
        } catch (IOException e) {
            throw new RuntimeException("Failed to print.", e);
        }
    }

    @Override
    public byte read() {
        try {
            return (byte) terminalInput.read();
        } catch (IOException e) {
            throw new RuntimeException("Failed to read.", e);
        }
    }

    /**
     * Get a JLine {@link Terminal} with a preferred configuration.
     *
     * @return The terminal.
     */
    private static Terminal defaultTerminal() {
        try {
            Terminal t = TerminalBuilder.terminal();
            t.echo(false);
            t.enterRawMode();
            return t;
        } catch (IOException e) {
            throw new UnsupportedOperationException("Failed to get terminal.",
                    e);
        }
    }

    @Override
    public void close() throws Exception {
        terminal.close();
    }
}
