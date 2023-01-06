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


    // Constructs a new HuffmanEncoder for decoding purpose
    // Parameters:
    //      None
    public HuffmanEncoder() throws IOException {
        this.bitCount = 0;
    }

    // Constructs a new HuffmanEncoder for encoding purpose
    // Parameters:
    //      File - originalFile: File to be compressed
    public HuffmanEncoder(File originalFile) throws IOException {
        this.bitCount = 0;
        this.originFile = originalFile;
        huffmanForest = new Queue<>();
        frequencyInfo = new MinHeap();
    }


    // Build a Huffman tree based on the character frequency of the input file from constructor
    // Returns:
    //      None
    // Parameters:
    //      None
    private void buildHuffmanTree() throws FileNotFoundException {

        // get frequency info from the file
        extractCharFrequencyInfo();


        //Build Huffman tree
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


    // Write a text file to store the Huffman tree info into the disk for decoding purpose.
    // Returns:
    //      None
    // Parameters:
    //      String - compressedFileName: prefix of the name of the text file.
    private void storeHuffmanTree(String compressedFileName) throws FileNotFoundException {

        // format the name of output file
        String name = FileNameEditor.addBack(compressedFileName,"DecodingInfo");
        name = FileNameEditor.changeSuffix(name, "txt");

        // Modify the output file name is the above name has existed in the directory
        if (FileNameEditor.isFileNameRepeated(name)) {
            name = FileNameEditor.renameRepeatedFile(name);
        }

        // Write the file
        File file = new File(name);

        PrintWriter output = new PrintWriter(file);

        output.print(bitCount + "\n");
        output.print(huffmanTree.reverseLevelOrder());
        output.close();

    }


    // Analyze the character frequency in the file provided and generate a min heap
    // that records the frequency of each character in the file
    // Returns:
    //      None
    // Parameters:
    //      None
    private void extractCharFrequencyInfo() throws FileNotFoundException {
        Scanner input = new Scanner(originFile);

        //Store the frequency info
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


    // Generate a dat file that contains that compresses the info from the original file
    // Returns:
    //      None
    // Parameters:
    //      None
    public void encode() throws IOException {

        // Build the huffman tree based on the input file by user,
        // and assign huffman code to each node
        buildHuffmanTree();
        assignCode();

        // Store the above result into a dat file
        Scanner input = new Scanner(originFile);

        // Modify the name of dat file based on the name of input file
        String name = FileNameEditor.changeSuffix(originFile.getName(), "dat");

        if (FileNameEditor.isFileNameRepeated(name)) {
            name = FileNameEditor.renameRepeatedFile(name);
        }

        File file = new File(name);
        FileOutputStream output = new FileOutputStream(file);

        // perform bitwise operation to write the decoding info into the dat file
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
        storeHuffmanTree(name);
        output.close();

    }


    // Generate a text file which unzip the dat file input by user
    // Returns:
    //      None
    // Parameters:
    //      File - compressedFile: file that to be unzipped
    public void decode(File compressedFile) throws IOException {

        // Generate the name of unzipped file based on compressed file
        String unzippedFileName = FileNameEditor.addBack(compressedFile.getName(),"Unzipped");
        unzippedFileName = FileNameEditor.changeSuffix(unzippedFileName, "txt");


        // Modify the unzipped file name if the name has existed in the directory
        if(FileNameEditor.isFileNameRepeated(unzippedFileName)) {
            unzippedFileName = FileNameEditor.renameRepeatedFile(unzippedFileName);
        }


        // Decode the file based on the huffman tree interpreted previously
        File unzippedFile = new File(unzippedFileName);

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


    // Generate a perfect binary tree that stores the huffman encoding based on the input file
    // Returns:
    //      None
    // Parameters:
    //      File - decodingInfo
    public void processDecodingInfo(File decodingInfo) throws FileNotFoundException {
        File file = decodingInfo;
        Scanner input = new Scanner(file);

        String info = "";
        String bitCount = "";
        boolean isBitCountFetched = false;

        // Extract the text from the input file
        while (input.hasNext()) {
            String cur = input.nextLine();

            if (!isBitCountFetched) {
                for (int i = 0; i < cur.length(); i++) {
                    bitCount += cur.charAt(i);

                }
                isBitCountFetched = true;

            } else {
                for (int i = 0; i < cur.length(); i++) {
                    info += cur.charAt(i);
                }

                info += '\n';

            }
        }

        // Generate the huffman tree based on the string
        this.bitCount = Integer.parseInt(bitCount);
        String[] nodeIndex;
        nodeIndex = info.split("<-");

        int length = Integer.parseInt(nodeIndex[0].substring(1));

        char[] huffmanTree = new char[length + 1];

        for(int i = 0; i < nodeIndex.length - 1; i++) {

            int index = Integer.parseInt(nodeIndex[i].substring(1));

            huffmanTree[index] = nodeIndex[i].charAt(0);
        }


        // Convert the array-based binary tree to node-based
        this.huffmanTree = new BinaryTree(interpretArrayToBinaryTree(huffmanTree, 0));

    }


    // Convert an array based binary tree to Node based binary tree
    // Returns:
    //      None
    // Parameters:
    //      None
    private Node interpretArrayToBinaryTree(char[] huffmanTree, int index) {
        Node cur = null;
        if(index < huffmanTree.length) {
            cur = new Node(huffmanTree[index], 0);

            cur.setLeft(interpretArrayToBinaryTree(huffmanTree, 2 * index + 1));
            cur.setRight(interpretArrayToBinaryTree(huffmanTree, 2 * index + 2));
        }

        return cur;
    }


    // Assign Huffman code to each node in the huffmanTree variable
    // Returns:
    //      None
    // Parameters:
    //      None
    private void assignCode() {
        assignCodeRecursive(huffmanTree.getRoot(), "");
    }


    // Recursive helper method of assignCode
    // Returns:
    //      None
    // Parameters:
    //      None
    private void assignCodeRecursive(Node cur, String code) {
        cur.setCode(code);

        if(cur.getLeft() != null) {
            assignCodeRecursive(cur.getLeft(), code + "0");
        }

        if(cur.getRight() != null) {
            assignCodeRecursive(cur.getRight(), code + "1");
        }
    }





    /*
    public static void main(String[] args) throws IOException {
        File file = new File("simple.txt");
        HuffmanEncoder h = new HuffmanEncoder(file);

        h.encode();


        h.decode();

        h.processDecodingInfo("simpleDecodeInfo.txt");

        h.decode();

    }
    */


}
