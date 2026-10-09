import java.io.File;
import java.util.Scanner;

public class Lexer implements LexerIdea{
    Scanner scanner = new Scanner("./testdata");

    @Override
    public void setTextBuffer(String buffer) {
        scanner = new Scanner(buffer);
    }

    @Override
    public int getCurrentOffset() {
        return 0;
    }

    @Override
    public int getCurrentLineNumber() {
        return 0;
    }

    @Override
    public Token getNextToken() {
        return null;
    }
}
