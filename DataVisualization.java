import java.awt.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class DataVisualization {
    public static void main(String[] args) {
        // 读取数据文件
        String filePath = "org-data-Federal_Government.csv"; // 确保文件路径正确
        List<String[]> data = readCSV(filePath);
        
        // 控制台测试输出
        consoleTest(data);
        
        // 启动 GUI 界面
        SwingUtilities.invokeLater(() -> createAndShowGUI(data));
    }

    // 读取 CSV 文件
    private static List<String[]> readCSV(String filePath) {
        try {
            return Files.lines(Paths.get(filePath))
                    .skip(1) // 跳过表头
                    .map(line -> line.split(","))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            System.err.println("文件读取失败: " + e.getMessage());
            return List.of();
        }
    }

    // 控制台测试应用
    private static void consoleTest(List<String[]> data) {
        if (data.isEmpty()) {
            System.out.println("数据为空，无法进行测试");
            return;
        }
        
        System.out.println("第一条数据: " + String.join(", ", data.get(0)));
        if (data.size() >= 10) {
            System.out.println("第十条数据: " + String.join(", ", data.get(9)));
        } else {
            System.out.println("数据不足 10 条");
        }
        System.out.println("总数据量: " + data.size());
    }

    // 创建 GUI 界面
    private static void createAndShowGUI(List<String[]> data) {
        JFrame frame = new JFrame("联邦政府数据可视化");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        String[] columnNames = {"字段名称", "值"}; // 适配 CSV 数据
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);

        // 填充表格数据
        for (String[] row : data) {
            if (row.length >= 2) {
                tableModel.addRow(new String[]{row[0], row[1]});
            }
        }

        JTable table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        frame.add(scrollPane, BorderLayout.CENTER);
        
        frame.setVisible(true);
    }
}
