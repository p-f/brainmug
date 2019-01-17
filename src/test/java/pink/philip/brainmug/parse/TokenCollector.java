/*
 * Copyright 2018-2019 Philip Fritzsche <p-f@users.noreply.github.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
