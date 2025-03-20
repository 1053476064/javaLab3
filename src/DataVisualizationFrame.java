import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class DataVisualizationFrame extends JFrame {
    public DataVisualizationFrame(List<String[]> data) {
        super("Federal Government Data Visualization");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1200, 700);
        this.getContentPane().setBackground(Color.BLACK);
        
        // 主面板，使用 BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.BLACK);
        
        // 创建并添加过滤面板
        FilterPanel filterPanel = new FilterPanel();
        mainPanel.add(filterPanel, BorderLayout.NORTH);
        
        // 创建并添加数据表格面板
        TablePanel tablePanel = new TablePanel(data);
        mainPanel.add(tablePanel, BorderLayout.CENTER);
        
        // 创建并添加统计数据面板
        StatsPanel statsPanel = new StatsPanel(data);
        mainPanel.add(statsPanel, BorderLayout.SOUTH);
        
        // 为过滤按钮添加动作：对表格数据进行过滤
        filterPanel.getFilterButton().addActionListener((ActionEvent e) -> {
            String text = filterPanel.getFilterField().getText();
            tablePanel.getSorter().setRowFilter(RowFilter.regexFilter(text));
        });
        
        this.add(mainPanel);
    }
}
