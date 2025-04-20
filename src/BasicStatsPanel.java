import java.awt.*;
import java.text.DecimalFormat;
import java.util.List;
import javax.swing.*;

public class BasicStatsPanel implements StatsDisplay {
    private final JPanel panel;

    public BasicStatsPanel(List<String[]> data) {
        //For compute the statistical values
        double max = data.stream().mapToDouble(r -> safeParseDouble(r[1])).max().orElse(0);
        double min = data.stream().mapToDouble(r -> safeParseDouble(r[1])).min().orElse(0);
        double avg = data.stream().mapToDouble(r -> safeParseDouble(r[1])).average().orElse(0);
        double sum = data.stream().mapToDouble(r -> safeParseDouble(r[1])).sum();

        //For format these values and build a JTable
        String[][] statsData = {
            {"Max", formatNumber(max)},
            {"Min", formatNumber(min)},
            {"Average", formatNumber(avg)},
            {"Sum", formatNumber(sum)}
        };
        String[] cols = {"Statistic", "Value"};

        JTable table = new JTable(statsData, cols);
        table.setEnabled(false);
        table.setBackground(Color.DARK_GRAY);
        table.setForeground(Color.WHITE);
        table.setGridColor(Color.LIGHT_GRAY);

        panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.BLACK);
        panel.add(new JScrollPane(table), BorderLayout.CENTER);
    }

    @Override
    public JPanel getPanel() {
        return panel;
    }

    private static double safeParseDouble(String s) {
        try { return Double.parseDouble(s); }
        catch (NumberFormatException e) { return 0; }
    }

    private static String formatNumber(double num) {
        if (num >= 1_000_000_000) return new DecimalFormat("0.0B").format(num / 1_000_000_000.0);
        if (num >= 1_000_000)     return new DecimalFormat("0.0M").format(num / 1_000_000.0);
        if (num >= 1_000)         return new DecimalFormat("0.0K").format(num / 1_000.0);
        return new DecimalFormat("0.##").format(num);
    }
}
