package pink.philip.brainmug.parse;

import pink.philip.brainmug.api.Program;

import java.io.IOException;
import java.io.InputStream;

/**
 * A parser for the brainfuck language.
 */
public class BrainfuckParser {
    /**
     * Parse a brainfuck program from an input stream.
     *
     * @param inputStream The input stream.
     * @return The program.
     * @throws IOException When reading from the stream fails.
     * @throws RuntimeException On parser errors.
     */
    public Program parse(InputStream inputStream) throws IOException {
        Tokenizer<BrainfuckToken> tokenizer =
                new DefaultTokenizer<>(BrainfuckToken.class);
        BrainfuckHandler brainfuckHandler = new BrainfuckHandler();
        tokenizer.tokenize(inputStream, brainfuckHandler);
        return brainfuckHandler.getProgram();
    }
}
