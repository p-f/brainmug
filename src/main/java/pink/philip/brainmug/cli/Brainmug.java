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

import org.apache.commons.cli.*;
import pink.philip.brainmug.api.BrainmugContext;
import pink.philip.brainmug.api.Memory;
import pink.philip.brainmug.api.Program;
import pink.philip.brainmug.api.RuntimeIO;
import pink.philip.brainmug.parse.BrainfuckParser;
import pink.philip.brainmug.runtime.impl.BrainfuckContext;
import pink.philip.brainmug.runtime.impl.io.JLineIO;
import pink.philip.brainmug.runtime.impl.memory.ArrayBasedMemory;
import pink.philip.brainmug.runtime.impl.optimizer.bf.MergeRepeatedInstructions;
import pink.philip.brainmug.util.DefaultBrainmugWriter;

import java.io.BufferedWriter;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

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
        CommandLine options;
        try {
            options = parseOptions(args);
        } catch (ParseException e) {
            System.err.println("Failed to parse options: " + e.getMessage());
            return;
        }
        List<String> extraArgs = options.getArgList();
        if (extraArgs.isEmpty()) {
            System.err.println("No input file.");
            return;
        }
        Path readable = null;
        for (String extraArg : extraArgs) {
            Path path = Paths.get(extraArg);
            if (Files.isReadable(path)) {
                readable = path;
                break;
            } else {
                System.err.println("File not readable: " + extraArg);
            }
        }
        if (readable == null) {
            System.err.println("No input file.");
            return;
        }
        InputStream input = Files.newInputStream(readable,
                StandardOpenOption.READ);
        Program program = new BrainfuckParser().parse(input);
        input.close();
        if (options.hasOption('O')) {
            program = new MergeRepeatedInstructions().execute(program);
        }
        if (options.hasOption('c'))  {
            String outputPathParameter = options.getOptionValue('c');
            Path outPath = Paths.get(outputPathParameter);
            if (!Files.exists(outPath)) {
                BufferedWriter writer = Files.newBufferedWriter(outPath,
                        StandardCharsets.UTF_8);
                DefaultBrainmugWriter.write(program, writer);
                writer.close();
            } else {
                System.err.println("File already exists: " +
                        outputPathParameter);
            }
        }
        RuntimeIO io = new JLineIO();
        Memory memory = new ArrayBasedMemory();
        BrainmugContext context = new BrainfuckContext(memory, io);

        program.execute(context);
        io.close();
    }

    /**
     * Parse the command line arguments.
     *
     * @param args The command line arguments.
     * @return The parsed arguments.
     * @throws ParseException when parsing fails.
     */
    private static CommandLine parseOptions(String[] args) throws ParseException {
        Options options = new Options();
        options.addOption(new Option("h", "help", false, "Show this help."));
        options.addOption(new Option("c", "output-comiled", true,
                "Write the compiled program to a file."));
        options.addOption(new Option("O", "optimize", false,
                "Optimize the program."));
        return new DefaultParser().parse(options, args);
    }
}
