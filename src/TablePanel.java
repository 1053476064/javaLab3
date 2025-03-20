import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableRowSorter;

public class TablePanel extends JPanel {
    private JTable table;
    private DefaultTableModel tableModel;
    private TableRowSorter<DefaultTableModel> sorter;

    public TablePanel(List<String[]> data) {
        this.setLayout(new BorderLayout());
        this.setBackground(Color.BLACK);
        
        String[] columnNames = {"Field Name", "Value"};
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
        
        // 填充表格数据，并使用 DataUtil 格式化数字
        for (String[] row : data) {
            if (row.length >= 2) {
                tableModel.addRow(new String[]{row[0], DataUtil.formatNumber(row[1])});
            }
        }
        
        JScrollPane scrollPane = new JScrollPane(table);
        this.add(scrollPane, BorderLayout.CENTER);
    }

    public TableRowSorter<DefaultTableModel> getSorter() {
        return sorter;
    }
}
