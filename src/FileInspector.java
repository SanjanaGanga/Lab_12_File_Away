import javax.swing.JFileChooser;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileInspector {

    public static void main(String[] args) {
        List<String> lines = new ArrayList<>();

        int lineCount = 0;
        int wordCount = 0;
        int charCount = 0;

        JFileChooser chooser = new JFileChooser();

        // Open JFileChooser directly in the 'src' directory
        File workingDirectory = new File(System.getProperty("user.dir"));
        chooser.setCurrentDirectory(new File(workingDirectory, "src"));

        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            File selectedFile = chooser.getSelectedFile();

            // Read the file line by line with try-catch for error handling
            try (BufferedReader br = new BufferedReader(new FileReader(selectedFile))) {
                String line;

                while ((line = br.readLine()) != null) {
                    lines.add(line);
                    lineCount++;

                    //Length of the line string
                    charCount += line.length();

                    // Count words using split on whitespace
                    if (!line.trim().isEmpty()) {
                        String[] words = line.trim().split("\\s+");
                        wordCount += words.length;
                    }
                }
            } catch (FileNotFoundException e) {
                System.out.println("File not found: " + e.getMessage());
                e.printStackTrace();
            } catch (IOException e) {
                System.out.println("An I/O Exception occurred while reading the file.");
                e.printStackTrace();
            }

            // Echo all lines to the screen
            System.out.println("--- FILE CONTENTS ---");
            for (String l : lines) {
                System.out.println(l);
            }

            // Output the summary report
            System.out.println("\n*********************************");
            System.out.println("        SUMMARY REPORT           ");
            System.out.println("*********************************");
            System.out.println("File Name:        " + selectedFile.getName());
            System.out.println("Number of Lines:  " + lineCount);
            System.out.println("Number of Words:  " + wordCount);
            System.out.println("Number of Chars:  " + charCount);
            System.out.println("*********************************");

        } else {
            System.out.println("No file selected. Exiting program.");
        }
    }
}