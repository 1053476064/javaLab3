import java.awt.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
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
        System.out.println("数据来源: Department of Government Efficiency, Good job Musk");
    }

    // 创建 GUI 界面
    private static void createAndShowGUI(List<String[]> data) {
        JFrame frame = new JFrame("联邦政府数据可视化");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 600);

        JPanel mainPanel = new JPanel(new BorderLayout());
        
        // 表格面板
        String[] columnNames = {"字段名称", "值"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(tableModel);
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(tableModel);
        table.setRowSorter(sorter);
        table.setRowHeight(25); // 提高行高，提升可读性

        for (String[] row : data) {
            if (row.length >= 2) {
                tableModel.addRow(new String[]{row[0], formatNumber(row[1])});
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

        // 自定义图表面板
        ChartPanel chartPanel = new ChartPanel(data);
        mainPanel.add(chartPanel, BorderLayout.EAST);

        frame.add(mainPanel);
        frame.setVisible(true);
    }
    
    // 格式化大数值数据
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
}

// 自定义柱状图面板
class ChartPanel extends JPanel {
    private final List<String[]> data;
    
    public ChartPanel(List<String[]> data) {
        this.data = data;
        this.setPreferredSize(new Dimension(350, 400));
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setFont(new Font("SansSerif", Font.PLAIN, 10));

        int width = getWidth();
        int height = getHeight();
        int barWidth = width / Math.min(data.size(), 10);

        int maxValue = data.stream().skip(1)
                .mapToInt(row -> {
                    try { return Integer.parseInt(row[1]); }
                    catch (NumberFormatException e) { return 0; }
                })
                .max().orElse(1);

        int x = 10;
        for (int i = 0; i < Math.min(data.size(), 10); i++) {
            try {
                int value = Integer.parseInt(data.get(i)[1]);
                int barHeight = (int) ((double) value / maxValue * (height - 50));
                g2d.setColor(new Color(0, 0, 255 - (i * 20)));
                g2d.fillRect(x, height - barHeight - 10, barWidth - 5, barHeight);
                g2d.setColor(Color.BLACK);
                g2d.drawRect(x, height - barHeight - 10, barWidth - 5, barHeight);
                if (i % 2 == 0) {
                    g2d.drawString(data.get(i)[0], x, height - 5);
                }
                x += barWidth;
            } catch (NumberFormatException ignored) {}
        }
    }
}