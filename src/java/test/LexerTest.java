import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class LexerTest {
    private Lexer lexer;

    @Before
    public void setUp() {
        lexer = new Lexer();
    }

    private void assertNextToken(Token.TokenType expectedType, String expectedValue) {
        Token token = lexer.getNextToken();
        Assert.assertEquals(expectedType, token.getTokenType());
        Assert.assertEquals(expectedValue, token.getTokenValue());
    }

    @Test
    public void testValidHtmlTags() {
        lexer.setTextBuffer("<html><body><p></body></html>");

        assertNextToken(Token.TokenType.KEYWORD, "<html>");
        assertNextToken(Token.TokenType.KEYWORD, "<body>");
        assertNextToken(Token.TokenType.KEYWORD, "<p>");
        assertNextToken(Token.TokenType.KEYWORD, "</body>");
        assertNextToken(Token.TokenType.KEYWORD, "</html>");
        assertNextToken(Token.TokenType.EOI, "EOI");
    }

    @Test
    public void testInvalidHtmlTags() {
        lexer.setTextBuffer("<notatag> <htmlx>");

        assertNextToken(Token.TokenType.INVALID, "<notatag>");
        assertNextToken(Token.TokenType.INVALID, "<htmlx>");
        assertNextToken(Token.TokenType.EOI, "EOI");
    }

    @Test
    public void testStringsAndDigits() {
        lexer.setTextBuffer("hello 123 world 456");

        assertNextToken(Token.TokenType.STRING, "hello");
        assertNextToken(Token.TokenType.DIGIT, "123");
        assertNextToken(Token.TokenType.STRING, "world");
        assertNextToken(Token.TokenType.DIGIT, "456");
        assertNextToken(Token.TokenType.EOI, "EOI");
    }

    @Test
    public void testMixedContent() {
        lexer.setTextBuffer("<p>hello 123!</p>");

        assertNextToken(Token.TokenType.KEYWORD, "<p>");
        assertNextToken(Token.TokenType.STRING, "hello");
        assertNextToken(Token.TokenType.DIGIT, "123");
        assertNextToken(Token.TokenType.INVALID, "!"); // punctuation
        assertNextToken(Token.TokenType.KEYWORD, "</p>");
        assertNextToken(Token.TokenType.EOI, "EOI");
    }

    @Test
    public void testEmptyAndWhitespace() {
        lexer.setTextBuffer("   \n\t  ");
        assertNextToken(Token.TokenType.EOI, "EOI");
    }
}
