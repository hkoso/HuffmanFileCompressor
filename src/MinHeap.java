import java.util.ArrayList;

public class MinHeap {

    private ArrayList<Node> heap;


    public MinHeap(){
        heap = new ArrayList<>();
    }

    public void insert(Node node) {
        int repeatNode = isRepeated(node);
        if(repeatNode != -1){
            heap.get(repeatNode).setFrequency(heap.get(repeatNode).getFrequency() + 1);
            return;
        }

        heap.add(node);
        bubbleUp();

    }

    public Node removeMin() {
        Node result = heap.get(0);

        heap.set(0, heap.get(heap.size() - 1));

        heap.remove(heap.size() - 1);

        bubbleDown();

        return result;
    }

    private void bubbleUp() {
        int cur = heap.size() - 1;
        int parent = cur / 2;

        while(heap.get(cur).compareTo(heap.get(parent)) == -1) {
            swap(cur, parent);

            cur = parent;
            parent /= 2;
        }

    }

    private void bubbleDown() {
        int cur = 0;
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

    private void swap(int cur, int parent) {
        Node temp = heap.get(cur);
        heap.set(cur, heap.get(parent));
        heap.set(parent, temp);
    }

    private int isRepeated(Node node) {
        for (int i = 0; i < heap.size(); i++) {
            if(heap.get(i).equals(node)) {
                return i;
            }
        }
        return -1;
    }

    public Node peek() {
        return heap.get(0);
    }

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
