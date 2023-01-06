import java.io.*;
import java.util.Scanner;

public class BinaryTest {

    public static void main(String[] args) throws IOException {
        File file = new File("simpleDecodingInfo.txt");

        Scanner input = new Scanner(file);
        boolean isBitCountFetched = false;

        String bitLength = "";
        String info = "";

        while (input.hasNext()) {
            String cur = input.nextLine();

            if(!isBitCountFetched) {
                for (int i = 0; i < cur.length(); i++) {
                    bitLength += cur.charAt(i);
                }
                isBitCountFetched = true;
            }
            else {

                for (int i = 0; i < cur.length(); i++) {
                    info += cur.charAt(i);
                }

                info += '\n';
            }

        }

        long b = Integer.parseInt(bitLength);
        System.out.println(b);
        System.out.println(info);

    }
}
