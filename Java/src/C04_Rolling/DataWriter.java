package C04_Rolling;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class DataWriter {
    public static void writeDataToFile(List<String> data, String filepath) throws IOException {
        Path outputFilePath = Paths.get(filepath);
        Files.deleteIfExists(outputFilePath);
        Files.createFile(outputFilePath);
        BufferedWriter writer = new BufferedWriter(new FileWriter(filepath, true));

        for (String str : data) {
            writer.append(str).append("\n");
        }

        writer.close();
        System.out.println("Data was written to file, path: " + outputFilePath.toAbsolutePath());
    }
}
