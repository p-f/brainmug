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
package pink.philip.brainmug.compiler;

import java.util.Objects;
import java.util.Optional;

/**
 * A runnable compiler pipeline.
 *
 * @param <I> The input of the compiler.
 * @param <O> The output of the compiler.
 */
public class CompilerPipeline<I, O> {

    /**
     * The input stage of this compiler.
     */
    private final CompilerStage<I, ?> inputStage;

    /**
     * The output stage of this compiler.
     */
    private final CompilerStage<?, O> outputStage;

    /**
     * The result holder.
     */
    private final CompilerResultHolderContext<O> output;

    /**
     * Create a new compiler instance.
     *
     * @param inputStage  The input stage of the compiler.
     * @param outputStage The output stage of the compiler.
     */
    public CompilerPipeline(CompilerStage<I, ?> inputStage,
                            CompilerStage<?, O> outputStage) {
        this.inputStage = Objects.requireNonNull(inputStage);
        this.outputStage = Objects.requireNonNull(outputStage);
        output = new CompilerResultHolderContext<>();
        outputStage.open(output);
    }

    /**
     * Run this compiler pipeline.
     *
     * @param input An input element.
     * @return The output.
     */
    public Optional<O> run(I input) {
        inputStage.handle(input);
        inputStage.close();
        return output.getResult();
    }
}
