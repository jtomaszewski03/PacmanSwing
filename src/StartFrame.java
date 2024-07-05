import javax.swing.*;
import java.awt.*;

public class StartFrame extends JFrame {
    public StartFrame() {
        setTitle("Pacman");
        setSize(500, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        JPanel startPanel = new JPanel(new GridLayout());
        Font menuFont = new Font("Arial", Font.BOLD, 20);
        JButton startButton = new JButton("New");
        startButton.setBackground(Color.DARK_GRAY);
        startButton.setForeground(Color.GRAY);
        startButton.setFont(menuFont);
        JButton highscoresButton = new JButton("Highscores");
        highscoresButton.setBackground(Color.DARK_GRAY);
        highscoresButton.setForeground(Color.GRAY);
        highscoresButton.setFont(menuFont);
        JButton exitButton = new JButton("Exit");
        exitButton.setBackground(Color.DARK_GRAY);
        exitButton.setForeground(Color.GRAY);
        exitButton.setFont(menuFont);
        startPanel.add(startButton);

        startButton.addActionListener(e -> {
            new PickSizeFrame();
            dispose();
        });
        startPanel.add(highscoresButton);

        highscoresButton.addActionListener(e -> {
            JFrame highscoreFrame = new JFrame("Highscores");
            highscoreFrame.setLayout(new BorderLayout());
            highscoreFrame.setSize(250, 250);
            highscoreFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            highscoreFrame.setLocationRelativeTo(null);

            Highscores highscores = new Highscores(highscoreFrame);
            highscores.readScores();
            JPanel hspanel = new JPanel();
            JList<String> list = new JList<>(highscores.getScoresString());
            hspanel.add(list);
            JScrollPane scrollPane = new JScrollPane(hspanel);
            scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);


            highscoreFrame.add(scrollPane);
            highscoreFrame.add(scrollPane);
            highscoreFrame.setVisible(true);

        });
        exitButton.addActionListener(e -> {
            dispose();
        });
        startPanel.add(exitButton);
        add(startPanel);
    }
}
