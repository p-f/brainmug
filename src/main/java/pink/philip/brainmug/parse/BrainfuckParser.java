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

import pink.philip.brainmug.api.Program;

import java.io.IOException;
import java.io.InputStream;

/**
 * A parser for the brainfuck language.
 */
public class BrainfuckParser {
    /**
     * Parse a brainfuck program from an input stream.
     *
     * @param inputStream The input stream.
     * @return The program.
     * @throws IOException When reading from the stream fails.
     * @throws RuntimeException On parser errors.
     */
    public Program parse(InputStream inputStream) throws IOException {
        Tokenizer<BrainfuckToken> tokenizer =
                new DefaultTokenizer<>(BrainfuckToken.class);
        BrainfuckHandler brainfuckHandler = new BrainfuckHandler();
        tokenizer.tokenize(inputStream, brainfuckHandler);
        return brainfuckHandler.getProgram();
    }
}
