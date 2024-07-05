import javax.swing.*;
import java.awt.*;

public class FinishFrame extends JFrame {
    public FinishFrame(int score, double playingTime) {
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        JPanel resultsPanel = new JPanel(new GridLayout(3, 1));
        JPanel infoPanel = new JPanel(new FlowLayout());

        JPanel typePanel = new JPanel(new FlowLayout());
        JLabel timeLabel = new JLabel("Total time: " + Math.round(playingTime * 10) / 10.0);
        JPanel enterPanel = new JPanel(new GridLayout(2, 1));

        JLabel scoreLabel = new JLabel("Your score: " + score);
        JLabel textLabel = new JLabel("Type your nickname");
        infoPanel.add(scoreLabel);
        typePanel.add(textLabel);
        resultsPanel.add(infoPanel);
        resultsPanel.add(typePanel);
        resultsPanel.add(enterPanel);

        JTextField name = new JTextField();
        JButton enter = new JButton("Finish");
        infoPanel.add(timeLabel);
        enterPanel.add(name);
        enterPanel.add(enter);
        add(resultsPanel);
        enter.addActionListener(e -> {
            PlayerScore ps = new PlayerScore(name.getText(), score);
            Highscores highscores = new Highscores(this);
            highscores.readScores();
            highscores.addScore(ps);
            highscores.saveScores();
            dispose();
            new StartFrame();
        });


    }
}
