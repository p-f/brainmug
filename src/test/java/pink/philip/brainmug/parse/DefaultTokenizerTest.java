package pink.philip.brainmug.parse;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static pink.philip.brainmug.parse.DefaultTokenizerTest.TestToken.ONE;
import static pink.philip.brainmug.parse.DefaultTokenizerTest.TestToken.ZERO;

/**
 * Test for {@link DefaultTokenizer}
 */
public class DefaultTokenizerTest {

    /**
     * Tokens for this test.
     */
    public enum TestToken implements Token {
        ONE('1'),
        ZERO('0');

        /**
         * Character representation of this token.
         */
        private final char str;

        /**
         * Create a token.
         *
         * @param str The char representation.
         */
        TestToken(char str) {
            this.str = str;
        }

        @Override
        public char asChar() {
            return str;
        }
    }

    /**
     * A token type were some tokens have the same char representation.
     */
    public enum InvalidToken implements Token {
        ZERO('0'),
        ZERO2('0'),
        ONE('1');

        /**
         * Character representation of this token.
         */
        private final char str;

        /**
         * Create a token.
         *
         * @param str The char representation.
         */
        InvalidToken(char str) {
            this.str = str;
        }

        @Override
        public char asChar() {
            return str;
        }
    }

    @Test
    public void testTokenize() throws IOException {
        ArrayList<TestToken> tokens = new ArrayList<>();
        TokenCollector<TestToken> collector = new TokenCollector<>(tokens);
        // The tokens in the following string are: 010101
        String data = "0someString   <- spaced 101\n\n\t\r\ncontrol seq.01 ";
        DefaultTokenizer<TestToken> tokenizer
                = new DefaultTokenizer<>(TestToken.class);
        tokenizer.tokenize(new ByteArrayInputStream(data.getBytes()),
                collector);
        assertEquals(Arrays.asList(ZERO, ONE, ZERO, ONE, ZERO, ONE),
                tokens);
        collector.verify();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIllegalTokenType() {
        Tokenizer<InvalidToken> t = new DefaultTokenizer<>(InvalidToken.class);
        fail();
    }
}
