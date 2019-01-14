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
