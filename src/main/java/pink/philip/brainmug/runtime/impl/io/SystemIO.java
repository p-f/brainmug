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
