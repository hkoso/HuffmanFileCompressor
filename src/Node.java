public class Node implements Comparable<Node>{

    // Initialize variables in the class
    private char character;
    private int frequency;

    private String code;

    private Node left;

    private Node right;

    private int index;



    // Constructs a new Node
    // Parameters:
    //      char: the character that node represents
    //      int: number of times such character appears in certain text
    public Node(char character, int frequency) {
        this.character = character;
        this.frequency = frequency;

        // initialize the huffman code variable and node index variable
        this.code = "";
        this.index = 0;
    }


    // Constructs a new Node stores total frequency info
    // Parameters:
    //      int: number of characters appears in certain text
    public Node(int frequency){
        this.frequency = frequency;
        this.code = "";
        this.index = 0;
    }


    // Determine if two nodes represents the same character info
    // Returns:
    //      boolean: if two nodes represents the same character info
    // Parameters:
    //      Node: another nodes to be compared with
    public boolean equals(Node other){
        return this.character == other.getCharacter();
    }


    // encode the node
    // Returns:
    //      None
    // Parameters:
    //      String: Huffman code of the character that node represents
    public void setCode(String code) {
        this.code = code;
    }


    // Get the huffman code of the node
    // Returns: Huffman code stored by the node
    // Parameters:
    //      None
    public String getCode() {
        return this.code;
    }


    // Get the character stored by the node
    // Returns:
    //      char: Character stored by the node
    // Parameters:
    //      None
    public char getCharacter() {
        return character;
    }


    // Set the frequency info inside the node
    // Returns: None
    // Parameters:
    //      int: frequency number
    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }


    // Get the frequency info inside the node
    // Returns:
    //      int: frequency number
    // Parameters:
    //      None
    public int getFrequency() {
        return frequency;
    }


    // set the parameter as the left child of the current node
    // Returns:
    //      None
    // Parameters:
    //      Node: another node to be the left child
    public void setLeft(Node node){
        this.left = node;
        if(node != null){
            node.setIndex(2 * this.getIndex() + 1);
        }
    }


    // set the parameter as the right child of the current node
    // Returns:
    //      None
    // Parameters:
    //      Node: another node to be the right child
    public void setRight(Node node) {
        this.right = node;
        if(node != null) {
            node.setIndex(2 * this.getIndex() + 2);
        }
    }


    // get the left child of the current node
    // Returns:
    //      Node: left child of the node
    // Parameters:
    //      None
    public Node getLeft() {
        return left;
    }


    // get the right child of the current node
    // Returns:
    //      Node: right child of the node
    // Parameters:
    //      None
    public Node getRight() {
        return right;
    }


    // get the index of the current node
    // Returns:
    //      int: index of the node
    // Parameters:
    //      None
    public int getIndex() {
        return this.index;
    }


    // set the index of the current node
    // Returns:
    //      None
    // Parameters:
    //      int: index to be updated
    public void setIndex(int index) {
        this.index = index;
    }


    // display the node information as string
    // Returns:
    //      String: character and frequency info in the node
    // Parameters:
    //      None
    public String toString() {
        return "" + character + ": " + frequency;
    }


    // Compare two nodes according to their frequency
    // Returns:
    //      1 - if this node has higher frequency
    //      0 - if two nodes have the same weight
    //     -1 - if the other node has higher weight
    // Parameters:
    //      None
    @Override
    public int compareTo(Node other) {
        if(this.frequency > other.getFrequency()) {
            return 1;
        }
        else if (this.frequency == other.getFrequency()) {
            return 0;
        }
        else {
            return -1;
        }
    }
}
