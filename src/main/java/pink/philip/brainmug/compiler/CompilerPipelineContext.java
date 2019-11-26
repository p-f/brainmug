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

/**
 * A context providing indirect access to the next compiler stage in a pipeline.
 *
 * @param <O> The input type of the next stage in the pipeline.
 */
class CompilerPipelineContext<O> implements CompilerContext<O> {

    /**
     * The next stage of the compiler pipeline.
     */
    private final CompilerStage<O, ?> nextStage;

    /**
     * Initialize this context.
     *
     * @param nextStage The next stage of the pipeline.
     */
    CompilerPipelineContext(CompilerStage<O, ?> nextStage) {
        this.nextStage = Objects.requireNonNull(nextStage);
    }

    @Override
    public CompilerStage<O, ?> getNextStage() {
        return nextStage;
    }

    @Override
    public void emit(O outputElement) {
        nextStage.handle(outputElement);
    }
}
