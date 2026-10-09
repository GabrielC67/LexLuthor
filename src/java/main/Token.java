public class Token {
    public enum TokenType {
        DIGIT, LETTER, STRING, KEYWORD, EOI, INVALID
    }

    private TokenType type;
    private String val;

    public Token(TokenType t, String v) {
        type = t;
        val = v;
    }

    public TokenType getTokenType() {
        return type;
    }

    public String getTokenValue() {
        return val;
    }

    @Override
    public String toString() {
        return "Token{" +
                "type=" + type +
                ", val='" + val + '\'' +
                '}';
    }
}
