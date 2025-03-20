import java.util.List;
import javax.swing.*;

public class DataVisualization {
    public static void main(String[] args) {
        String filePath = "org-data-Federal_Government.csv";
        List<String[]> data = DataLoader.readCSV(filePath);
        
        // 控制台测试数据
        ConsoleTest.runTest(data);
        
        // 启动图形界面
        SwingUtilities.invokeLater(() -> {
            DataVisualizationFrame frame = new DataVisualizationFrame(data);
            frame.setVisible(true);
        });
    }
}
