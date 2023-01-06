import java.util.ArrayList;

public class MinHeap {

    private ArrayList<Node> heap;

    // Constructs a new array-based min heap
    // Parameters:
    //      None
    public MinHeap(){
        heap = new ArrayList<>();
    }


    // Insert the node and perform heap sort towards the array.
    // If there is a node representing the same character info already in the array,
    // the frequency of the node in the array will be incremented 1.
    // Returns:
    //      None
    // Parameters:
    //      Node: node to be added
    public void insert(Node node) {
        int repeatNode = isRepeated(node);
        if(repeatNode != -1){
            heap.get(repeatNode).setFrequency(heap.get(repeatNode).getFrequency() + 1);
            bubbleDown(repeatNode);
            return;
        }

        heap.add(node);
        bubbleUp();

    }


    // Remove the min frequency node and perform heap sort towards the array.
    // Returns:
    //      Node has the least frequency
    // Parameters:
    //      None
    public Node removeMin() {
        Node result = heap.get(0);

        heap.set(0, heap.get(heap.size() - 1));

        heap.remove(heap.size() - 1);

        bubbleDown(0);

        return result;
    }

    // bubble up the last node in the heap array
    // Returns:
    //      None
    // Parameters:
    //      None
    private void bubbleUp() {
        int cur = heap.size() - 1;
        int parent = cur / 2;

        while(heap.get(cur).compareTo(heap.get(parent)) == -1) {
            swap(cur, parent);

            cur = parent;
            parent /= 2;
        }

    }



    // bubble down the first node in the heap array
    // Returns:
    //      None
    // Parameters:
    //      int - position: position of the node to be bubbled down
    private void bubbleDown(int position) {
        int cur = position;
        int left = 2 * cur + 1;
        int right = 2 * cur + 2;

        while(true) {
            if(left < heap.size() && heap.get(cur).compareTo(heap.get(left)) > 0) {
                swap(cur, left);
            }
            else if (right < heap.size() && heap.get(cur).compareTo(heap.get(right)) > 0) {
                swap(cur, right);
            }
            else {
                return;
            }
        }
    }





    // Swap the position of two node in the array
    // Returns:
    //      None
    // Parameters:
    //      cur - int: position of one node
    //      parent - int: position of the other array
    private void swap(int cur, int parent) {
        Node temp = heap.get(cur);
        heap.set(cur, heap.get(parent));
        heap.set(parent, temp);
    }


    // Check if the character that the node represent has already in the heap
    // Returns:
    //      int: position of the repeated node or -1 if there is no such node
    // Parameters:
    //      Node - node: node to be checked
    private int isRepeated(Node node) {
        for (int i = 0; i < heap.size(); i++) {
            if(heap.get(i).equals(node)) {
                return i;
            }
        }
        return -1;
    }



    // Peek the first node in the heap array
    // Returns:
    //      Node: first node in the array
    // Parameters:
    //      None
    public Node peek() {
        return heap.get(0);
    }



    // Get the number of Nodes in the heap
    // Returns:
    //      int: number of nodes in the heap
    // Parameters:
    //      None
    public int size() {
        return heap.size();
    }

    /*
    public static void main(String[] arg) {
        MinHeap heap = new MinHeap();

        Node n1 = new Node('a', 1);
        Node n2 = new Node('b', 1);
        Node n3 = new Node('c', 1);
        Node n4 = new Node('e', 1);

        heap.insert(n1);
        heap.insert(n1);
        heap.insert(n4);
        heap.insert(n2);
        heap.insert(n3);
        heap.insert(n1);

        System.out.println(heap.removeMin());
        System.out.println(heap.removeMin());
        System.out.println(heap.removeMin());
        System.out.println(heap.removeMin());

    }*/

}
