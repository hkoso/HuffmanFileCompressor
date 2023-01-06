import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class TestRunner {

    public static void main(String[] args) {
        Result result1 = JUnitCore.runClasses(TestNode.class);

        System.out.println("Start testing Node class.");
        for(Failure failure: result1.getFailures()){

            System.out.println(failure);
        }
        System.out.println("End testing Node class.");
        System.out.println("If all test for TestNode passed: " + result1.wasSuccessful());

        Result result2 = JUnitCore.runClasses(TestBinaryTree.class);

        System.out.println("\nStart testing BinaryTree class.");
        for(Failure failure: result2.getFailures()){

            System.out.println(failure);
        }
        System.out.println("End testing BinaryTree class.");
        System.out.println("If all test for TestBinaryTree passed: " + result2.wasSuccessful());


        Result result3 = JUnitCore.runClasses(TestMinHeap.class);

        System.out.println("\nStart testing MinHeap class.");
        for(Failure failure: result3.getFailures()){

            System.out.println(failure);
        }
        System.out.println("End testing MinHeap class.");
        System.out.println("If all test for TestBinaryTree passed: " + result3.wasSuccessful());
    }
}
