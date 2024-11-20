import javax.swing.*;
import java.io.*;
import java.util.*;

public class Highscores {
    private List<PlayerScore> scores;
    private JFrame outerFrame;

    public Highscores(JFrame outerFrame) {
        this.outerFrame = outerFrame;
        scores = new ArrayList<>();
    }

    public void addScore(PlayerScore player) {
        scores.add(player);

    }

    public void readScores() {
        try {
            FileInputStream inputStream = new FileInputStream("./highscores.txt");
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
            while (true) scores.add((PlayerScore) objectInputStream.readObject());
        } catch (ClassNotFoundException e) {
            System.err.println("Class not found!");
        } catch (EOFException e) {
            System.out.println("Loaded all players");
        } catch (IOException e) {
            System.out.println("File not found");
        }
    }

    public void saveScores() {
        try {
            FileOutputStream outputStream = new FileOutputStream("./highscores.txt");
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
            scores.sort(Collections.reverseOrder());
            for (PlayerScore x : scores) {
                objectOutputStream.writeObject(x);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(outerFrame, "Creating new file");
        }
    }

    public String[] getScoresString() {
        String[] strings = new String[scores.size()];
        for (int i = 0; i < scores.size(); i++) {
            strings[i] = scores.get(i).toString();
        }
        return strings;
    }

}
