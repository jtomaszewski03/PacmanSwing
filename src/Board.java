import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class Board {
    public enum Direction {
        LEFT, RIGHT, UP, DOWN, NULL
    }

    private int[][] board;
    private JLabel[][] labels;
    private JFrame outerFrame;
    boolean pacmanSpedUp = false;
    boolean enemyFreeze = false;
    boolean enemySlow = false;
    boolean pacmanEaten = false;
    private int pacmanAnim = 0;
    private Direction redDir = Direction.RIGHT;
    private Direction yellowDir = Direction.LEFT;
    private Direction greenDir = Direction.RIGHT;
    private int tempRed, tempYellow, tempGreen, foodEaten;
    private final int boardSize;

    //0 - pacman ; 1 - wall ; 2 - food ; 3 - empty ; 4 - redEnemy ; 5 - yellowEnemy ; 6 - greenEnemy
    //7 - freeze ; 8 - slow ; 9 - hundred_points ; 10 - multiply_points_star ; 11 - lightning


    public Board(JLabel[][] labels, int size, JFrame outerFrame) {
        this.outerFrame = outerFrame;
        this.boardSize = size;
        this.labels = labels;
        foodEaten = 0;

        switch (size) {


            case 10 -> board = new int[][]{
                    {1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                    {1, 4, 2, 2, 2, 2, 2, 2, 2, 1},
                    {1, 2, 1, 1, 2, 1, 2, 1, 2, 1},
                    {1, 2, 1, 1, 2, 1, 2, 1, 2, 1},
                    {1, 2, 1, 1, 2, 1, 2, 1, 2, 1},
                    {1, 2, 2, 2, 2, 0, 2, 2, 2, 1},
                    {1, 2, 1, 1, 2, 1, 2, 1, 2, 1},
                    {1, 2, 1, 1, 2, 1, 2, 1, 2, 1},
                    {1, 2, 2, 2, 2, 2, 2, 2, 2, 1},
                    {1, 1, 1, 1, 1, 1, 1, 1, 1, 1}
            };
            case 15 -> board = new int[][]{
                    {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                    {1, 4, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1},
                    {1, 2, 1, 1, 2, 1, 1, 2, 1, 2, 1, 2, 1, 2, 1},
                    {1, 2, 1, 1, 2, 1, 2, 2, 1, 2, 1, 2, 1, 2, 1},
                    {1, 2, 1, 1, 2, 1, 2, 1, 1, 1, 1, 2, 2, 2, 1},
                    {1, 2, 2, 2, 2, 0, 2, 1, 1, 1, 1, 1, 2, 2, 1},
                    {1, 2, 1, 1, 2, 1, 2, 2, 1, 2, 1, 1, 1, 2, 1},
                    {1, 2, 2, 1, 2, 1, 2, 2, 1, 2, 1, 2, 2, 2, 1},
                    {1, 1, 2, 1, 2, 1, 2, 2, 1, 2, 2, 2, 1, 2, 1},
                    {1, 2, 2, 1, 2, 1, 2, 2, 2, 2, 1, 1, 1, 5, 1},
                    {1, 2, 1, 1, 2, 1, 2, 1, 1, 2, 1, 2, 2, 2, 1},
                    {1, 2, 1, 1, 2, 1, 2, 1, 1, 2, 1, 2, 1, 2, 1},
                    {1, 2, 2, 2, 2, 1, 2, 1, 1, 2, 2, 2, 2, 2, 1},
                    {1, 6, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1, 2, 2, 1},
                    {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}
            };
            case 20 -> board = new int[][]{
                    {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                    {1, 4, 2, 2, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1},
                    {1, 2, 1, 2, 2, 2, 2, 2, 2, 1, 1, 1, 1, 1, 2, 1, 1, 1, 2, 1},
                    {1, 2, 1, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 2, 1, 1, 1, 2, 1},
                    {1, 2, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 1, 1, 1, 2, 1},
                    {1, 2, 1, 1, 1, 1, 1, 1, 2, 1, 1, 2, 1, 1, 2, 2, 2, 2, 2, 1},
                    {1, 2, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 1, 1, 1, 1, 1, 1, 2, 1},
                    {1, 2, 2, 2, 2, 2, 2, 2, 2, 1, 1, 0, 1, 1, 1, 1, 1, 1, 2, 1},
                    {1, 2, 1, 1, 2, 1, 1, 1, 2, 1, 1, 2, 1, 1, 1, 2, 2, 1, 2, 1},
                    {1, 2, 1, 1, 2, 1, 1, 1, 2, 1, 1, 2, 1, 2, 2, 2, 2, 1, 2, 1},
                    {1, 2, 1, 1, 2, 1, 1, 1, 2, 1, 1, 2, 1, 2, 2, 1, 2, 1, 2, 1},
                    {1, 2, 1, 1, 2, 1, 1, 1, 2, 1, 1, 2, 1, 2, 1, 1, 2, 2, 5, 1},
                    {1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1, 1, 1, 2, 1},
                    {1, 1, 1, 2, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 2, 1, 1, 1, 2, 1},
                    {1, 1, 2, 2, 2, 1, 1, 1, 2, 1, 1, 1, 1, 1, 2, 1, 1, 1, 2, 1},
                    {1, 2, 2, 1, 2, 2, 1, 1, 2, 1, 1, 1, 1, 1, 2, 2, 1, 1, 2, 1},
                    {1, 2, 1, 1, 1, 2, 2, 1, 2, 1, 1, 1, 1, 1, 1, 2, 1, 1, 2, 1},
                    {1, 2, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1},
                    {1, 6, 2, 2, 2, 2, 2, 2, 1, 1, 1, 1, 1, 2, 2, 1, 2, 2, 1, 1},
                    {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}
            };
            case 25 -> board = new int[][]{
                    {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                    {1, 4, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1},
                    {1, 1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 1, 2, 1, 2, 2, 1, 1},
                    {1, 1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 2, 2, 1, 2, 2, 1, 1},
                    {1, 1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1, 1, 2, 2, 2, 2, 1, 1, 2, 2, 1, 1},
                    {1, 1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1, 1, 2, 1, 1, 2, 2, 2, 2, 2, 1, 1},
                    {1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1, 2, 2, 1, 1, 2, 1, 1, 2, 2, 1, 1},
                    {1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 1, 2, 1, 2, 1, 1, 1, 2, 1, 1, 2, 2, 2, 1},
                    {1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1},
                    {1, 2, 1, 1, 1, 1, 1, 1, 2, 2, 2, 1, 2, 2, 2, 2, 1, 1, 1, 1, 1, 2, 2, 2, 1},
                    {1, 2, 2, 1, 1, 1, 1, 1, 2, 0, 2, 1, 2, 1, 1, 2, 1, 1, 1, 1, 1, 2, 1, 2, 1},
                    {1, 2, 2, 1, 1, 2, 2, 2, 2, 1, 2, 1, 2, 1, 1, 2, 2, 2, 2, 1, 1, 2, 1, 2, 1},
                    {1, 2, 2, 2, 2, 2, 2, 1, 2, 1, 2, 1, 2, 1, 1, 2, 1, 1, 2, 2, 2, 2, 1, 2, 1},
                    {1, 1, 2, 1, 1, 1, 1, 1, 2, 1, 2, 1, 2, 1, 1, 2, 1, 1, 1, 1, 1, 2, 1, 2, 1},
                    {1, 1, 2, 1, 1, 1, 1, 1, 2, 1, 2, 1, 2, 1, 1, 2, 1, 1, 1, 1, 1, 2, 1, 2, 1},
                    {1, 1, 2, 1, 1, 1, 1, 1, 2, 1, 2, 1, 2, 1, 1, 2, 1, 1, 1, 1, 1, 2, 1, 2, 1},
                    {1, 1, 2, 1, 1, 1, 2, 2, 2, 1, 2, 1, 2, 1, 1, 2, 1, 1, 1, 2, 2, 2, 1, 5, 1},
                    {1, 1, 2, 1, 1, 2, 2, 1, 2, 1, 2, 1, 2, 1, 1, 2, 1, 1, 1, 2, 1, 2, 1, 2, 1},
                    {1, 1, 2, 1, 2, 2, 1, 1, 2, 1, 2, 1, 2, 1, 1, 2, 2, 2, 2, 2, 1, 2, 1, 2, 1},
                    {1, 1, 2, 1, 2, 2, 1, 1, 2, 1, 2, 2, 2, 1, 1, 2, 1, 1, 1, 1, 1, 2, 1, 2, 1},
                    {1, 1, 2, 2, 2, 2, 2, 2, 2, 1, 2, 1, 2, 1, 1, 2, 1, 1, 1, 1, 1, 2, 1, 2, 1},
                    {1, 2, 2, 1, 1, 1, 1, 1, 2, 1, 2, 1, 2, 1, 1, 2, 1, 1, 1, 1, 1, 2, 2, 2, 1},
                    {1, 2, 1, 1, 1, 1, 2, 2, 2, 1, 2, 2, 2, 1, 1, 2, 1, 1, 1, 1, 1, 2, 2, 2, 1},
                    {1, 6, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1},
                    {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}
            };
            case 30 -> board = new int[][]{
                    {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                    {1, 4, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1},
                    {1, 2, 1, 1, 2, 2, 1, 1, 2, 2, 2, 1, 1, 2, 2, 1, 1, 1, 2, 1, 1, 1, 2, 1, 1, 1, 2, 1, 2, 1},
                    {1, 2, 1, 1, 2, 1, 1, 1, 1, 2, 1, 1, 1, 1, 2, 1, 1, 1, 2, 1, 1, 2, 2, 2, 1, 1, 2, 1, 2, 1},
                    {1, 2, 1, 1, 2, 2, 1, 1, 2, 2, 2, 1, 1, 2, 2, 1, 1, 1, 2, 1, 1, 2, 2, 2, 1, 1, 2, 1, 2, 1},
                    {1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1},
                    {1, 2, 1, 1, 2, 1, 1, 1, 1, 2, 1, 1, 1, 1, 2, 1, 1, 1, 2, 1, 1, 1, 2, 1, 1, 1, 2, 1, 2, 1},
                    {1, 2, 1, 1, 2, 1, 1, 1, 1, 2, 1, 1, 1, 1, 2, 1, 1, 1, 2, 1, 1, 2, 2, 2, 1, 1, 2, 1, 2, 1},
                    {1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1},
                    {1, 2, 1, 1, 2, 1, 1, 1, 1, 2, 2, 1, 1, 2, 2, 1, 1, 1, 2, 1, 1, 2, 2, 2, 1, 1, 2, 1, 2, 1},
                    {1, 2, 1, 1, 2, 1, 1, 1, 1, 2, 1, 1, 1, 1, 2, 1, 1, 1, 2, 1, 1, 1, 1, 1, 1, 1, 2, 1, 2, 1},
                    {1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1, 2, 2, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1},
                    {1, 1, 1, 2, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 1, 1, 1, 2, 1, 1, 1, 2, 1, 1, 1, 2, 1, 2, 1},
                    {1, 2, 2, 2, 1, 1, 1, 1, 1, 2, 1, 2, 2, 1, 2, 1, 1, 1, 2, 1, 1, 1, 2, 1, 1, 1, 2, 1, 2, 1},
                    {1, 2, 1, 1, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 0, 1, 1, 1, 2, 1, 1, 2, 2, 2, 1, 1, 2, 1, 2, 1},
                    {1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 5, 2, 2, 1},
                    {1, 2, 1, 1, 1, 1, 1, 1, 1, 2, 2, 1, 1, 2, 2, 1, 1, 1, 2, 1, 1, 2, 2, 2, 1, 1, 2, 1, 2, 1},
                    {1, 2, 1, 1, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 2, 1, 1, 1, 2, 1, 1, 1, 1, 1, 1, 1, 2, 1, 2, 1},
                    {1, 2, 1, 1, 1, 1, 1, 1, 1, 2, 2, 1, 1, 2, 2, 1, 1, 1, 2, 1, 1, 1, 1, 1, 1, 1, 2, 1, 2, 1},
                    {1, 2, 1, 1, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 2, 1, 1, 1, 2, 1, 1, 1, 2, 1, 1, 1, 2, 1, 2, 1},
                    {1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1},
                    {1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 2, 1, 1, 1, 2, 1, 1, 1, 2, 1, 1, 1, 2, 1, 2, 1},
                    {1, 1, 1, 2, 2, 2, 2, 2, 1, 2, 1, 1, 1, 1, 2, 1, 1, 1, 2, 1, 1, 1, 2, 2, 2, 1, 2, 1, 2, 1},
                    {1, 1, 1, 2, 1, 1, 1, 2, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1, 2, 2, 2, 1, 2, 1, 2, 2, 2, 1},
                    {1, 2, 2, 2, 1, 1, 1, 2, 2, 2, 1, 1, 1, 1, 2, 1, 1, 1, 2, 1, 2, 1, 1, 1, 2, 2, 2, 1, 2, 1},
                    {1, 2, 1, 1, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 2, 1, 1, 1, 2, 1, 2, 1, 1, 1, 1, 1, 2, 1, 2, 1},
                    {1, 6, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1},
                    {1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 2, 1},
                    {1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1},
                    {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}
            };
        }
        tempRed = 2;
        tempYellow = 2;
        tempGreen = 2;
    }

    public void loadBoard(JPanel boardPanel) {
        int scale = PacmanGameFrame.getScreenSize() / boardSize;
        Image pacman = new ImageIcon("../Pacman/texturePac/pacmanclosed_NULL.png").getImage().getScaledInstance(scale,
                scale, Image.SCALE_SMOOTH);
        Image wall = new ImageIcon("../Pacman/texturePac/wall.png").getImage().getScaledInstance(scale,
                scale, Image.SCALE_SMOOTH);
        Image food = new ImageIcon("../Pacman/texturePac/food.png").getImage().getScaledInstance(scale,
                scale, Image.SCALE_SMOOTH);
        Image empty = new ImageIcon("../Pacman/texturePac/empty.png").getImage().getScaledInstance(scale,
                scale, Image.SCALE_SMOOTH);
        Image redEnemy = new ImageIcon("../Pacman/texturePac/redenemy.png").getImage().getScaledInstance(scale,
                scale, Image.SCALE_SMOOTH);
        Image yellowEnemy = new ImageIcon("../Pacman/texturePac/yellowenemy.png").getImage().getScaledInstance(scale,
                scale, Image.SCALE_SMOOTH);
        Image greenEnemy = new ImageIcon("../Pacman/texturePac/greenenemy.png").getImage().getScaledInstance(scale,
                scale, Image.SCALE_SMOOTH);
        for (int y = 0; y < boardSize; y++) {
            for (int x = 0; x < boardSize; x++) {
                switch (board[y][x]) {
                    case 1 -> {
                        labels[y][x] = new JLabel(new ImageIcon(wall));
                        boardPanel.add(labels[y][x]);
                    }
                    case 2 -> {
                        labels[y][x] = new JLabel(new ImageIcon(food));
                        boardPanel.add(labels[y][x]);
                    }
                    case 3 -> {
                        labels[y][x] = new JLabel(new ImageIcon(empty));
                        boardPanel.add(labels[y][x]);
                    }
                    case 0 -> {
                        labels[y][x] = new JLabel(new ImageIcon(pacman));
                        boardPanel.add(labels[y][x]);
                    }
                    case 4 -> {
                        labels[y][x] = new JLabel(new ImageIcon(redEnemy));
                        boardPanel.add(labels[y][x]);
                    }
                    case 5 -> {
                        labels[y][x] = new JLabel(new ImageIcon(yellowEnemy));
                        boardPanel.add(labels[y][x]);
                    }
                    default -> {
                        labels[y][x] = new JLabel(new ImageIcon(greenEnemy));
                        boardPanel.add(labels[y][x]);
                    }
                }
            }
        }
    }

    public void rescaleImages() {
        int scale = PacmanGameFrame.getScreenSize() / boardSize;
        for (int y = 0; y < boardSize; y++) {
            for (int x = 0; x < boardSize; x++) {
                switch (board[y][x]) {
                    case 1 -> {
                        ImageIcon scaledIcon = new ImageIcon(new ImageIcon("../Pacman/texturePac/wall.png").getImage().getScaledInstance(scale, scale, Image.SCALE_SMOOTH));
                        labels[y][x].setIcon(scaledIcon);
                    }
                    case 2 -> {
                        ImageIcon scaledIcon = new ImageIcon(new ImageIcon("../Pacman/texturePac/food.png").getImage().getScaledInstance(scale, scale, Image.SCALE_SMOOTH));
                        labels[y][x].setIcon(scaledIcon);
                    }
                    case 3 -> {
                        ImageIcon scaledIcon = new ImageIcon(new ImageIcon("../Pacman/texturePac/empty.png").getImage().getScaledInstance(scale, scale, Image.SCALE_SMOOTH));
                        labels[y][x].setIcon(scaledIcon);
                    }
                    case 0 -> {
                        ImageIcon scaledIcon = new ImageIcon(new ImageIcon("../Pacman/texturePac/pacmanclosed_NULL.png").getImage().getScaledInstance(scale, scale, Image.SCALE_SMOOTH));
                        labels[y][x].setIcon(scaledIcon);
                    }
                    case 4 -> {
                        ImageIcon scaledIcon = new ImageIcon(new ImageIcon("../Pacman/texturePac/redenemy.png").getImage().getScaledInstance(scale, scale, Image.SCALE_SMOOTH));
                        labels[y][x].setIcon(scaledIcon);
                    }
                    case 5 -> {
                        ImageIcon scaledIcon = new ImageIcon(new ImageIcon("../Pacman/texturePac/yellowenemy.png").getImage().getScaledInstance(scale, scale, Image.SCALE_SMOOTH));
                        labels[y][x].setIcon(scaledIcon);
                    }
                    case 6 -> {
                        ImageIcon scaledIcon = new ImageIcon(new ImageIcon("../Pacman/texturePac/greenenemy.png").getImage().getScaledInstance(scale, scale, Image.SCALE_SMOOTH));
                        labels[y][x].setIcon(scaledIcon);
                    }
                    case 7 -> {
                        ImageIcon scaledIcon = new ImageIcon(new ImageIcon("../Pacman/texturePac/freeze.png").getImage().getScaledInstance(scale, scale, Image.SCALE_SMOOTH));
                        labels[y][x].setIcon(scaledIcon);
                    }
                    case 8 -> {
                        ImageIcon scaledIcon = new ImageIcon(new ImageIcon("../Pacman/texturePac/slow.png").getImage().getScaledInstance(scale, scale, Image.SCALE_SMOOTH));
                        labels[y][x].setIcon(scaledIcon);
                    }
                    case 9 -> {
                        ImageIcon scaledIcon = new ImageIcon(new ImageIcon("../Pacman/texturePac/hundred.png").getImage().getScaledInstance(scale, scale, Image.SCALE_SMOOTH));
                        labels[y][x].setIcon(scaledIcon);
                    }
                    case 10 -> {
                        ImageIcon scaledIcon = new ImageIcon(new ImageIcon("../Pacman/texturePac/star.png").getImage().getScaledInstance(scale, scale, Image.SCALE_SMOOTH));
                        labels[y][x].setIcon(scaledIcon);
                    }
                    case 11 -> {
                        ImageIcon scaledIcon = new ImageIcon(new ImageIcon("../Pacman/texturePac/lightning.png").getImage().getScaledInstance(scale, scale, Image.SCALE_SMOOTH));
                        labels[y][x].setIcon(scaledIcon);
                    }
                }
            }
        }
    }


    public synchronized void moveCharacter(Direction direction, String character) {
        int scale = outerFrame.getHeight() / boardSize;
        ImageIcon scaledCharacter;
        if (character.equals("pacman"))
            scaledCharacter = new ImageIcon(new ImageIcon("../Pacman/texturePac/pacman_" + direction + ".png").getImage().getScaledInstance(scale, scale, Image.SCALE_SMOOTH));
        else
            scaledCharacter = new ImageIcon(new ImageIcon("../Pacman/texturePac/" + character + ".png").getImage().getScaledInstance(scale, scale, Image.SCALE_SMOOTH));
        ImageIcon scaledEmpty = new ImageIcon(new ImageIcon("../Pacman/texturePac/empty.png").getImage().getScaledInstance(scale, scale, Image.SCALE_SMOOTH));
        ImageIcon scaledFood = new ImageIcon(new ImageIcon("../Pacman/texturePac/food.png").getImage().getScaledInstance(scale, scale, Image.SCALE_SMOOTH));
        int number = getCharacterNumber(character);
        for (int y = 0; y < boardSize; y++) {
            for (int x = 0; x < boardSize; x++) {
                if (board[y][x] == number) {
                    switch (direction) {
                        case UP -> {
                            if (y != 1 && !isWall(x, y - 1)) {
                                changeNeededIcons(scaledEmpty, scaledCharacter, scaledFood, number, y, x, y - 1, x);
                                return;
                            }
                        }
                        case DOWN -> {
                            if (y != boardSize - 2 && !isWall(x, y + 1)) {
                                changeNeededIcons(scaledEmpty, scaledCharacter, scaledFood, number, y, x, y + 1, x);
                                return;
                            }
                        }
                        case LEFT -> {
                            if (x != 1 && !isWall(x - 1, y)) {
                                changeNeededIcons(scaledEmpty, scaledCharacter, scaledFood, number, y, x, y, x - 1);
                                return;
                            }
                        }
                        case RIGHT -> {
                            if (x != boardSize - 2 && !isWall(x + 1, y)) {
                                changeNeededIcons(scaledEmpty, scaledCharacter, scaledFood, number, y, x, y, x + 1);
                                return;
                            }
                        }
                    }
                }
            }
        }
    }

    private void changeNeededIcons(ImageIcon scaledEmpty, ImageIcon scaledCharacter, ImageIcon scaledFood, int number, int y, int x, int y2, int x2) {
        int scale = outerFrame.getHeight() / boardSize;
        ImageIcon freezePerk = new ImageIcon(new ImageIcon("../Pacman/texturePac/freeze.png").getImage().getScaledInstance(scale, scale, Image.SCALE_SMOOTH));
        ImageIcon slowPerk = new ImageIcon(new ImageIcon("../Pacman/texturePac/slow.png").getImage().getScaledInstance(scale, scale, Image.SCALE_SMOOTH));
        ImageIcon hundredPerk = new ImageIcon(new ImageIcon("../Pacman/texturePac/hundred.png").getImage().getScaledInstance(scale, scale, Image.SCALE_SMOOTH));
        ImageIcon starPerk = new ImageIcon(new ImageIcon("../Pacman/texturePac/star.png").getImage().getScaledInstance(scale, scale, Image.SCALE_SMOOTH));
        ImageIcon lightningPerk = new ImageIcon(new ImageIcon("../Pacman/texturePac/lightning.png").getImage().getScaledInstance(scale, scale, Image.SCALE_SMOOTH));
        if (number == 0 && isEnemyThere(x2, y2)) return;
        if (number != 0) {
            board[y][x] = getTempOf(number);
            switch (board[y][x]) {
                case 2 -> labels[y][x].setIcon(scaledFood);
                case 3 -> labels[y][x].setIcon(scaledEmpty);
                case 7 -> labels[y][x].setIcon(freezePerk);
                case 8 -> labels[y][x].setIcon(slowPerk);
                case 9 -> labels[y][x].setIcon(hundredPerk);
                case 10 -> labels[y][x].setIcon(starPerk);
                case 11 -> labels[y][x].setIcon(lightningPerk);
            }
        } else {
            switch (board[y2][x2]) {
                case 2 -> foodEaten++;
                case 7 -> setEnemyFreeze(true);
                case 8 -> setEnemySlow(true);
                case 9 -> addHundredEaten();
                case 10 -> multiplyScore();
                case 11 -> setPacmanSpedUp(true);
            }
            board[y][x] = 3;
            labels[y][x].setIcon(scaledEmpty);
        }
        if (number != 0) setTemp(board[y2][x2], number);
        board[y2][x2] = number;
        labels[y2][x2].setIcon(scaledCharacter);


        if (isPacmanEaten()) setPacmanEatenBool(true);

    }

    public synchronized void moveEnemy(Direction direction, int enemy) {
        Direction newDirection = direction;
        switch (newDirection) {
            case LEFT -> {
                if (fewOptionsToMove(getCharPosX(enemy), getCharPosY(enemy))) {
                    if (Math.random() < 0.4) newDirection = randomDirWithoutDim('x');
                } else if (isWall(getCharPosX(enemy) - 1, getCharPosY(enemy)))
                    newDirection = randomDirWithout(Direction.LEFT);

            }
            case RIGHT -> {
                if (fewOptionsToMove(getCharPosX(enemy), getCharPosY(enemy))) {
                    if (Math.random() < 0.4) newDirection = randomDirWithoutDim('x');
                } else if (isWall(getCharPosX(enemy) + 1, getCharPosY(enemy)))
                    newDirection = randomDirWithout(Direction.RIGHT);

            }
            case UP -> {
                if (fewOptionsToMove(getCharPosX(enemy), getCharPosY(enemy))) {
                    if (Math.random() < 0.4) newDirection = randomDirWithoutDim('y');
                } else if (isWall(getCharPosX(enemy), getCharPosY(enemy) - 1))
                    newDirection = randomDirWithout(Direction.UP);
            }
            case DOWN -> {
                if (fewOptionsToMove(getCharPosX(enemy), getCharPosY(enemy))) {
                    if (Math.random() < 0.4) newDirection = randomDirWithoutDim('y');
                } else if (isWall(getCharPosX(enemy), getCharPosY(enemy) + 1))
                    newDirection = randomDirWithout(Direction.DOWN);

            }
        }


        if (enemy == 4) {
            redDir = newDirection;
            moveCharacter(redDir, "redenemy");
        } else if (enemy == 5) {
            yellowDir = newDirection;
            moveCharacter(yellowDir, "yellowenemy");
        } else {
            greenDir = newDirection;
            moveCharacter(greenDir, "greenenemy");
        }
    }

    public synchronized void changePacmanImage(Direction direction) {
        int scale = PacmanGameFrame.getScreenSize() / boardSize;
        ImageIcon scaledPacman = new ImageIcon(new ImageIcon("../Pacman/texturePac/pacman_" + direction + ".png").getImage().getScaledInstance(scale, scale, Image.SCALE_SMOOTH));
        ImageIcon scaledPacmanClosed = new ImageIcon(new ImageIcon("../Pacman/texturePac/pacmanclosed_" + direction + ".png").getImage().getScaledInstance(scale, scale, Image.SCALE_SMOOTH));
        ImageIcon scaledHalfPacmanClosed = new ImageIcon(new ImageIcon("../Pacman/texturePac/pacmanhalfclosed_" + direction + ".png").getImage().getScaledInstance(scale, scale, Image.SCALE_SMOOTH));
        for (int y = 0; y < boardSize; y++) {
            for (int x = 0; x < boardSize; x++) {
                if (board[y][x] == 0) {
                    switch (pacmanAnim) {
                        case 0 -> {
                            labels[y][x].setIcon(scaledPacmanClosed);
                            pacmanAnim++;
                        }
                        case 1 -> {
                            labels[y][x].setIcon(scaledHalfPacmanClosed);
                            pacmanAnim++;
                        }
                        case 2 -> {
                            labels[y][x].setIcon(scaledPacman);
                            pacmanAnim++;
                        }
                        case 3 -> {
                            labels[y][x].setIcon(scaledHalfPacmanClosed);
                            pacmanAnim = 0;
                        }
                    }
                }
            }
        }

    }

    public void spawnRandomPerk(int enemy) {
        int random = (int) (Math.random() * 5) + 7;
        setTemp(random, enemy);
    }

    public int findFreeEnemy() {
        while (true) {
            int random = (int) (Math.random() * 3 + 4);
            if (getTempOf(random) != 4 && getTempOf(random) != 5 && getTempOf(random) != 6) return random;
        }
    }

    public int getCharacterNumber(String character) {
        return switch (character) {
            case "pacman" -> 0;
            case "redenemy" -> 4;
            case "yellowenemy" -> 5;
            case "greenenemy" -> 6;
            default -> -1;
        };

    }

    public void setTemp(int val, int number) {
        switch (number) {
            case 4 -> tempRed = val;
            case 5 -> tempYellow = val;
            case 6 -> tempGreen = val;
        }
    }

    public int getTempOf(int number) {
        int val = -1;
        switch (number) {
            case 4 -> val = tempRed;
            case 5 -> val = tempYellow;
            case 6 -> val = tempGreen;
        }
        return val;
    }

    public boolean isPacmanEaten() {
        return (tempRed == 0 || tempYellow == 0 || tempGreen == 0);
    }
    public void setPacmanEatenBool(boolean val){
        pacmanEaten = val;
    }
    public boolean getPacmanEatenBool(){
        return pacmanEaten;
    }

    public boolean isWall(int x, int y) {
        return board[y][x] == 1;
    }

    public boolean isEnemyThere(int x, int y) {
        return (board[y][x] == 4 || board[y][x] == 5 || board[y][x] == 6);
    }

    public boolean fewOptionsToMove(int x, int y) {
        int countOptions = 0;
        if (!isWall(x + 1, y)) countOptions++;
        if (!isWall(x - 1, y)) countOptions++;
        if (!isWall(x, y + 1)) countOptions++;
        if (!isWall(x, y - 1)) countOptions++;

        return countOptions > 2;
    }

    public boolean isAllFoodEaten() {
        return (int) Arrays.stream(board).flatMapToInt(x -> Arrays.stream(x)).filter(x -> x == 2).count() == 0;
    }

    public int getCharPosY(int character) {
        for (int y = 0; y < boardSize; y++) {
            for (int x = 0; x < boardSize; x++) {
                if (board[y][x] == character) {
                    return y;
                }
            }
        }
        if (tempRed == character) return getCharPosY(4);
        if (tempYellow == character) return getCharPosY(5);
        if (tempGreen == character) return getCharPosY(6);
        return -2;
    }

    public int getCharPosX(int character) {
        for (int y = 0; y < boardSize; y++) {
            for (int x = 0; x < boardSize; x++) {
                if (board[y][x] == character) {
                    return x;
                }
            }
        }
        if (tempRed == character) return getCharPosX(4);
        if (tempYellow == character) return getCharPosX(5);
        if (tempGreen == character) return getCharPosX(6);
        return -2;
    }

    public Direction randomDirWithout(Direction direction) {
        switch (direction) {
            case RIGHT -> {
                if (Math.random() < 0.5) direction = Direction.LEFT;
                else if (Math.random() < 0.5) direction = Direction.UP;
                else direction = Direction.DOWN;
            }
            case LEFT -> {
                if (Math.random() < 0.5) direction = Direction.RIGHT;
                else if (Math.random() < 0.5) direction = Direction.UP;
                else direction = Direction.DOWN;
            }
            case DOWN -> {
                if (Math.random() < 0.5) direction = Direction.LEFT;
                else if (Math.random() < 0.5) direction = Direction.UP;
                else direction = Direction.RIGHT;
            }
            case UP -> {
                if (Math.random() < 0.5) direction = Direction.LEFT;
                else if (Math.random() < 0.5) direction = Direction.RIGHT;
                else direction = Direction.DOWN;
            }
        }
        return direction;
    }

    public Direction randomDirWithoutDim(char dimension) {
        Direction direction = Direction.NULL;
        switch (dimension) {
            case 'x' -> {
                if (Math.random() < 0.5) direction = Direction.UP;
                else direction = Direction.DOWN;
            }
            case 'y' -> {
                if (Math.random() < 0.5) direction = Direction.LEFT;
                else direction = Direction.RIGHT;
            }
        }
        return direction;
    }

    public int getFoodEaten() {
        return foodEaten;
    }

    public Direction getRedDir() {
        return redDir;
    }

    public Direction getYellowDir() {
        return yellowDir;
    }

    public Direction getGreenDir() {
        return greenDir;
    }

    public void addHundredEaten() {
        foodEaten += 10;
    }

    public void multiplyScore() {
        foodEaten *= 2;
    }

    public boolean isPacmanSpedUp() {
        return pacmanSpedUp;
    }

    public void setPacmanSpedUp(boolean spedUp) {
        pacmanSpedUp = spedUp;
    }

    public boolean isEnemyFreeze() {
        return enemyFreeze;
    }

    public boolean isEnemySlow() {
        return enemySlow;
    }

    public void setEnemyFreeze(boolean freezed) {
        enemyFreeze = freezed;
    }

    public void setEnemySlow(boolean slowed) {
        enemySlow = slowed;
    }
}
