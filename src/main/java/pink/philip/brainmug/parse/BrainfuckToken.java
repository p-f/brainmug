package pink.philip.brainmug.parse;

/**
 * Default tokens of the brainf{@code ***} language.
 */
public enum BrainfuckToken implements Token {
    LEFT('<'),
    RIGHT('>'),
    DECREMENT('-'),
    INCREMENT('+'),
    READ(','),
    PRINT('.'),
    LOOP_START('['),
    LOOP_END(']');

    /**
     * The char used to identify this token in the code.
     */
    private final char str;

    /**
     * Constructor.
     *
     * @param str The {@code char} identifying this token.
     */
    BrainfuckToken(char str) {
        this.str = str;
    }

    @Override
    public char asChar() {
        return str;
    }
}
