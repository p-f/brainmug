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
