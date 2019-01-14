package pink.philip.brainmug.api.exceptions;

/**
 * A generic brainmug runtime exception. (Unchecked.)
 */
public class BrainmugRuntimeException extends RuntimeException {
    /**
     * Construct this exception using a message.
     *
     * @param message The exception message.
     */
    public BrainmugRuntimeException(String message) {
        super(message);
    }
}
