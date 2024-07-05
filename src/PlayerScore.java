import javax.swing.*;
import java.io.*;
import java.util.List;

public class PlayerScore implements Serializable, Comparable<PlayerScore> {
    private String nick;
    private int score;

    public PlayerScore(String nick, int score) {
        this.nick = nick;
        this.score = score;
    }

    @Override
    public int compareTo(PlayerScore o) {
        return this.score - o.score;
    }

    @Override
    public String toString() {
        return "Player - " + nick + ":  " + score + "\n";
    }

    public String getNick() {
        return nick;
    }

    public int getScore() {
        return score;
    }
}
