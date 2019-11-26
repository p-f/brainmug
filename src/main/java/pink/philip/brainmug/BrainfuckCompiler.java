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
package pink.philip.brainmug;

import pink.philip.brainmug.api.Program;
import pink.philip.brainmug.compiler.CompilerBuilder;
import pink.philip.brainmug.compiler.CompilerPipeline;
import pink.philip.brainmug.compiler.bf.CreateInstructionsFromTokens;
import pink.philip.brainmug.compiler.input.InputFromStream;
import pink.philip.brainmug.compiler.parse.BasicTokenizer;
import pink.philip.brainmug.compiler.parse.BrainfuckToken;

import java.io.InputStream;
import java.util.Arrays;

/**
 * Helper class creating a brainfuck compiler.
 */
public class BrainfuckCompiler {

    /**
     * Creates an instance of a brainfuck compiler.
     */
    public static CompilerPipeline<InputStream, Program> getInstance() {
        return CompilerBuilder
                .from(new InputFromStream())
                .addStage(new BasicTokenizer<>(Arrays.asList(
                        BrainfuckToken.class.getEnumConstants())))
                .addStage(new CreateInstructionsFromTokens())
                .build();
    }
}
