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

import java.io.IOException;
import java.io.InputStream;

/**
 * Split a sequence of characters into a sequence of tokens.
 *
 * @param <T> The type of tokens to recognize.
 */
public interface Tokenizer<T extends Token> {

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
