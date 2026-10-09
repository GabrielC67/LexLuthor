import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Stack;

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
            System.out.println("Scanning & Parsing file: " + file.getName());
            System.out.println("=========================================");
            try {
                String content = new String(Files.readAllBytes(Paths.get(file.getPath())));
                ArrayList<Token> tokens = lex(content);
                
                System.out.println("\n--- Building Tree ---");
                Tree domTree = buildTree(tokens);
                domTree.traversePreOrder(domTree.getRoot(), "");
                
            } catch (Exception e) {
                System.out.println("Error reading file: " + file.getName());
                e.printStackTrace();
            }
        }
    }

    public static ArrayList<Token> lex(String content) {
        Lexer lexer = new Lexer();
        lexer.setTextBuffer(content);

        ArrayList<Token> tokens = new ArrayList<>();
        Token token;
        do {
            token = lexer.getNextToken();
            tokens.add(token);
        } while (token.getTokenType() != Token.TokenType.EOI);
        
        return tokens;
    }

    public static Tree buildTree(ArrayList<Token> tokens) {
        Tree tree = new Tree();
        Stack<Tree.Node> stack = new Stack<>();

        for (Token token : tokens) {
            if (token.getTokenType() == Token.TokenType.KEYWORD) {
                String val = token.getTokenValue();
                if (val.startsWith("</")) {
                    // It's a closing tag, pop the stack
                    if (!stack.isEmpty()) {
                        stack.pop();
                    }
                } else {
                    // It's an opening tag, create a node and add as child to current parent
                    Tree.Node node = new Tree.Node(token);
                    if (!stack.isEmpty()) {
                        tree.addNode(stack.peek(), node);
                    } else {
                        tree.setRoot(node); // First tag (e.g. <html>) is root
                    }
                    stack.push(node);
                }
            } else if (token.getTokenType() != Token.TokenType.EOI) {
                // For strings, digits, or text inside tags
                String text = token.getTokenValue().trim();
                if (!text.isEmpty()) {
                    Tree.Node node = new Tree.Node(token);
                    if (!stack.isEmpty()) {
                        tree.addNode(stack.peek(), node);
                    }
                }
            }
        }
        return tree;
    }
}
