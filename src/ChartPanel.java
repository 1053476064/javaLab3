import java.awt.*;
import java.util.List;
import javax.swing.*;

public class ChartPanel extends JPanel {
    private List<String[]> data; // 原始数据：每个元素包含字段名称和数值（字符串）

    public ChartPanel(List<String[]> data) {
        this.data = data;
        this.setBackground(Color.BLACK);
    }
    
    // 更新数据后重绘
    public void updateData(List<String[]> newData) {
        this.data = newData;
        repaint();
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (data == null || data.isEmpty()) {
            return;
        }
        
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.WHITE);
        int width = getWidth();
        int height = getHeight();
        
        // 计算所有数据的最大数值
        double maxVal = data.stream().mapToDouble(row -> DataUtil.parseDouble(row[1])).max().orElse(1);
        
        int barWidth = Math.max(10, width / (data.size() * 2)); // 每个柱子的宽度
        int gap = barWidth; // 柱子之间的间隔
        
        // 绘制标题
        g2d.drawString("数据数值柱状图", 10, 20);
        
        // 绘制每个柱子
        for (int i = 0; i < data.size(); i++) {
            String[] row = data.get(i);
            double value = DataUtil.parseDouble(row[1]);
            int barHeight = (int) ((value / maxVal) * (height - 50));
            int x = 10 + i * (barWidth + gap);
            int y = height - barHeight - 30;
            
            // 绘制柱子
            g2d.fillRect(x, y, barWidth, barHeight);
            
            // 绘制数值标签
            g2d.drawString(DataUtil.formatNumber(row[1]), x, y - 5);
        }
    }
}
