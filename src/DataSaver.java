import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class DataSaver {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        ArrayList<String> csvRecords = new ArrayList<>();
        boolean done = false;

        System.out.println("*** CSV Data Entry Program ***");

        // Loop data input routine using SafeInput
        while (!done) {
            String firstName = SafeInput.getNonZeroLenString(in, "Enter First Name");
            String lastName = SafeInput.getNonZeroLenString(in, "Enter Last Name");

            // Format ID Number as a 6-digit zero-padded string
            int idNumInt = SafeInput.getRangedInt(in, "Enter ID Number (1-999999)", 1, 999999);
            String idNum = String.format("%06d", idNumInt);

            String email = SafeInput.getNonZeroLenString(in, "Enter Email");
            int yearOfBirth = SafeInput.getRangedInt(in, "Enter Year of Birth", 1000, 2026);

            // Create CSV Record with the format: FirstName, LastName, IDNumber, Email, YearOfBirth
            String csvRecord = String.format("%s, %s, %s, %s, %d", firstName, lastName, idNum, email, yearOfBirth);
            csvRecords.add(csvRecord);

            // Ask user if they wish to add another record
            done = !SafeInput.getYNConfirm(in, "Do you want to add another record?");
        }

        // Prompt for output filename and automatically add .csv extension
        String fileName = SafeInput.getNonZeroLenString(in, "Enter file name to save");
        if (!fileName.toLowerCase().endsWith(".csv")) {
            fileName += ".csv";
        }

        // Make sure file target path points directly into the 'src' directory
        File workingDirectory = new File(System.getProperty("user.dir"));
        Path targetPath = Paths.get(workingDirectory.getPath(), "src", fileName);

        // Write CSV records
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(targetPath.toFile()))) {
            for (String record : csvRecords) {
                writer.write(record);
                writer.newLine();
            }
            System.out.println("\nSuccessfully saved " + csvRecords.size() + " record(s) to: " + targetPath.toAbsolutePath());
        } catch (IOException e) {
            System.out.println("An I/O exception occurred while saving the file.");
            e.printStackTrace();
        }
    }
}
