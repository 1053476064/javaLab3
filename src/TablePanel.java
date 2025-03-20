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
    // 保存原始数据（顺序与模型行对应）
    private List<String[]> rawData;

    public TablePanel(List<String[]> data) {
        this.setLayout(new BorderLayout());
        this.setBackground(Color.BLACK);
        this.rawData = data;
        
        // 三列：Field Name, Formatted Value, Raw Value（隐藏）
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
        
        // 填充数据：隐藏列存储数值的原始 double 值
        for (String[] row : data) {
            if (row.length >= 2) {
                Object[] rowData = new Object[3];
                rowData[0] = row[0];
                rowData[1] = DataUtil.formatNumber(row[1]);
                rowData[2] = DataUtil.parseDouble(row[1]);
                tableModel.addRow(rowData);
            }
        }
        
        // 隐藏第三列（Raw Value）
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
    
    // 返回过滤后对应的原始数据（注意：模型行索引与原始数据下标对应）
    public List<String[]> getFilteredRawData() {
        List<String[]> filtered = new ArrayList<>();
        for (int i = 0; i < table.getRowCount(); i++) {
            int modelIndex = table.convertRowIndexToModel(i);
            filtered.add(rawData.get(modelIndex));
        }
        return filtered;
    }
}
