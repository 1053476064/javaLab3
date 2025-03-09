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
        // Read data file
        String filePath = "org-data-Federal_Government.csv";
        List<String[]> data = readCSV(filePath);
        
        // Console test output
        consoleTest(data);
        
        // Launch GUI
        SwingUtilities.invokeLater(() -> createAndShowGUI(data));
    }

    private static List<String[]> readCSV(String filePath) {
        try (Stream<String> lines = Files.lines(Paths.get(filePath))) {
            return lines.skip(1) // Skip header
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
        if (data.size() >= 10) {
            System.out.println("Tenth data entry: " + String.join(", ", data.get(9)));
        } else {
            System.out.println("Less than 10 data entries available");
        }
        System.out.println("Total number of entries: " + data.size());
        System.out.println("Data source: Department of Government Efficiency, Good job Musk");
    }

    private static void createAndShowGUI(List<String[]> data) {
        JFrame frame = new JFrame("Federal Government Data Visualization");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1200, 700);
        frame.getContentPane().setBackground(Color.BLACK);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.BLACK);

        // Filter panel
        JPanel filterPanel = new JPanel();
        filterPanel.setBackground(Color.BLACK);
        filterPanel.setLayout(new FlowLayout());
        JLabel filterLabel = new JLabel("Search:");
        filterLabel.setForeground(Color.WHITE);
        JTextField filterField = new JTextField(15);
        JButton filterButton = new JButton("Filter");
        filterPanel.add(filterLabel);
        filterPanel.add(filterField);
        filterPanel.add(filterButton);

        // Table panel
        String[] columnNames = {"Field Name", "Value"};
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

        // Statistics panel (Displays Max, Min, Average, Sum)
        double max = data.stream().mapToDouble(row -> parseDouble(row[1])).max().orElse(0);
        double min = data.stream().mapToDouble(row -> parseDouble(row[1])).min().orElse(0);
        double avg = data.stream().mapToDouble(row -> parseDouble(row[1])).average().orElse(0);
        double sum = data.stream().mapToDouble(row -> parseDouble(row[1])).sum();
        
        String[][] statsData = {
            {"Max", formatNumber(String.valueOf(max))},
            {"Min", formatNumber(String.valueOf(min))},
            {"Average", formatNumber(String.valueOf(avg))},
            {"Sum", formatNumber(String.valueOf(sum))}
        };
        
        String[] statsColumns = {"Statistic", "Value"};
        JTable statsTable = new JTable(new DefaultTableModel(statsData, statsColumns));
        statsTable.setBackground(Color.DARK_GRAY);
        statsTable.setForeground(Color.WHITE);
        statsTable.setGridColor(Color.LIGHT_GRAY);
        JScrollPane statsScrollPane = new JScrollPane(statsTable);
        statsScrollPane.setPreferredSize(new Dimension(400, 100));
        
        JPanel statsPanel = new JPanel();
        statsPanel.setBackground(Color.BLACK);
        statsPanel.add(statsScrollPane);
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
            double num = Double.parseDouble(numStr);
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
