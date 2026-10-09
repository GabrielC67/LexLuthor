import java.util.ArrayList;
import java.util.List;

public class Tree {

    public static class Node {
        public Token data;
        public List<Node> children;

        public Node(Token data) {
            this.data = data;
            this.children = new ArrayList<>();
        }

        public void addChild(Node child) {
            this.children.add(child);
        }
    }

    private Node root;

    public Tree() {
        this.root = null;
    }

    public void setRoot(Node root) {
        this.root = root;
    }

    public Node getRoot() {
        return root;
    }

    public void addNode(Node parent, Node node) {
        if (parent != null) {
            parent.addChild(node);
        } else {
            if (this.root == null) {
                this.root = node;
            }
        }
    }

    public void traversePreOrder(Node node, String indent) {
        if (node == null) return;

        // Print the current node's token
        System.out.println(indent + node.data.getTokenValue());

        // Traverse all children (N-ary tree, not just left/right)
        for (Node child : node.children) {
            traversePreOrder(child, indent + "  ");
        }
    }
}
