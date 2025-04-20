import javax.swing.JPanel;

public abstract class StatsDecorator implements StatsDisplay {
    protected final StatsDisplay inner;

    public StatsDecorator(StatsDisplay inner) {
        this.inner = inner;
    }

    @Override
    public JPanel getPanel() {
        return inner.getPanel();
    }
}
