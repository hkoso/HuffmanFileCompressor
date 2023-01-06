
import org.junit.Test;
import static org.junit.Assert.*;

public class TestNode {

    // Set up test value
    Node node1 = new Node('A', 12);
    Node node2 = new Node('a', 12);
    Node node3 = new Node('a', 30);
    Node node4 = new Node(30);

    @Test
    public void testEquals() {
        assertTrue(node2.equals(node3));
        assertTrue(node2.equals(node2));
        assertFalse(node1.equals(node2));
        assertFalse(node3.equals(node4));
    }

    @Test
    public void testSetterAndGetter() {
        node1.setCode("1010");
        assertEquals(node1.getCode(), "1010");
        assertEquals(node2.getCode(), "");
        node1.setCode("");
        assertNotEquals("1010", node1.getCode());
        assertEquals(node1.getCode(), "");


        assertEquals(node1.getCharacter(), 'A');
        assertEquals('\0', node4.getCharacter());
        assertNotEquals('A', node3.getCharacter());


        assertEquals(node1.getFrequency(), 12);
        assertNotEquals(12, node3.getFrequency());
        node1.setFrequency(10);
        assertNotEquals(12, node1.getFrequency());
        node1.setFrequency(12);


        assertEquals(node1.getIndex(), 0);
        assertEquals(node2.getIndex(), 0);
        assertEquals(node3.getIndex(), 0);
        assertEquals(node4.getIndex(), 0);


        node1.setLeft(node2);
        node1.setRight(node3);
        node2.setLeft(node4);
        assertEquals(node2.getIndex(), 2 * 0 + 1);
        assertEquals(node3.getIndex(), 2 * 0 + 2);
        assertEquals(node4.getIndex(), 2 * node2.getIndex() + 1);


        assertTrue(node1.getLeft().equals(node2));
        assertTrue(node1.getRight().equals(node3));

    }

    @Test
    public void testComparable() {
        assertEquals(-1, node1.compareTo(node3));
        assertEquals(0, node1.compareTo(node2));
        assertEquals(1, node3.compareTo(node1));


    }

}
