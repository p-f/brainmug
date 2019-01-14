package pink.philip.brainmug.parse;

import java.util.Collection;
import java.util.Objects;

/**
 * Collect tokens into a collection.
 *
 * @param <T> The token type.
 */
public class TokenCollector<T> implements TokenSequenceHandler<T> {

    /**
     * The collection used to store tokens.
     */
    private final Collection<T> collection;

    /**
     * Was {@link #handleStart()} called?
     */
    private boolean startCalled = false;

    /**
     * Was {@link #handleEnd()} called?
     */
    private boolean endCalled = false;

    /**
     * Constructor, setting the collection to use.
     *
     * @param collection The collection.
     */
    public TokenCollector(Collection<T> collection) {
        Objects.requireNonNull(collection);
        this.collection = collection;
    }

    @Override
    public void handleStart() {
        if (startCalled) {
            if (endCalled) {
                throw new IllegalStateException("End already handled.");
            } else {
                throw new IllegalStateException("Start already handled.");
            }
        }
        startCalled = true;
    }

    @Override
    public void handleToken(T token) {
        if (!startCalled) {
            throw new IllegalStateException("Start was not handled.");
        }
        if (endCalled) {
            throw new IllegalStateException("End was already handled.");
        }
        collection.add(token);
    }

    @Override
    public void handleEnd() {
        if (endCalled) {
            throw new IllegalStateException("End already handled.");
        } else {
            if (!startCalled) {
                throw new IllegalStateException("Start was not handled.");
            } else {
                endCalled = true;
            }
        }
    }

    /**
     * Check if both {@link #handleStart()} and {@link #handleEnd()} were
     * called in order.
     *
     * @throws IllegalStateException If either method was not called.
     */
    public void verify() {
        if (!(startCalled && endCalled)) {
            throw new IllegalStateException("Start and end not handled.");
        }
    }
}
