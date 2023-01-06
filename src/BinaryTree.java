public class BinaryTree implements Comparable<BinaryTree> {
    private Node root;
    private int weight;

    // Constructs a new binary tree
    // Parameters:
    // root: the root node of the tree
    public BinaryTree(Node root) {
        this.root = root;
        this.weight = root.getFrequency(); // add the frequency of the node to total weight
    }


    // Get the root node of the tree.
    // Returns the root node.
    // Parameters:
    //      None
    public Node getRoot() {
        return root;
    }


    // Get the total weight of the tree.
    // Returns the weight.
    // Parameters:
    //      None
    public int getWeight() {
        return weight;
    }


    // Merger the current binary tree with another binary tree
    // Returns: merged binary tree
    // Parameters:
    //      None
    public BinaryTree mergeHuffmanTree(BinaryTree other) {
        Node root = new Node(this.getWeight() + other.getWeight());

        updateTreeNodeIndex(this, true);
        root.setLeft(this.getRoot());

        updateTreeNodeIndex(other, false);
        root.setRight(other.getRoot());
        return new BinaryTree(root);
    }


    // Get the Huffman code of the character from a binary tree.
    // Returns: the huffman code of the character or null if the character does not exist
    // Parameters:
    //      char - An ASCII character
    public String search(char ch) {
        if(root != null) {
            return searchRecursive(root, ch);
        }

        return "";
    }


    // Helper function of the search()
    // Returns: the huffman code of the character or null if the character does not exist
    // Parameters:
    //      Node - A node contains character encoding info
    //      char - An ASCII character
    private String searchRecursive(Node cur, char ch) {
        String result = "";

        // perform inorder search
        // if we find the character
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

    // Update the index of the nodes of a binary tree when it is merged with the other tree
    // Returns: None
    // Parameters:
    //      BinaryTree - A binary tree contains nodes that has information of its index
    //      boolean - if this tree will be merged as left subtree of the new binary tree
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


    // Helper function of the updateTreeNodeIndex(BinaryTree tree, boolean isLeftSubtree)
    // Returns: None
    // Parameters:
    //      Node - A node contains index info
    //      int - index of the node's parent
    private void updateTreeNodeIndexRecursive(Node cur, int index) {
        cur.setIndex(index);
        if(cur.getLeft() != null) {
            updateTreeNodeIndexRecursive(cur.getLeft(), 2 * index + 1);
        }

        if(cur.getRight() != null) {
            updateTreeNodeIndexRecursive(cur.getRight(), 2 * index + 2);
        }
    }


    // Add a null node as the root of the current binary tree,
    // and let the tree be left subtree of the root
    // Returns: new binary tree with root as a null character node
    // Parameters:
    //      None
    public BinaryTree addNullRoot() {
        Node root = new Node(this.getWeight());
        int index = this.getRoot().getIndex();
        root.setLeft(this.getRoot());
        this.getRoot().setIndex(index);

        updateTreeNodeIndex(this, true);
        return new BinaryTree(root);
    }



    // print the binary tree in reverse level order
    // Returns: A string contains characters in the tree in reversed level order
    // Parameters:
    //      None
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


    // Override the comparable class to compare two binary tree according to their total weight
    // Returns:
    //      1 - if this binary tree has higher weight
    //      0 - if two binary trees have the same weight
    //     -1 - if this binary tree has lower weight
    // Parameters:
    //      BinaryTree - another binary tree to compare with
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
    }
}
