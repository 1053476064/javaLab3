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
        
        JLabel nameLabel = new JLabel("名称包含：");
        nameLabel.setForeground(Color.WHITE);
        nameFilterField = new JTextField(10);
        
        JLabel minLabel = new JLabel("最小值：");
        minLabel.setForeground(Color.WHITE);
        minValueField = new JTextField(5);
        
        JLabel maxLabel = new JLabel("最大值：");
        maxLabel.setForeground(Color.WHITE);
        maxValueField = new JTextField(5);
        
        applyButton = new JButton("应用过滤");
        resetButton = new JButton("清除过滤");
        
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
