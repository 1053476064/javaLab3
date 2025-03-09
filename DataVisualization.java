import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import org.jfree.chart.*;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import java.awt.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DataVisualization {
    public static void main(String[] args) {
        // 读取数据文件
        String filePath = "org-data-Federal_Government.csv";
        List<String[]> data = readCSV(filePath);
        
        // 控制台测试输出
        consoleTest(data);
        
        // 启动 GUI 界面
        SwingUtilities.invokeLater(() -> createAndShowGUI(data));
    }

    // 读取 CSV 文件
    private static List<String[]> readCSV(String filePath) {
        try (Stream<String> lines = Files.lines(Paths.get(filePath))) {
            return lines.skip(1) // 跳过表头
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
        frame.setSize(1000, 600);

        // 创建主面板
        JPanel mainPanel = new JPanel(new BorderLayout());
        
        // 表格面板
        String[] columnNames = {"字段名称", "值"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(tableModel);
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(tableModel);
        table.setRowSorter(sorter);

        for (String[] row : data) {
            if (row.length >= 2) {
                tableModel.addRow(new String[]{row[0], row[1]});
            }
        }
        JScrollPane scrollPane = new JScrollPane(table);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // 统计面板
        JLabel statsLabel = new JLabel("数据项总数: " + data.size());
        JPanel statsPanel = new JPanel();
        statsPanel.add(statsLabel);
        mainPanel.add(statsPanel, BorderLayout.NORTH);
        
        // 详细信息面板
        JTextArea detailsText = new JTextArea(5, 50);
        detailsText.setEditable(false);
        JScrollPane detailsScroll = new JScrollPane(detailsText);
        mainPanel.add(detailsScroll, BorderLayout.SOUTH);
        
        table.getSelectionModel().addListSelectionListener(event -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                detailsText.setText("详情:\n" + tableModel.getValueAt(selectedRow, 0) + ": " + tableModel.getValueAt(selectedRow, 1));
            }
        });

        // 图表面板
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (String[] row : data) {
            if (row.length >= 2) {
                try {
                    dataset.addValue(Double.parseDouble(row[1]), "Value", row[0]);
                } catch (NumberFormatException ignored) {}
            }
        }
        JFreeChart barChart = ChartFactory.createBarChart("数据分布", "类别", "值", dataset, PlotOrientation.VERTICAL, false, true, false);
        ChartPanel chartPanel = new ChartPanel(barChart);
        mainPanel.add(chartPanel, BorderLayout.EAST);

        frame.add(mainPanel);
        frame.setVisible(true);
    }
}
