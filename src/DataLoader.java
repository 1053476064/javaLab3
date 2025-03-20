import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DataLoader {
    // Read the CSV file and return a list of rows (each row is an array of strings)
    public static List<String[]> readCSV(String filePath) {
        try (Stream<String> lines = Files.lines(Paths.get(filePath))) {
            // Skip the header and split each line by commas
            return lines.skip(1)
                    .map(line -> line.split(","))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            System.err.println("File read failed: " + e.getMessage());
            return List.of();
        }
    }
}
