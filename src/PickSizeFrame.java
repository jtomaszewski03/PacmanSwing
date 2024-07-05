import javax.swing.*;
import java.awt.*;


public class PickSizeFrame extends JFrame {
    public PickSizeFrame() {
        setSize(200, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        setLayout(new GridLayout(3, 1, 30, 30));
        JLabel chooseLabel = new JLabel("Choose maze size:");
        Integer[] sizes = {10, 15, 20, 25, 30};
        JComboBox<Integer> pickSize = new JComboBox<>(sizes);
        pickSize.setAlignmentY(300);
        pickSize.setBackground(Color.DARK_GRAY);
        pickSize.setForeground(Color.GRAY);
        JButton startButton = new JButton("Start");
        startButton.setBackground(Color.DARK_GRAY);
        startButton.setForeground(Color.GRAY);
        add(chooseLabel);
        add(pickSize);
        add(startButton);
        startButton.addActionListener(e -> {
            new PacmanGameFrame(sizes[pickSize.getSelectedIndex()]);
            dispose();
        });
    }
}
