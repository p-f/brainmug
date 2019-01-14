package pink.philip.brainmug.parse;

import pink.philip.brainmug.api.Program;

import java.io.IOException;
import java.io.InputStream;

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
        Tokenizer<DefaultToken> tokenizer =
                new DefaultTokenizer<>(DefaultToken.class);
        DefaultHandler brainfuckHandler = new DefaultHandler();
        tokenizer.tokenize(inputStream, brainfuckHandler);
        return brainfuckHandler.getProgram();
    }
}
