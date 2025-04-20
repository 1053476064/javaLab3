import java.awt.*;
import javax.swing.*;

public class BorderedStatsPanel extends StatsDecorator {
    public BorderedStatsPanel(StatsDisplay inner) {
        super(inner);
    }

    @Override
    public JPanel getPanel() {
        JPanel base = super.getPanel();
        base.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        return base;
    }
}
