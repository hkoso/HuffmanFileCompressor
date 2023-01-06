import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestBinaryTree {

    // Set up
    Node node1 =  new Node('a', 12);
    Node node2 =  new Node('b', 2);

    BinaryTree binaryTree1 = new BinaryTree(node1);
    BinaryTree binaryTree2 = binaryTree1.mergeHuffmanTree(new BinaryTree(node2));

    @Test
    public void testGetter(){
        assertTrue(binaryTree1.getRoot().equals(node1));
        assertEquals(binaryTree1.getWeight(), node1.getFrequency());


        assertEquals(binaryTree2.getWeight(), node1.getFrequency() + node2.getFrequency());
    }

    @Test
    public void testOtherFeature() {
        node2.setCode("110010");
        BinaryTree binaryTree3 = binaryTree1.mergeHuffmanTree(new BinaryTree(node2));
        assertEquals(binaryTree3.search('b'), "110010");

        assertEquals(node2.getIndex(), 2);

        assertEquals(binaryTree3.reverseLevelOrder(), "b2<-a1<-\0" + "0<-");

        assertEquals(binaryTree1.compareTo(binaryTree3), -1);
        assertEquals(binaryTree1.compareTo(binaryTree1), 0);
        assertEquals(binaryTree3.compareTo(binaryTree1), 1);

    }
}
