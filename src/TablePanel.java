import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableRowSorter;

public class TablePanel extends JPanel {
    private JTable table;
    private DefaultTableModel tableModel;
    private TableRowSorter<DefaultTableModel> sorter;
    // Keep original raw data (each row corresponds to the table model row)
    private List<String[]> rawData;

    public TablePanel(List<String[]> data) {
        this.setLayout(new BorderLayout());
        this.setBackground(Color.BLACK);
        this.rawData = data;
        
        // Three columns: Field Name, Formatted Value, and Raw Value (hidden)
        String[] columnNames = {"Field Name", "Value", "Raw Value"};
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);
        
        sorter = new TableRowSorter<>(tableModel);
        table.setRowSorter(sorter);
        
        table.setRowHeight(25);
        table.setBackground(Color.DARK_GRAY);
        table.setForeground(Color.WHITE);
        table.setGridColor(Color.LIGHT_GRAY);
        
        JTableHeader tableHeader = table.getTableHeader();
        tableHeader.setBackground(Color.BLACK);
        tableHeader.setForeground(Color.WHITE);
        
        // Fill the table. The hidden column stores the raw numeric value.
        for (String[] row : data) {
            if (row.length >= 2) {
                Object[] rowData = new Object[3];
                rowData[0] = row[0];
                rowData[1] = DataUtil.formatNumber(row[1]);
                rowData[2] = DataUtil.parseDouble(row[1]);
                tableModel.addRow(rowData);
            }
        }
        
        // Hide the third column (raw value)
        table.removeColumn(table.getColumnModel().getColumn(2));
        
        JScrollPane scrollPane = new JScrollPane(table);
        this.add(scrollPane, BorderLayout.CENTER);
    }

    public JTable getTable() {
        return table;
    }
    
    public TableRowSorter<DefaultTableModel> getSorter() {
        return sorter;
    }
    
    // Get the raw data corresponding to the filtered table rows
    public List<String[]> getFilteredRawData() {
        List<String[]> filtered = new ArrayList<>();
        for (int i = 0; i < table.getRowCount(); i++) {
            int modelIndex = table.convertRowIndexToModel(i);
            filtered.add(rawData.get(modelIndex));
        }
        return filtered;
    }
}
