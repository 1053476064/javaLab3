import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

/**
 * GUI frame for displaying the data table, filter, stats and chart.
 */
public class DataVisualizationFrame extends JFrame {
    public DataVisualizationFrame(List<String[]> data) {
        super("Federal Government Data Visualization");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 700);
        getContentPane().setBackground(Color.BLACK);

        // Main layout
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.BLACK);

        // --- Filter Panel ---
        JPanel filterPanel = new JPanel(new FlowLayout());
        filterPanel.setBackground(Color.BLACK);
        JLabel filterLabel = new JLabel("Search:");
        filterLabel.setForeground(Color.WHITE);
        JTextField filterField = new JTextField(15);
        filterField.setForeground(Color.WHITE);
        filterField.setBackground(Color.DARK_GRAY);
        JButton filterButton = new JButton("Filter");
        filterPanel.add(filterLabel);
        filterPanel.add(filterField);
        filterPanel.add(filterButton);

        // --- Table Panel ---
        String[] columns = {"Field Name", "Value"};
        DefaultTableModel tableModel = new DefaultTableModel(columns, 0);
        JTable table = new JTable(tableModel);
        table.setBackground(Color.DARK_GRAY);
        table.setForeground(Color.WHITE);
        table.setRowHeight(25);
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(tableModel);
        table.setRowSorter(sorter);
        // Load data
        for (String[] row : data) {
            if (row.length >= 2) {
                tableModel.addRow(new String[]{row[0], row[1]});
            }
        }
        JScrollPane tableScroll = new JScrollPane(table);

        mainPanel.add(filterPanel, BorderLayout.NORTH);
        mainPanel.add(tableScroll, BorderLayout.CENTER);

        // --- Observer Setup ---
        DataFilterSubject subject = new DataFilterSubject();
        ChartPanel chartObserver = new ChartPanel(data);
        StatsObserver statsObserver = new StatsObserver(data);
        subject.addObserver(statsObserver);
        subject.addObserver(chartObserver);

        // Add stats and chart panels
        mainPanel.add(statsObserver.getPanel(), BorderLayout.SOUTH);
        mainPanel.add(chartObserver, BorderLayout.EAST);

        // --- Filter Action ---
        filterButton.addActionListener((ActionEvent e) -> {
            String text = filterField.getText();
            sorter.setRowFilter(RowFilter.regexFilter(text));
            // Collect filtered data from table
            List<String[]> filtered = IntStream.range(0, table.getRowCount())
                .mapToObj(i -> new String[]{
                    table.getValueAt(i, 0).toString(),
                    table.getValueAt(i, 1).toString()
                })
                .collect(Collectors.toList());
            subject.notifyObservers(filtered);
        });

        setContentPane(mainPanel);
    }
}
