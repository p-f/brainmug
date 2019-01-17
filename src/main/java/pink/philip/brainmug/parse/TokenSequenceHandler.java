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
