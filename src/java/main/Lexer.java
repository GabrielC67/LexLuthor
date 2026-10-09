import java.util.Arrays;
import java.util.List;

public class Lexer implements LexerIdea {
    private String buffer;
    private int idx = -1;
    private char ch;
    private int currentLineNumber = 1;

    private static final List<String> VALID_TAGS = Arrays.asList(
            "html", "head", "body", "p", "div", "strong", "em", "ul", "li"
    );

    @Override
    public void setTextBuffer(String buffer) {
        this.buffer = buffer;
        this.idx = -1;
        this.currentLineNumber = 1;
        nextChar();
    }

    private char nextChar() {
        idx++;
        if (buffer == null || idx >= buffer.length()) {
            ch = '\0';
        } else {
            ch = buffer.charAt(idx);
            if (ch == '\n') {
                currentLineNumber++;
            }
        }
        return ch;
    }

    @Override
    public int getCurrentOffset() {
        return idx;
    }

    @Override
    public int getCurrentLineNumber() {
        return currentLineNumber;
    }

    @Override
    public Token getNextToken() {
        while (true) {
            if (ch == '\0') {
                return new Token(Token.TokenType.EOI, "EOI");
            } else if (ch == '<') {
                // HTML tag
                StringBuilder tagBuilder = new StringBuilder();
                tagBuilder.append(ch);
                ch = nextChar();
                
                boolean isClosing = false;
                if (ch == '/') {
                    isClosing = true;
                    tagBuilder.append(ch);
                    ch = nextChar();
                }

                StringBuilder keywordBuilder = new StringBuilder();
                while (ch != '>' && ch != '\0') {
                    keywordBuilder.append(ch);
                    tagBuilder.append(ch);
                    ch = nextChar();
                }
                
                if (ch == '>') {
                    tagBuilder.append(ch);
                    ch = nextChar();
                }

                String keyword = keywordBuilder.toString().trim().toLowerCase();
                if (VALID_TAGS.contains(keyword)) {
                    return new Token(Token.TokenType.KEYWORD, tagBuilder.toString());
                } else {
                    return new Token(Token.TokenType.INVALID, tagBuilder.toString());
                }
            } else if (Character.isLetter(ch)) {
                StringBuilder stringBuilder = new StringBuilder();
                while (Character.isLetterOrDigit(ch)) {
                    stringBuilder.append(ch);
                    ch = nextChar();
                }
                return new Token(Token.TokenType.STRING, stringBuilder.toString());
            } else if (Character.isDigit(ch)) {
                StringBuilder numBuilder = new StringBuilder();
                while (Character.isDigit(ch)) {
                    numBuilder.append(ch);
                    ch = nextChar();
                }
                return new Token(Token.TokenType.DIGIT, numBuilder.toString());
            } else if (Character.isWhitespace(ch)) {
                ch = nextChar();
            } else {
                // Other characters (punctuation etc.)
                String invalidChar = String.valueOf(ch);
                ch = nextChar();
                return new Token(Token.TokenType.INVALID, invalidChar);
            }
        }
    }
}
