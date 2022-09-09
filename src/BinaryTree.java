public class BinaryTree implements Comparable<BinaryTree> {
    private Node root;
    private int weight;

    public BinaryTree(Node root) {
        this.root = root;
        this.weight = root.getFrequency();
    }

    public Node getRoot() {
        return root;
    }

    public int getWeight() {
        return weight;
    }

    public String search(char ch) {
        if(root != null) {
            return searchRecursive(root, ch);
        }

        return "";
    }

    private String searchRecursive(Node cur, char ch) {
        String result = "";
        if(cur.getCharacter() == ch) {
            result = cur.getCode();
            return result;
        }

        if(cur.getLeft() != null) {
            result = searchRecursive(cur.getLeft(), ch);
            if(result != ""){
                return result;
            }
        }

        if(cur.getRight() != null) {
            result = searchRecursive(cur.getRight(), ch);
            if(result != "") {
                return result;
            }
        }

        return result;
    }

    public String reverseLevelOrder() {
        String result = "";
        Queue<Node> queue = new Queue<>();

        Node cur = root;
        queue.enqueue(cur);

        while(queue.size() != 0) {
            cur = queue.dequeue();

            if (cur.getLeft() != null) {
                queue.enqueue(cur.getLeft());

            }

            if (cur.getRight() != null) {
                queue.enqueue(cur.getRight());
            }

            result = "" + cur.getCharacter() + cur.getIndex() + "<-" + result;

        }

        return result;
    }

    public void updateTreeNodeIndex(BinaryTree tree, boolean isLeftSubtree) {
        Node cur = tree.getRoot();
        int index = cur.getIndex();

        if(isLeftSubtree) {
            index = 2 * index + 1;
        }
        else {
            index = 2 * index + 2;
        }

        updateTreeNodeIndexRecursive(cur, index);

    }

    private void updateTreeNodeIndexRecursive(Node cur, int index) {
        cur.setIndex(index);
        if(cur.getLeft() != null) {
            updateTreeNodeIndexRecursive(cur.getLeft(), 2 * index + 1);
        }

        if(cur.getRight() != null) {
            updateTreeNodeIndexRecursive(cur.getRight(), 2 * index + 2);
        }
    }

    public BinaryTree addNullRoot() {
        Node root = new Node(this.getWeight());
        int index = this.getRoot().getIndex();
        root.setLeft(this.getRoot());
        this.getRoot().setIndex(index);

        updateTreeNodeIndex(this, true);
        return new BinaryTree(root);
    }

    public BinaryTree mergeHuffmanTree(BinaryTree other) {
        Node root = new Node(this.getWeight() + other.getWeight());

        updateTreeNodeIndex(this, true);
        root.setLeft(this.getRoot());

        updateTreeNodeIndex(other, false);
        root.setRight(other.getRoot());
        return new BinaryTree(root);
    }

    @Override
    public int compareTo(BinaryTree other) {

        if (this.weight > other.getWeight()) {
            return 1;
        } else if (this.weight == other.getWeight()) {
            return 0;
        } else {
            return -1;
        }
    }

    public static void main(String args[]) {
        Node n = new Node('a', 1);
        BinaryTree t = new BinaryTree(n);

        n.setLeft(new Node('D', 2));
        n.setRight(new Node('B', 2));

        System.out.println(t.reverseLevelOrder());
    }
}
