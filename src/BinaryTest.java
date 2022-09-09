import java.io.*;

public class BinaryTest {

    public static void main(String[] args) throws IOException {
        File file = new File("test.dat");
        FileOutputStream output = new FileOutputStream(file);

        boolean l = '\n' == '\u0000';
        char i = '\n';
        byte b = (byte) 0b10000000;

        System.out.print(l);

        FileInputStream input = new FileInputStream(file);
        File unzippedFile = new File("unzipped.txt");
        PrintWriter output1  = new PrintWriter(unzippedFile);

        output1.print(i);
        output1.print("ha");

        output1.close();
    }
}
