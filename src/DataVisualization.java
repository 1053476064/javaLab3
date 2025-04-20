import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.swing.SwingUtilities;

public class DataVisualization {
    public static void main(String[] args) {
        String filePath = "org-data-Federal_Government.csv";
        List<String[]> data = readCSV(filePath);

        consoleTest(data);

        SwingUtilities.invokeLater(() -> {
            DataVisualizationFrame frame = new DataVisualizationFrame(data);
            frame.setVisible(true);
        });
    }

    private static List<String[]> readCSV(String filePath) {
        try (Stream<String> lines = Files.lines(Paths.get(filePath))) {
            return lines.skip(1) // skip header
                        .map(line -> line.split(","))
                        .collect(Collectors.toList());
        } catch (Exception e) {
            System.err.println("File read failed: " + e.getMessage());
            return List.of();
        }
    }

    private static void consoleTest(List<String[]> data) {
        if (data.isEmpty()) {
            System.out.println("No data available for testing");
            return;
        }
        System.out.println("First data entry: " + String.join(", ", data.get(0)));
        System.out.println(data.size() >= 10
            ? "Tenth data entry: " + String.join(", ", data.get(9))
            : "Less than 10 data entries available");
        System.out.println("Total number of entries: " + data.size());
        System.out.println("Data source: Department of Government Efficiency, Good job Musk");
    }
}
