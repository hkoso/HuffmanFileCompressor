import java.io.*;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Scanner;

public class HuffmanEncoder {

    private File originFile;

    private Queue<BinaryTree> huffmanForest;

    private BinaryTree huffmanTree;
    private MinHeap frequencyInfo;

    private long bitCount;

    public HuffmanEncoder(File originFile) throws IOException {
        this.bitCount = 0;
        this.originFile = originFile;
        huffmanForest = new Queue<>();
        frequencyInfo = new MinHeap();
    }

    public void processDecodingInfo(String filename) throws FileNotFoundException {
        File file = new File(filename);
        Scanner input = new Scanner(file);

        String info = "";
        while (input.hasNext()) {
            String cur = input.nextLine();

            for (int i = 0; i < cur.length(); i++) {
                info += cur.charAt(i);
            }

            info += '\n';
        }

        String[] nodeIndex;
        nodeIndex = info.split("<-");

        int length = Integer.parseInt(nodeIndex[0].substring(1));

        char[] huffmanTree = new char[length + 1];

        for(int i = 0; i < nodeIndex.length - 1; i++) {

            int index = Integer.parseInt(nodeIndex[i].substring(1));

            huffmanTree[index] = nodeIndex[i].charAt(0);
        }

        this.huffmanTree = new BinaryTree(interpretArrayToBinaryTree(huffmanTree, 0));

    }



    private Node interpretArrayToBinaryTree(char[] huffmanTree, int index) {
        Node cur = null;
        if(index < huffmanTree.length) {
            cur = new Node(huffmanTree[index], 0);

            cur.setLeft(interpretArrayToBinaryTree(huffmanTree, 2 * index + 1));
            cur.setRight(interpretArrayToBinaryTree(huffmanTree, 2 * index + 2));
        }

        return cur;
    }

    public void decode() throws IOException {
        File compressedFile = new File("compressed.dat");
        File unzippedFile = new File("unzipped.txt");

        FileInputStream input = new FileInputStream(compressedFile);
        PrintWriter output  = new PrintWriter(unzippedFile);

        long bitDecoded = 0;
        byte value;
        byte bitExtractor = (byte) 0b10000000;
        Node cur = huffmanTree.getRoot();
        while (true) {
            value = (byte) input.read();
            for (int i = 0; i < 8; i++) {

                if(cur.getCharacter() != '\u0000') {
                    output.print(cur.getCharacter());
                    cur = huffmanTree.getRoot();
                }

                if(bitDecoded == bitCount) {
                    output.close();
                    return;
                }
                bitExtractor &= value;

                if (bitExtractor == 0b0) {
                    cur = cur.getLeft();
                }
                else {
                    cur = cur.getRight();
                }

                bitDecoded++;
                bitExtractor = (byte) 0b10000000;
                value <<= 1;
            }

        }


    }

    public void encode() throws IOException {
        buildHuffmanTree();
        assignCode();
        Scanner input = new Scanner(originFile);

        String name = "compressed.dat";

        File file = new File(name);
        FileOutputStream output = new FileOutputStream(file);
        int bitOccupied = 0;
        int bitShifted = 0;

        byte encoding = 0b0;
        while (input.hasNext()) {
            String curLine = input.nextLine();

            for(int i = 0; i <= curLine.length(); i++) {

                String code;
                if(i == curLine.length())  {
                    code = huffmanTree.search('\n');
                }
                else {
                    code = huffmanTree.search(curLine.charAt(i));
                }

                for (int j = 0; j < code.length(); j++) {
                    int bit = code.charAt(j) - '0';


                    if (bitShifted != 7 || bitShifted == 0) {

                        if(bit == 1) {
                            encoding |= 0b1;
                        }

                        encoding <<= 1;
                        bitShifted++;

                    }
                    else {
                        if(bit == 1) {
                            encoding |= 0b1;
                        }

                        output.write(encoding);
                        encoding &= 0b0;
                        bitShifted = 0;

                    }

                    bitOccupied++;
                }
            }

            bitCount = bitOccupied;
        }

        while (bitShifted != 7 && bitShifted != 0) {
            encoding <<= 1;
            bitShifted++;
        }

        output.write(encoding);
        storeHuffmanTree();

    }

    private void assignCode() {
        assignCodeRecursive(huffmanTree.getRoot(), "");
    }

    private void assignCodeRecursive(Node cur, String code) {
        cur.setCode(code);

        if(cur.getLeft() != null) {
            assignCodeRecursive(cur.getLeft(), code + "0");
        }

        if(cur.getRight() != null) {
            assignCodeRecursive(cur.getRight(), code + "1");
        }
    }


    private void buildHuffmanTree() throws FileNotFoundException {
        storeCharFrequencyInfo();

        if(frequencyInfo.size() == 0) {
            return;
        }
        else if(frequencyInfo.size() == 1) {
            Node tempNode = frequencyInfo.removeMin();
            BinaryTree tempBinaryTree = new BinaryTree(tempNode);

            huffmanTree = tempBinaryTree.addNullRoot();

        } else if (frequencyInfo.size() == 2) {
            Node tempNode1 = frequencyInfo.removeMin();
            Node tempNode2 = frequencyInfo.removeMin();

            BinaryTree tempBinaryTree1 = new BinaryTree(tempNode1);
            BinaryTree tempBinaryTree2 = new BinaryTree(tempNode2);

            huffmanTree = tempBinaryTree1.mergeHuffmanTree(tempBinaryTree2);
        } else {
            Node tempNode1 = frequencyInfo.removeMin();
            Node tempNode2 = frequencyInfo.removeMin();

            BinaryTree tempHuffmanTree;

            BinaryTree tempBinaryTree1 = new BinaryTree(tempNode1);
            BinaryTree tempBinaryTree2 = new BinaryTree(tempNode2);

            tempHuffmanTree = tempBinaryTree1.mergeHuffmanTree(tempBinaryTree2);

            huffmanForest.enqueue(tempHuffmanTree);

            while(huffmanForest.size() != 1 || frequencyInfo.size() != 0) {
                if(frequencyInfo.size() == 0) {
                    tempBinaryTree1 = huffmanForest.dequeue();
                    tempBinaryTree2 = huffmanForest.dequeue();

                    tempHuffmanTree = tempBinaryTree1.mergeHuffmanTree(tempBinaryTree2);
                    huffmanForest.enqueue(tempHuffmanTree);

                } else if (frequencyInfo.size() == 1) {
                    Node tempNode = frequencyInfo.removeMin();
                    tempBinaryTree1 = huffmanForest.dequeue();
                    tempBinaryTree2 = new BinaryTree(tempNode);

                    tempHuffmanTree = tempBinaryTree1.mergeHuffmanTree(tempBinaryTree2);
                    huffmanForest.enqueue(tempHuffmanTree);
                }  else {
                    tempNode1 = frequencyInfo.removeMin();
                    tempNode2 = frequencyInfo.peek();
                    tempBinaryTree1 = huffmanForest.peek();

                    if(tempNode1.getFrequency() + tempNode2.getFrequency()
                            <= tempNode1.getFrequency() + tempBinaryTree1.getWeight()) {

                        tempNode2 = frequencyInfo.removeMin();
                        tempBinaryTree1 = new BinaryTree(tempNode1);
                        tempBinaryTree2 = new BinaryTree(tempNode2);

                        tempHuffmanTree = tempBinaryTree1.mergeHuffmanTree(tempBinaryTree2);
                        huffmanForest.enqueue(tempHuffmanTree);

                    }
                    else {
                        tempBinaryTree1 = new BinaryTree(tempNode1);
                        tempBinaryTree2 = huffmanForest.dequeue();

                        tempHuffmanTree = tempBinaryTree1.mergeHuffmanTree(tempBinaryTree2);
                        huffmanForest.enqueue(tempHuffmanTree);
                    }


                }
            }

            huffmanTree = huffmanForest.dequeue();
        }

    }

    private void storeHuffmanTree() throws FileNotFoundException {
        String name = originFile.getName();
        name = FileNameEditor.addBack(name,"DecodeInfo");

        File file = new File(name);

        PrintWriter output = new PrintWriter(file);

        output.print(huffmanTree.reverseLevelOrder());
        output.close();

    }

    private void storeCharFrequencyInfo() throws FileNotFoundException {
        Scanner input = new Scanner(originFile);

        while (input.hasNextLine()) {
            String curLine = input.nextLine();
            for(int i = 0; i < curLine.length(); i++) {
                frequencyInfo.insert(new Node(curLine.charAt(i), 1));
            }
            if(input.hasNextLine()) {
                frequencyInfo.insert(new Node('\n', 1));
            }
        }

        input.close();

    }


    public static void main(String[] args) throws IOException {
        File file = new File("simple.txt");
        HuffmanEncoder h = new HuffmanEncoder(file);

        h.encode();


        File b = new File("compressed.dat");


        h.decode();

        h.processDecodingInfo("simpleDecodeInfo.txt");

        h.decode();

    }


}
