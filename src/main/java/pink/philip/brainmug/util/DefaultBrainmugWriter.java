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
package pink.philip.brainmug.util;

import pink.philip.brainmug.api.Program;
import pink.philip.brainmug.api.instructions.Instruction;

import java.io.IOException;
import java.io.Writer;

/**
 * Writes a brainmug program where {@link Instruction#toString()} is used
 * to convert the program to a string representation.
 */
public final class DefaultBrainmugWriter {

    /**
     * The magic bytes used to identify brainmug program source code.
     * (Currently not in use.)
     */
    public static final String MAGIC_BYTES_BRAINMUG = "\uD83E\uDDE0☕";

    /**
     * The magic bytes used to identify brainmug programs in experimental
     * snapshot releases.
     */
    public static final String MAGIC_BYTES_BRAINMUG_EXPERIMENTAL_SNAPSHOT =
            "\uD83E\uDDEA";

    /**
     * No instances of this class are needed.
     */
    private DefaultBrainmugWriter() {
    }

    /**
     * Write a program to a {@link Writer}.
     * The encoding of the writer is expected to be UTF-8.
     * This does not close the writer.
     *
     * @param program The program to write.
     * @param writer  The writer.
     * @throws IOException when writing fails.
     */
    public static void write(Program program, Writer writer)
            throws IOException {
        writer.write(MAGIC_BYTES_BRAINMUG);
        writer.write(MAGIC_BYTES_BRAINMUG_EXPERIMENTAL_SNAPSHOT);
        for (Instruction component : program.getComponents()) {
            writer.write(component.toString());
        }
        writer.write(System.lineSeparator());
    }
}
