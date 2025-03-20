import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class StatsPanel extends JPanel {
    public StatsPanel(List<String[]> data) {
        this.setBackground(Color.BLACK);
        
        // 计算统计数据
        double max = data.stream().mapToDouble(row -> DataUtil.parseDouble(row[1])).max().orElse(0);
        double min = data.stream().mapToDouble(row -> DataUtil.parseDouble(row[1])).min().orElse(0);
        double avg = data.stream().mapToDouble(row -> DataUtil.parseDouble(row[1])).average().orElse(0);
        double sum = data.stream().mapToDouble(row -> DataUtil.parseDouble(row[1])).sum();
        
        String[][] statsData = {
            {"Max", DataUtil.formatNumber(String.valueOf(max))},
            {"Min", DataUtil.formatNumber(String.valueOf(min))},
            {"Average", DataUtil.formatNumber(String.valueOf(avg))},
            {"Sum", DataUtil.formatNumber(String.valueOf(sum))}
        };
        
        String[] statsColumns = {"Statistic", "Value"};
        DefaultTableModel statsModel = new DefaultTableModel(statsData, statsColumns);
        JTable statsTable = new JTable(statsModel);
        
        statsTable.setBackground(Color.DARK_GRAY);
        statsTable.setForeground(Color.WHITE);
        statsTable.setGridColor(Color.LIGHT_GRAY);
        
        JScrollPane statsScrollPane = new JScrollPane(statsTable);
        statsScrollPane.setPreferredSize(new Dimension(400, 100));
        this.add(statsScrollPane);
    }
}
