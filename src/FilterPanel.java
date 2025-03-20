import java.awt.*;
import javax.swing.*;

public class FilterPanel extends JPanel {
    private JTextField nameFilterField;
    private JTextField minValueField;
    private JTextField maxValueField;
    private JButton applyButton;
    private JButton resetButton;

    public FilterPanel() {
        this.setBackground(Color.BLACK);
        this.setLayout(new FlowLayout());
        
        JLabel nameLabel = new JLabel("Name contains:");
        nameLabel.setForeground(Color.WHITE);
        nameFilterField = new JTextField(10);
        
        JLabel minLabel = new JLabel("Min value:");
        minLabel.setForeground(Color.WHITE);
        minValueField = new JTextField(5);
        
        JLabel maxLabel = new JLabel("Max value:");
        maxLabel.setForeground(Color.WHITE);
        maxValueField = new JTextField(5);
        
        applyButton = new JButton("Apply Filter");
        resetButton = new JButton("Reset Filter");
        
        this.add(nameLabel);
        this.add(nameFilterField);
        this.add(minLabel);
        this.add(minValueField);
        this.add(maxLabel);
        this.add(maxValueField);
        this.add(applyButton);
        this.add(resetButton);
    }

    public JTextField getNameFilterField() {
        return nameFilterField;
    }

    public JTextField getMinValueField() {
        return minValueField;
    }

    public JTextField getMaxValueField() {
        return maxValueField;
    }

    public JButton getApplyButton() {
        return applyButton;
    }
    
    public JButton getResetButton() {
        return resetButton;
    }
}
