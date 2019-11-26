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
 * An abstract base class for compiler stages providing commonly used
 * functions.
 *
 * @param <I> The input element type.
 * @param <O> The output element type.
 */
public abstract class AbstractCompilerStage<I, O>
        implements CompilerStage<I, O> {

    /**
     * The context of this stage.
     */
    protected CompilerContext<O> context;

    @Override
    public void open(CompilerContext<O> context) {
        this.context = Objects.requireNonNull(context);
    }

    /**
     * Emit an element.
     *
     * @param outputElement The element to emit.
     */
    protected void emit(O outputElement) {
        if (context != null) {
            context.emit(outputElement);
        } else {
            throw new IllegalStateException("Stage was not initialized.");
        }
    }

    @Override
    public void close() {
        CompilerStage<O, ?> nextStage = context.getNextStage();
        if (nextStage != null) {
            nextStage.close();
        }
    }
}
