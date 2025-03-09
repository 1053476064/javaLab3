import java.awt.*;
import java.awt.event.ActionEvent;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableRowSorter;

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

    private static List<String[]> readCSV(String filePath) {
        try (Stream<String> lines = Files.lines(Paths.get(filePath))) {
            return lines.skip(1) // 跳过表头
                    .map(line -> line.split(","))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            System.err.println("文件读取失败: " + e.getMessage());
            return List.of();
        }
    }

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
        System.out.println("数据来源: Department of Government Efficiency, Good job Musk");
    }

    private static void createAndShowGUI(List<String[]> data) {
        JFrame frame = new JFrame("联邦政府数据可视化");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1200, 700);
        frame.getContentPane().setBackground(Color.BLACK);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.BLACK);

        // 过滤面板
        JPanel filterPanel = new JPanel();
        filterPanel.setBackground(Color.BLACK);
        filterPanel.setLayout(new FlowLayout());
        JLabel filterLabel = new JLabel("搜索:");
        filterLabel.setForeground(Color.WHITE);
        JTextField filterField = new JTextField(15);
        JButton filterButton = new JButton("筛选");
        filterPanel.add(filterLabel);
        filterPanel.add(filterField);
        filterPanel.add(filterButton);

        // 表格面板
        String[] columnNames = {"字段名称", "值"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(tableModel);
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(tableModel);
        table.setRowSorter(sorter);
        table.setRowHeight(25);
        table.setBackground(Color.DARK_GRAY);
        table.setForeground(Color.WHITE);
        table.setGridColor(Color.LIGHT_GRAY);
        
        JTableHeader tableHeader = table.getTableHeader();
        tableHeader.setBackground(Color.BLACK);
        tableHeader.setForeground(Color.WHITE);

        for (String[] row : data) {
            if (row.length >= 2) {
                tableModel.addRow(new String[]{row[0], formatNumber(row[1])});
            }
        }
        JScrollPane scrollPane = new JScrollPane(table);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(filterPanel, BorderLayout.NORTH);

        // 统计面板
        double max = data.stream().mapToDouble(row -> parseDouble(row[1])).max().orElse(0);
        double min = data.stream().mapToDouble(row -> parseDouble(row[1])).min().orElse(0);
        double avg = data.stream().mapToDouble(row -> parseDouble(row[1])).average().orElse(0);
        JLabel statsLabel = new JLabel("最大值: " + max + " | 最小值: " + min + " | 平均值: " + avg);
        statsLabel.setForeground(Color.WHITE);
        JPanel statsPanel = new JPanel();
        statsPanel.setBackground(Color.BLACK);
        statsPanel.add(statsLabel);
        mainPanel.add(statsPanel, BorderLayout.SOUTH);
        
        filterButton.addActionListener((ActionEvent e) -> {
            String text = filterField.getText();
            sorter.setRowFilter(RowFilter.regexFilter(text));
        });

        frame.add(mainPanel);
        frame.setVisible(true);
    }

    private static String formatNumber(String numStr) {
        try {
            long num = Long.parseLong(numStr);
            if (num >= 1_000_000_000) {
                return new DecimalFormat("0.0B").format(num / 1_000_000_000.0);
            } else if (num >= 1_000_000) {
                return new DecimalFormat("0.0M").format(num / 1_000_000.0);
            } else if (num >= 1_000) {
                return new DecimalFormat("0.0K").format(num / 1_000.0);
            } else {
                return numStr;
            }
        } catch (NumberFormatException e) {
            return numStr;
        }
    }

    private static double parseDouble(String numStr) {
        try {
            return Double.parseDouble(numStr);
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}
