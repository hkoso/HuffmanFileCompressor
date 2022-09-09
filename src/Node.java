public class Node implements Comparable<Node>{
    private char character;
    private int frequency;

    private String code;

    private Node left;

    private Node right;

    private int index;

    public Node(char character, int frequency) {
        this.character = character;
        this.frequency = frequency;
        this.code = "";
        this.index = 0;
    }

    public Node(int frequency){
        this.frequency = frequency;
        this.code = "";
        this.index = 0;
    }

    public boolean equals(Node other){
        return this.character == other.getCharacter();
    }
    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }
    public char getCharacter() {
        return character;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setLeft(Node node){
        this.left = node;
        if(node != null){
            node.setIndex(2 * this.getIndex() + 1);
        }
    }

    public void setRight(Node node) {
        this.right = node;
        if(node != null) {
            node.setIndex(2 * this.getIndex() + 2);
        }
    }

    public Node getLeft() {
        return left;
    }

    public Node getRight() {
        return right;
    }

    public int getIndex() {
        return this.index;
    }

    public void setIndex(int i) {
        this.index = i;
    }

    public String toString() {
        return "" + character + ": " + frequency;
    }

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
