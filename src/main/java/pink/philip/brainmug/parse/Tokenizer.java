package pink.philip.brainmug.parse;

import java.io.IOException;
import java.io.InputStream;

/**
 * Split a sequence of characters into a sequence of tokens.
 *
 * @param <T> The type of tokens to recognize.
 */
public interface Tokenizer<T extends Enum<T> & Token> {

    /**
     * Tokenize an {@link InputStream} and forward the tokens to a handler.
     *
     * @param input   The input stream.
     * @param handler The token sequence handler.
     * @throws IOException When reading from the input stream fails.
     */
    void tokenize(InputStream input,
                  TokenSequenceHandler<T> handler) throws IOException;
}
