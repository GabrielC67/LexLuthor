import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class LexLuthor {
    public static void main(String[] args) {
        File testDataDir = new File("testdata");
        if (!testDataDir.exists() || !testDataDir.isDirectory()) {
            System.out.println("Could not find testdata directory.");
            return;
        }

        File[] files = testDataDir.listFiles((dir, name) -> name.endsWith(".html"));
        if (files == null || files.length == 0) {
            System.out.println("No HTML files found in testdata.");
            return;
        }

        for (File file : files) {
            System.out.println("\n=========================================");
            System.out.println("Scanning file: " + file.getName());
            System.out.println("=========================================");
            try {
                String content = new String(Files.readAllBytes(Paths.get(file.getPath())));
                lex(content);
            } catch (Exception e) {
                System.out.println("Error reading file: " + file.getName());
                e.printStackTrace();
            }
        }
    }

    public static void lex(String content) {
        Lexer lexer = new Lexer();
        lexer.setTextBuffer(content);

        ArrayList<Token> tokens = new ArrayList<>();
        Token token;
        do {
            token = lexer.getNextToken();
            tokens.add(token);
            System.out.println(token);
        } while (token.getTokenType() != Token.TokenType.EOI);
    }
}
