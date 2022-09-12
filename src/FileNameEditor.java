import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class FileNameEditor {

    private String fileName;

    public FileNameEditor() {
    }

    public static boolean hasSuffix(String fileName, String suffix) {

        int suffixPosition = findSuffixPosition(fileName);
        if(suffixPosition == 0) {
            return false;
        }



        String curSuffix = fileName.substring(suffixPosition);
        return curSuffix.equals(suffix);


    }

    public static String addFront(String fileName, String text) {
        return text + fileName;

    }

    public static String addBack(String fileName, String text) {
        int suffixPosition = findSuffixPosition(fileName);

        String renamePart1 = fileName.substring(0, suffixPosition);
        String renamePart2 = fileName.substring(suffixPosition);

        return renamePart1 + text + renamePart2;

    }

    public static int findSuffixPosition(String fileName) {
        int position = 0;
        for (int i = 0; i < fileName.length(); i++) {
            if(fileName.charAt(i) == '.') {
                position = i;
            }
        }

        return position;
    }

    public static String changeSuffix(String fileName, String replaceSuffix) {

        int position = findSuffixPosition(fileName);

        String result = fileName;

        result = result.substring(0, position + 1);

        result += replaceSuffix;

        return result;
    }

    public static boolean isFileNameRepeated(String fileName) {
        File file = new File(fileName);

        if(file.exists()) {
            return true;
        }

        return false;
    }

    public static String renameRepeatedFile(String fileName) {

        int num = 1;
        String repeatIndex = " (" + num + ")";
        String result = FileNameEditor.addBack(fileName, repeatIndex);

        File file = new File(result);

        while (file.exists()) {
            repeatIndex = " (" + num + ")";
            result = FileNameEditor.addBack(fileName, repeatIndex);

            file = new File(result);
            num++;
        }

        return result;
    }

    /*
    public static void main(String[] args) throws FileNotFoundException {
        String fileName = "test.txt";
        File file;

        if(FileNameEditor.isFileNameRepeated(fileName)) {
            file = new File(FileNameEditor.renameRepeatedFile(fileName));
        }
        else {
            file = new File(fileName);
        }

        PrintWriter output = new PrintWriter(file);
        output.write("haha");

        output.close();
    }*/
}
