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

/**
 * A context of a compilation stage.
 *
 * @param <O> The type of output elements of a stage.
 */
public interface CompilerContext<O> {

    /**
     * Get the next stage of the compiler.
     *
     * @return The stage (or null, when the current stage is the final stage.
     */
    CompilerStage<O, ?> getNextStage();

    /**
     * Emit an output element.
     *
     * @param outputElement The element to emit.
     */
    void emit(O outputElement);
}