package pink.philip.brainmug.parse;

/**
 * A handler for a sequence of {@link Token}s (or any object) of a certain type.
 *
 * @param <T> The token type.
 */
public interface TokenSequenceHandler<T> {

    /**
     * Handle the start of the token sequence.
     */
    void handleStart();

    /**
     * Handle a token.
     *
     * @param token The token.
     */
    void handleToken(T token);

    /**
     * Handle the end of the sequence.
     */
    void handleEnd();
}
