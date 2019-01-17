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
package pink.philip.brainmug.cli;

import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;
import pink.philip.brainmug.api.BrainmugContext;
import pink.philip.brainmug.api.Memory;
import pink.philip.brainmug.api.Program;
import pink.philip.brainmug.api.RuntimeIO;
import pink.philip.brainmug.parse.BrainfuckParser;
import pink.philip.brainmug.runtime.impl.BrainfuckContext;
import pink.philip.brainmug.runtime.impl.io.JLineIO;
import pink.philip.brainmug.runtime.impl.memory.ArrayBasedMemory;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * The main class, providing the CLI.
 */
public class Brainmug {

    /**
     * Main method.
     *
     * @param args The command line arguments.
     */
    public static void main(String[] args) throws Exception {
        if (args.length < 1) {
            System.err.println("Usage: INFILE");
            return;
        }
        Path path = Paths.get(args[0]);
        InputStream input = Files.newInputStream(path, StandardOpenOption.READ);
        Program program = new BrainfuckParser().parse(input);
        input.close();

        Terminal terminal = TerminalBuilder.terminal();
        terminal.echo(false);
        terminal.enterRawMode();
        RuntimeIO io = new JLineIO(terminal);
        Memory memory = new ArrayBasedMemory();
        BrainmugContext context = new BrainfuckContext(memory, io);

        program.execute(context);
        terminal.close();
    }
}
