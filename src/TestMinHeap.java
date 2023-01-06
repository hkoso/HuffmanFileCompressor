import org.junit.Test;

import static org.junit.Assert.*;


public class TestMinHeap {

    MinHeap heap = new MinHeap();

    Node n1 = new Node('a', 1);
    Node n2 = new Node('b', 1);
    Node n3 = new Node('c', 1);
    Node n4 = new Node('e', 1);


    @Test
    public void testHeap() {
        heap.insert(n1);
        heap.insert(n4);
        heap.insert(n2);
        heap.insert(n3);
        heap.insert(n1);
        heap.insert(n1);
        heap.insert(n2);

        assertEquals(heap.size(), 4);
        assertEquals(heap.removeMin().getFrequency(), 1);
        assertEquals(heap.removeMin().getFrequency(), 1);
        assertEquals(heap.removeMin().getFrequency(), 2);
        assertEquals(heap.removeMin().getFrequency(), 3);

    }
}
