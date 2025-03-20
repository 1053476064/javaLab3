import java.awt.*;
import javax.swing.*;

public class DetailsPanel extends JPanel {
    private JTextArea detailsArea;

    public DetailsPanel() {
        this.setBackground(Color.BLACK);
        this.setLayout(new BorderLayout());
        detailsArea = new JTextArea(5, 30);
        detailsArea.setEditable(false);
        detailsArea.setLineWrap(true);
        detailsArea.setWrapStyleWord(true);
        detailsArea.setBackground(Color.DARK_GRAY);
        detailsArea.setForeground(Color.WHITE);
        JScrollPane scrollPane = new JScrollPane(detailsArea);
        this.add(scrollPane, BorderLayout.CENTER);
    }
    
    // 根据传入数据更新详情显示
    public void updateDetails(String[] rowData) {
        if (rowData == null) {
            detailsArea.setText("");
            return;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < rowData.length; i++) {
            sb.append("属性 ").append(i + 1).append(": ").append(rowData[i]).append("\n");
        }
        detailsArea.setText(sb.toString());
    }
}
