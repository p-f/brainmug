package pink.philip.brainmug.parse;

/**
 * Tokens that can be identified using a single {@code char}.
 * <p>
 * Implementations of this interface are expected to be an {@code enum}.
 */
public interface Token {
    /**
     * Get the representation of the token, as a {@code char}.
     *
     * @return The {@code char} representing the token.
     */
    char asChar();
}
