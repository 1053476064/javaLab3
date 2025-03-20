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
    private List<String[]> rawData; // 全部原始数据

    public DataVisualizationFrame(List<String[]> data) {
        super("Federal Government Data Visualization");
        this.rawData = data;
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1200, 700);
        this.getContentPane().setBackground(Color.BLACK);
        
        // 初始化各个面板
        filterPanel = new FilterPanel();
        tablePanel = new TablePanel(data);
        statsPanel = new StatsPanel(data);
        chartPanel = new ChartPanel(data);
        detailsPanel = new DetailsPanel();
        
        // 左侧：表格和详情（上下分割）
        JSplitPane leftSplit = new JSplitPane(JSplitPane.VERTICAL_SPLIT, tablePanel, detailsPanel);
        leftSplit.setDividerLocation(400);
        
        // 右侧：统计与图表（上下分割）
        JSplitPane rightSplit = new JSplitPane(JSplitPane.VERTICAL_SPLIT, statsPanel, chartPanel);
        rightSplit.setDividerLocation(200);
        
        // 主分割面板：左右分割
        JSplitPane mainSplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftSplit, rightSplit);
        mainSplit.setDividerLocation(700);
        
        // 主面板
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.BLACK);
        mainPanel.add(filterPanel, BorderLayout.NORTH);
        mainPanel.add(mainSplit, BorderLayout.CENTER);
        this.add(mainPanel);
        
        // 添加表格选中行监听器，更新详情面板
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
        
        // 过滤按钮动作：构造多个 RowFilter，并更新表格、统计和图表
        filterPanel.getApplyButton().addActionListener(e -> applyFilters());
        filterPanel.getResetButton().addActionListener(e -> resetFilters());
    }
    
    // 根据过滤面板中输入的条件构造 RowFilter
    private void applyFilters() {
        List<RowFilter<Object, Object>> filters = new ArrayList<>();
        // 过滤名称（第一列）
        String nameText = filterPanel.getNameFilterField().getText().trim();
        if (!nameText.isEmpty()) {
            filters.add(RowFilter.regexFilter("(?i)" + nameText, 0));
        }
        // 过滤最小值（隐藏列，索引为 1 之后隐藏的第三列为模型中的索引 2）
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
                // 忽略无效输入
            }
        }
        // 过滤最大值
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
                // 忽略无效输入
            }
        }
        
        RowFilter<Object, Object> combinedFilter = filters.isEmpty() ? null : RowFilter.andFilter(filters);
        tablePanel.getSorter().setRowFilter(combinedFilter);
        updateLinkedPanels();
    }
    
    // 清除过滤条件
    private void resetFilters() {
        filterPanel.getNameFilterField().setText("");
        filterPanel.getMinValueField().setText("");
        filterPanel.getMaxValueField().setText("");
        tablePanel.getSorter().setRowFilter(null);
        updateLinkedPanels();
    }
    
    // 根据当前表格过滤结果更新统计面板和图表面板
    private void updateLinkedPanels() {
        List<String[]> filteredData = tablePanel.getFilteredRawData();
        statsPanel.updateData(filteredData);
        chartPanel.updateData(filteredData);
    }
}
