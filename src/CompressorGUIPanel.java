import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

public class CompressorGUIPanel extends JFrame {


    private JRadioButton writingMode = new JRadioButton("Writing Mode", true);
    private JRadioButton compressingMode = new JRadioButton("Compressing Mode");
    private JRadioButton unzippingMode = new JRadioButton("Unzipping Mode");
    private JButton compress = new JButton("Compress");
    private JButton saveAndCompress = new JButton("Save and Compress");
    private JButton unzip = new JButton("Unzip");

    private JTextField textFileAddressInput = new JTextField();
    private JTextField decodeFileAddressInput = new JTextField();
    private JTextField fileNameInput = new JTextField();

    private JTextArea textInput = new JTextArea();

    public CompressorGUIPanel() {
        this.setLayout(new GridLayout(2, 1));

        JPanel modeSelection = new JPanel();
        modeSelection.setLayout(new GridLayout(1, 3));

        ButtonListener operations = new ButtonListener();

        ButtonGroup modeSelector = new ButtonGroup();
        modeSelector.add(writingMode);
        modeSelector.add(compressingMode);
        modeSelector.add(unzippingMode);
        modeSelection.add(writingMode);
        modeSelection.add(compressingMode);
        modeSelection.add(unzippingMode);
        writingMode.addActionListener(operations);
        compressingMode.addActionListener(operations);
        unzippingMode.addActionListener(operations);


        JPanel textFileAddress = new JPanel();
        textFileAddress.setLayout(new GridLayout(1, 2));
        JLabel textFileAddressLabel = new JLabel("File Address");
        textFileAddress.add(textFileAddressInput);
        textFileAddress.add(textFileAddressLabel);


        JPanel decodeFileAddress = new JPanel();
        decodeFileAddress.setLayout(new GridLayout(1, 2));
        JLabel decodeFileAddressLabel = new JLabel("Decoding File Address");
        decodeFileAddress.add(decodeFileAddressInput);
        decodeFileAddress.add(decodeFileAddressLabel);


        JPanel fileName = new JPanel();
        fileName.setLayout(new GridLayout(1, 2));
        JLabel fileNameLabel = new JLabel("File Name");
        fileName.add(fileNameInput);
        fileName.add(fileNameLabel);


        JLabel textAreaTitle = new JLabel("Text");

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new GridLayout(5, 1));
        infoPanel.add(modeSelection);
        infoPanel.add(textFileAddress);
        infoPanel.add(decodeFileAddress);
        infoPanel.add(fileName);
        infoPanel.add(textAreaTitle);
        this.add(infoPanel);

        JPanel textProcessing = new JPanel();
        textProcessing.setLayout(new FlowLayout());
        textProcessing.add(saveAndCompress);
        textProcessing.add(compress);
        textProcessing.add(unzip);

        compress.addActionListener(operations);
        saveAndCompress.addActionListener(operations);
        unzip.addActionListener(operations);



        JScrollPane textArea = new JScrollPane(textInput);

        JPanel dataManipulatingComponent = new JPanel();
        dataManipulatingComponent.setLayout(new BorderLayout());
        dataManipulatingComponent.add(textArea, BorderLayout.CENTER);
        dataManipulatingComponent.add(textProcessing, BorderLayout.SOUTH);

        this.add(dataManipulatingComponent);

        writingMode();
    }

    class ButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getSource() == compress) {
                System.out.println("compress");
                try {
                    compress();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
            else if(e.getSource() == saveAndCompress) {
                System.out.println("save");
                try {
                    saveAndCompress();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
            else if(e.getSource() == unzip) {
                System.out.println("unzip");
                try {
                    unzip();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
            else if(e.getSource() == compress) {
                System.out.println("compress");
            }

            else if (writingMode.isSelected()) {
                System.out.println("writingMode");
                writingMode();
            }
            else if (compressingMode.isSelected()) {
                System.out.println("compressingMode");
                compressingMode();
            }
            else if (unzippingMode.isSelected()) {
                System.out.println("unzippingMode");
                unzippingMode();
            }


        }
    }

    private void unzip() throws IOException {
        File decodingFile = new File(decodeFileAddressInput.getText());
        File compressedFile = new File(textFileAddressInput.getText());

        if(!decodingFile.exists() || !compressedFile.exists()) {
            FileNotExistMessage fileNotExistMessage = new FileNotExistMessage();
        }

        HuffmanEncoder decoder = new HuffmanEncoder();
        decoder.processDecodingInfo(decodingFile);
        decoder.decode(compressedFile);

        FileSavedMessage confirm = new FileSavedMessage();

    }

    private void compress() throws IOException {
        File file = new File(textFileAddressInput.getText());

        if(!file.exists()) {
            FileNotExistMessage fileNotExistMessage = new FileNotExistMessage();
        }

        HuffmanEncoder encoder = new HuffmanEncoder(file);
        encoder.encode();

        FileSavedMessage confirm = new FileSavedMessage();

    }

    private void saveAndCompress () throws IOException {

        String filename = fileNameInput.getText();
        FileNameEditor.hasSuffix(filename, ".txt");

        if(FileNameEditor.findSuffixPosition(filename) == 0) {
            filename += ".txt";
        }
        else {
            FileNameEditor.changeSuffix(filename, ".txt");
        }

        if(FileNameEditor.isFileNameRepeated(filename)) {
            filename = FileNameEditor.renameRepeatedFile(filename);
        }

        File file = new File(filename);
        PrintWriter output = new PrintWriter(file);

        output.print(textInput.getText());

        output.close();

        HuffmanEncoder encoder = new HuffmanEncoder(file);

        encoder.encode();

        output.close();

        FileSavedMessage confirm = new FileSavedMessage();



    }

    private void unzippingMode() {
        compress.setEnabled(false);
        saveAndCompress.setEnabled(false);
        unzip.setEnabled(true);

        textInput.setEnabled(false);
        textInput.setBackground(Color.LIGHT_GRAY);

        textFileAddressInput.setEnabled(true);
        textFileAddressInput.setBackground(Color.WHITE);

        decodeFileAddressInput.setEnabled(true);
        decodeFileAddressInput.setBackground(Color.WHITE);

        fileNameInput.setEnabled(false);
        fileNameInput.setBackground(Color.LIGHT_GRAY);
    }


    private void compressingMode() {
        unzip.setEnabled(false);
        saveAndCompress.setEnabled(false);
        compress.setEnabled(true);

        textFileAddressInput.setBackground(Color.WHITE);
        textFileAddressInput.setEnabled(true);

        decodeFileAddressInput.setBackground(Color.LIGHT_GRAY);
        decodeFileAddressInput.setEnabled(false);

        fileNameInput.setBackground(Color.LIGHT_GRAY);
        fileNameInput.setEnabled(false);

        textInput.setEnabled(false);
        textInput.setBackground(Color.LIGHT_GRAY);
    }

    private void writingMode() {
        fileNameInput.setEnabled(true);
        fileNameInput.setBackground(Color.WHITE);

        saveAndCompress.setEnabled(true);
        compress.setEnabled(false);
        unzip.setEnabled(false);

        textFileAddressInput.setEnabled(false);
        textFileAddressInput.setBackground(Color.LIGHT_GRAY);

        decodeFileAddressInput.setEnabled(false);
        decodeFileAddressInput.setBackground(Color.LIGHT_GRAY);

        textInput.setEnabled(true);
        textInput.setBackground(Color.WHITE);
    }

    public static void main(String[] args) {
        CompressorGUIPanel huffmanEncodingStarter = new CompressorGUIPanel();
        huffmanEncodingStarter.setTitle("Huffman Encoding");
        huffmanEncodingStarter.setSize(800, 600);
        huffmanEncodingStarter.setLocationRelativeTo(null);
        huffmanEncodingStarter.setDefaultCloseOperation(EXIT_ON_CLOSE);
        huffmanEncodingStarter.setVisible(true);
    }

}
