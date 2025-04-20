import java.awt.*;
import javax.swing.*;

/**
 * I created this decorator so that I can apply a dark theme
 * to any StatsDisplay implementation.
 */
public class DarkModeStatsPanel extends StatsDecorator {
    public DarkModeStatsPanel(StatsDisplay inner) {
        super(inner);
    }

    @Override
    public JPanel getPanel() {
        //For get the base panel and then apply my dark styling
        JPanel base = super.getPanel();
        base.setBackground(Color.DARK_GRAY);

        //For also update the table inside (if present) to match dark mode
        SwingUtilities.invokeLater(() -> {
            for (Component c : base.getComponents()) {
                if (c instanceof JScrollPane) {
                    JScrollPane sp = (JScrollPane) c;
                    Component view = sp.getViewport().getView();
                    if (view instanceof JTable) {
                        JTable tbl = (JTable) view;
                        tbl.setBackground(Color.BLACK);
                        tbl.setForeground(Color.WHITE);
                    }
                }
            }
        });

        return base;
    }
}
