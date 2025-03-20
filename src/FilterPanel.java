import java.awt.*;
import javax.swing.*;

public class FilterPanel extends JPanel {
    private JTextField filterField;
    private JButton filterButton;

    public FilterPanel() {
        this.setBackground(Color.BLACK);
        this.setLayout(new FlowLayout());
        
        JLabel filterLabel = new JLabel("Search:");
        filterLabel.setForeground(Color.WHITE);
        
        filterField = new JTextField(15);
        filterButton = new JButton("Filter");
        
        this.add(filterLabel);
        this.add(filterField);
        this.add(filterButton);
    }

    public JTextField getFilterField() {
        return filterField;
    }

    public JButton getFilterButton() {
        return filterButton;
    }
}
