import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class StatsPanel extends JPanel {
    private DefaultTableModel statsModel;
    private JTable statsTable;

    public StatsPanel(List<String[]> data) {
        this.setBackground(Color.BLACK);
        String[] statsColumns = {"Statistic", "Value"};
        String[][] statsData = calculateStats(data);
        statsModel = new DefaultTableModel(statsData, statsColumns);
        statsTable = new JTable(statsModel);
        
        statsTable.setBackground(Color.DARK_GRAY);
        statsTable.setForeground(Color.WHITE);
        statsTable.setGridColor(Color.LIGHT_GRAY);
        
        JScrollPane statsScrollPane = new JScrollPane(statsTable);
        statsScrollPane.setPreferredSize(new Dimension(400, 100));
        this.add(statsScrollPane);
    }
    
    // Calculate statistics: max, min, average, and sum.
    private String[][] calculateStats(List<String[]> data) {
        double max = data.stream().mapToDouble(row -> DataUtil.parseDouble(row[1])).max().orElse(0);
        double min = data.stream().mapToDouble(row -> DataUtil.parseDouble(row[1])).min().orElse(0);
        double avg = data.stream().mapToDouble(row -> DataUtil.parseDouble(row[1])).average().orElse(0);
        double sum = data.stream().mapToDouble(row -> DataUtil.parseDouble(row[1])).sum();
        
        return new String[][] {
            {"Max", DataUtil.formatNumber(String.valueOf(max))},
            {"Min", DataUtil.formatNumber(String.valueOf(min))},
            {"Average", DataUtil.formatNumber(String.valueOf(avg))},
            {"Sum", DataUtil.formatNumber(String.valueOf(sum))}
        };
    }
    
    // Update statistics when filtered data changes.
    public void updateData(List<String[]> newData) {
        String[][] newStats = calculateStats(newData);
        statsModel.setDataVector(newStats, new Object[]{"Statistic", "Value"});
    }
}
