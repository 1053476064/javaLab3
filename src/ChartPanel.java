import java.awt.*;
import java.util.List;
import javax.swing.*;

public class ChartPanel extends JPanel {
    private List<String[]> data; // Each element contains the field name and the number (as a string)

    public ChartPanel(List<String[]> data) {
        this.data = data;
        this.setBackground(Color.BLACK);
    }
    
    // Update data and repaint the chart.
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
        
        // Find the maximum value for scaling the bar heights.
        double maxVal = data.stream().mapToDouble(row -> DataUtil.parseDouble(row[1])).max().orElse(1);
        
        int barWidth = Math.max(10, width / (data.size() * 2)); // Bar width for each data item
        int gap = barWidth; // Gap between bars
        
        // Draw the chart title.
        g2d.drawString("Bar Chart of Values", 10, 20);
        
        // Draw each bar.
        for (int i = 0; i < data.size(); i++) {
            String[] row = data.get(i);
            double value = DataUtil.parseDouble(row[1]);
            int barHeight = (int) ((value / maxVal) * (height - 50));
            int x = 10 + i * (barWidth + gap);
            int y = height - barHeight - 30;
            
            // Draw the bar.
            g2d.fillRect(x, y, barWidth, barHeight);
            
            // Draw the value label above the bar.
            g2d.drawString(DataUtil.formatNumber(row[1]), x, y - 5);
        }
    }
}
