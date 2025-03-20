import java.util.List;
import javax.swing.*;

public class DataVisualization {
    public static void main(String[] args) {
        String filePath = "org-data-Federal_Government.csv";
        List<String[]> data = DataLoader.readCSV(filePath);
        
        // Run a console test to check the data
        ConsoleTest.runTest(data);
        
        // Launch the GUI on the Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            DataVisualizationFrame frame = new DataVisualizationFrame(data);
            frame.setVisible(true);
        });
    }
}
