import java.util.List;
import javax.swing.*;

public class StatsObserver implements DataObserver {
    private StatsDisplay currentDisplay;

    public StatsObserver(List<String[]> initialData) {
        //For set up my initial decorator chain: Basic -> Bordered -> DarkMode
        this.currentDisplay = new DarkModeStatsPanel(
                                 new BorderedStatsPanel(
                                   new BasicStatsPanel(initialData)
                                 )
                               );
    }

    //the JPanel that should be added to the GUI layout
    public JPanel getPanel() {
        return currentDisplay.getPanel();
    }

    @Override
    public void onDataChanged(List<String[]> filteredData) {
        //rebuild my decorator chain with the new filtered data
        this.currentDisplay = new DarkModeStatsPanel(
                                 new BorderedStatsPanel(
                                   new BasicStatsPanel(filteredData)
                                 )
                               );
        SwingUtilities.invokeLater(() -> {
            JPanel p = getPanel();
            p.revalidate();
            p.repaint();
        });
    }
}
