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
package pink.philip.brainmug.compiler.parse;

import pink.philip.brainmug.compiler.AbstractCompilerStage;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

/**
 * Split a sequence of characters into a sequence of tokens.<p>
 * Other tokens will simply be ignored.
 *
 * @param <T> The type of tokens to recognize.
 */
public class BasicTokenizer<T extends Token>
        extends AbstractCompilerStage<Integer, T> {

    /**
     * A map assigning a token from a character.
     */
    final Map<Integer, T> tokenMap;

    /**
     * Create a new tokenizer instance from a collection of accepted tokens.
     *
     * @param acceptedTokens A collection of accepted tokens.
     */
    public BasicTokenizer(Collection<T> acceptedTokens) {
        tokenMap = new TreeMap<>();
        for (T token : Objects.requireNonNull(acceptedTokens)) {
            tokenMap.put((int) token.asChar(), token);
        }
    }

    @Override
    public void handle(Integer inputElement) {
        final T token = tokenMap.get(inputElement);
        if (token != null) {
            emit(token);
        }
    }
}
