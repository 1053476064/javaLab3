import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.List;
import javax.swing.*;

public class ChartPanel extends JPanel implements DataObserver {
    private List<String[]> data;

    public ChartPanel(List<String[]> data) {
        this.data = data;
        setPreferredSize(new Dimension(350, 400));
        setBackground(Color.BLACK);
    }

    @Override
    public void onDataChanged(List<String[]> filteredData) {
        this.data = filteredData;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        //draw nothing if there is no data
        if (data == null || data.isEmpty()) return;

        Graphics2D g2d = (Graphics2D) g;
        int width = getWidth(), height = getHeight();
        int items = Math.min(data.size(), 10);
        int barWidth = width / (items == 0 ? 1 : items);

        //For determine the maximum value for scaling.
        double max = data.stream().limit(items)
                         .mapToDouble(r -> safeParseDouble(r[1]))
                         .max().orElse(1);

        int x = 10;
        for (int i = 0; i < items; i++) {
            double val = safeParseDouble(data.get(i)[1]);
            int barH = (int) (val / max * (height - 50));

            //For draw a white filled bar with a black border.
            g2d.setColor(Color.WHITE);
            g2d.fillRect(x, height - barH - 10, barWidth - 5, barH);
            g2d.setColor(Color.BLACK);
            g2d.drawRect(x, height - barH - 10, barWidth - 5, barH);

            if (i % 2 == 0) {
                AffineTransform orig = g2d.getTransform();
                g2d.setFont(g2d.getFont().deriveFont(10f));
                g2d.setColor(Color.WHITE);
                g2d.setTransform(AffineTransform.getRotateInstance(-Math.PI/4, x + barWidth/2, height - 5));
                g2d.drawString(data.get(i)[0], x, height - 5);
                g2d.setTransform(orig);
            }
            x += barWidth;
        }
    }

    private static double safeParseDouble(String s) {
        try { return Double.parseDouble(s); }
        catch (NumberFormatException e) { return 0; }
    }
}
