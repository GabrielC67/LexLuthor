import org.junit.Assert;
import org.junit.Test;
public class TokenTest {

    @Test
    public void testTokenCreation() {
        Token token = new Token(Token.TokenType.KEYWORD, "<html>");
        Assert.assertEquals(Token.TokenType.KEYWORD, token.getTokenType());
        Assert.assertEquals("<html>", token.getTokenValue());
    }

    @Test
    public void testTokenToString() {
        Token token = new Token(Token.TokenType.STRING, "hello");
        String expected = "Token{type=STRING, val='hello'}";
        Assert.assertEquals(expected, token.toString());
    }
}
