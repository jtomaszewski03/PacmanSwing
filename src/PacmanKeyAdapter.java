import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class PacmanKeyAdapter extends KeyAdapter {

    private Board.Direction direction;

    public PacmanKeyAdapter(Board board) {
        direction = Board.Direction.NULL;
    }


    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT -> direction = Board.Direction.LEFT;
            case KeyEvent.VK_RIGHT -> direction = Board.Direction.RIGHT;
            case KeyEvent.VK_UP -> direction = Board.Direction.UP;
            case KeyEvent.VK_DOWN -> direction = Board.Direction.DOWN;
        }
    }

    public Board.Direction getDirection() {
        return direction;
    }
}
