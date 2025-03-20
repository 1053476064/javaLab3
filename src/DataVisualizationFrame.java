import javax.swing.*;
import javax.swing.RowFilter;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class DataVisualizationFrame extends JFrame {
    private FilterPanel filterPanel;
    private TablePanel tablePanel;
    private StatsPanel statsPanel;
    private ChartPanel chartPanel;
    private DetailsPanel detailsPanel;
    private List<String[]> rawData; // All original data

    public DataVisualizationFrame(List<String[]> data) {
        super("Federal Government Data Visualization");
        this.rawData = data;
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1200, 700);
        this.getContentPane().setBackground(Color.BLACK);
        
        // Initialize all panels
        filterPanel = new FilterPanel();
        tablePanel = new TablePanel(data);
        statsPanel = new StatsPanel(data);
        chartPanel = new ChartPanel(data);
        detailsPanel = new DetailsPanel();
        
        // Left side: Table and Details (vertical split)
        JSplitPane leftSplit = new JSplitPane(JSplitPane.VERTICAL_SPLIT, tablePanel, detailsPanel);
        leftSplit.setDividerLocation(400);
        
        // Right side: Stats and Chart (vertical split)
        JSplitPane rightSplit = new JSplitPane(JSplitPane.VERTICAL_SPLIT, statsPanel, chartPanel);
        rightSplit.setDividerLocation(200);
        
        // Main split pane: left and right
        JSplitPane mainSplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftSplit, rightSplit);
        mainSplit.setDividerLocation(700);
        
        // Main panel with border layout
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.BLACK);
        mainPanel.add(filterPanel, BorderLayout.NORTH);
        mainPanel.add(mainSplit, BorderLayout.CENTER);
        this.add(mainPanel);
        
        // Add a listener to update the details panel when a table row is selected.
        tablePanel.getTable().getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    int selectedRow = tablePanel.getTable().getSelectedRow();
                    if (selectedRow >= 0) {
                        int modelIndex = tablePanel.getTable().convertRowIndexToModel(selectedRow);
                        String[] rowData = rawData.get(modelIndex);
                        detailsPanel.updateDetails(rowData);
                    }
                }
            }
        });
        
        // Set up filter actions: apply or reset filters and update other panels.
        filterPanel.getApplyButton().addActionListener(e -> applyFilters());
        filterPanel.getResetButton().addActionListener(e -> resetFilters());
    }
    
    // Build a combined RowFilter based on filter inputs.
    private void applyFilters() {
        List<RowFilter<Object, Object>> filters = new ArrayList<>();
        // Filter by name (first column)
        String nameText = filterPanel.getNameFilterField().getText().trim();
        if (!nameText.isEmpty()) {
            filters.add(RowFilter.regexFilter("(?i)" + nameText, 0));
        }
        // Filter by minimum value (hidden column at model index 2)
        String minText = filterPanel.getMinValueField().getText().trim();
        if (!minText.isEmpty()) {
            try {
                double minVal = Double.parseDouble(minText);
                filters.add(new RowFilter<Object, Object>() {
                    @Override
                    public boolean include(Entry<? extends Object, ? extends Object> entry) {
                        Double value = (Double) entry.getValue(2);
                        return value >= minVal;
                    }
                });
            } catch (NumberFormatException ex) {
                // Ignore invalid input.
            }
        }
        // Filter by maximum value
        String maxText = filterPanel.getMaxValueField().getText().trim();
        if (!maxText.isEmpty()) {
            try {
                double maxVal = Double.parseDouble(maxText);
                filters.add(new RowFilter<Object, Object>() {
                    @Override
                    public boolean include(Entry<? extends Object, ? extends Object> entry) {
                        Double value = (Double) entry.getValue(2);
                        return value <= maxVal;
                    }
                });
            } catch (NumberFormatException ex) {
                // Ignore invalid input.
            }
        }
        
        RowFilter<Object, Object> combinedFilter = filters.isEmpty() ? null : RowFilter.andFilter(filters);
        tablePanel.getSorter().setRowFilter(combinedFilter);
        updateLinkedPanels();
    }
    
    // Clear all filter inputs and reset the table filter.
    private void resetFilters() {
        filterPanel.getNameFilterField().setText("");
        filterPanel.getMinValueField().setText("");
        filterPanel.getMaxValueField().setText("");
        tablePanel.getSorter().setRowFilter(null);
        updateLinkedPanels();
    }
    
    // Update the statistics and chart panels based on the current filtered data.
    private void updateLinkedPanels() {
        List<String[]> filteredData = tablePanel.getFilteredRawData();
        statsPanel.updateData(filteredData);
        chartPanel.updateData(filteredData);
    }
}
