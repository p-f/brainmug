package pink.philip.brainmug.runtime.impl.io;

import org.jline.terminal.Terminal;
import pink.philip.brainmug.api.RuntimeIO;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.Objects;

/**
 * IO implementation based on JLine3.
 */
public class JLineIO implements RuntimeIO {
    private final Reader terminalInput;
    private final Writer terminalOutput;

    /**
     * Setup this implementation.
     *
     * @param terminal The terminal to use.
     */
    public JLineIO(Terminal terminal) {
        terminalInput = Objects.requireNonNull(terminal).reader();
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
}
