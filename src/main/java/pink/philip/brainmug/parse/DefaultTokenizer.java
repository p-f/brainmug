package pink.philip.brainmug.parse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

/**
 * A tokenizer creating tokens from an input stream, ignoring unrecognized
 * characters.
 *
 * @param <T> The type of the tokens.
 */
public class DefaultTokenizer<T extends Enum<T> & Token>
        implements Tokenizer<T> {

    /**
     * A map assigning a token to certain characters.
     */
    private final Map<Character, T> tokenMap;

    /**
     * Create this tokenizer from an input stream.
     */
    public DefaultTokenizer(Class<T> tokenType) {
        tokenMap = new TreeMap<>();
        T[] tokens = tokenType.getEnumConstants();
        for (T token : tokens) {
            tokenMap.put(token.asChar(), token);
        }
        if (tokenMap.size() < tokens.length) {
            throw new IllegalArgumentException(
                    "Failed to create token mapping.");
        }
    }

    @Override
    public void tokenize(InputStream input,
                         TokenSequenceHandler<T> handler) throws IOException {
        Objects.requireNonNull(handler);
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(input));
        handler.handleStart();
        int nextChar;
        do {
            nextChar = reader.read();
            if (nextChar == -1) {
                break;
            }
            if (Character.isValidCodePoint(nextChar)) {
                T nextToken = tokenMap.get((char) nextChar);
                if (nextToken != null) {
                    handler.handleToken(nextToken);
                }
            }
        } while (true);
        handler.handleEnd();
        reader.close();
    }
}
