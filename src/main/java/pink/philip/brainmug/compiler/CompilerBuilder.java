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
 * A builder for a compiler pipeline.
 */
public class CompilerBuilder {

    /**
     * A stage of this builder.
     *
     * @param <I> The input of the initial stage.
     * @param <O> The output of the current stage.
     */
    public static class AtStage<I, O> {

        /**
         * The initial stage.
         */
        private final CompilerStage<I,?> initial;

        /**
         * The current stage.
         */
        private final CompilerStage<?, O> current;

        /**
         * Create a new step of the builder.
         *
         * @param initial The initial stage of the pipeline.
         * @param current The current stage associated with the step.
         */
        AtStage(CompilerStage<I, ?> initial, CompilerStage<?, O> current) {
            this.initial = Objects.requireNonNull(initial);
            this.current = Objects.requireNonNull(current);
        }

        /**
         * Add a stage to the compiler pipeline.
         *
         * @param next The next compiler stage.
         * @param <N>  The output type of the next stage.
         * @return A {@link CompilerBuilder builder} from the next stage.
         */
        public <N> AtStage<I, N> addStage(CompilerStage<O, N> next) {
            current.open(new CompilerPipelineContext<>(
                    Objects.requireNonNull(next)));
            return new AtStage<>(initial, next);
        }

        /**
         * Build a compiler pipeline.
         *
         * @return The compiler pipeline.
         */
        public CompilerPipeline<I, O> build() {
            return new CompilerPipeline<>(initial, current);
        }
    }

    /**
     * Start the builder from an initial stage.
     */
    public static <I, O> AtStage<I,O> from(CompilerStage<I, O> initial) {
        return new AtStage<I, O>(initial, initial);
    }
}
