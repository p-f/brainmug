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

import java.util.Optional;

/**
 * A context holding the result of the final compiler stage.
 *
 * @param <O> The output type.
 */
public class CompilerResultHolderContext<O> implements CompilerContext<O> {

    /**
     * The result of the associated compiler stage.
     */
    private O result;

    @Override
    public CompilerStage<O, ?> getNextStage() {
        return null;
    }

    @Override
    public void emit(O outputElement) {
        result = outputElement;
    }

    /**
     * Get the result of the compilation stage.
     *
     * @return The result.
     */
    public Optional<O> getResult() {
        return Optional.ofNullable(result);
    }
}
