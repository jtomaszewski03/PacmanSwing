import javax.swing.*;

public class PacmanGameFrame extends JFrame {
    private static int screenSize;
    public PacmanGameFrame(int boardSize) {
        if (boardSize == 10) setSize((boardSize * 30) + 200, (boardSize * 30) + 200);
        else setSize((boardSize * 30) + 100, (boardSize * 30) + 100);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        screenSize = getHeight();
        PacmanPanel pacmanPanel = new PacmanPanel(this, boardSize);
        add(pacmanPanel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new StartFrame());
    }

    public static int getScreenSize() {
        return screenSize;
    }

    public static void setScreenSize(int screenSize) {
        PacmanGameFrame.screenSize = screenSize;
    }
}
