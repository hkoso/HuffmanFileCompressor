import java.util.ArrayList;

public class Queue<E> {

    private ArrayList<E> queue;



    public Queue() {
        this.queue = new ArrayList<E>();
    }

    public E peek() {
        return queue.get(0);
    }

    public void enqueue(E element) {
        queue.add(element);
    }

    public E dequeue() {
        return queue.remove(0);
    }

    public int size() {
        return queue.size();
    }

    /*
    public static void main(String[] arg) {
        Queue queue1 = new Queue();
        Queue queue2 = new Queue();

        Node n1 = new Node('a', 1);
        Node n2 = new Node('b', 1);

        BinaryTree t1 = new BinaryTree(n1);
        BinaryTree t2 = new BinaryTree(n2);

        queue1.enqueue(n1);
        queue2.enqueue(t1);
        queue1.enqueue(n2);
        queue2.enqueue(t2);

        System.out.println(queue1.dequeue());
        System.out.println(queue1.dequeue());

        BinaryTree temp = (BinaryTree) queue2.dequeue();
        System.out.println(temp.preOrder());


        temp = (BinaryTree) queue2.dequeue();
        System.out.println(temp.preOrder());

    }
    */
}
