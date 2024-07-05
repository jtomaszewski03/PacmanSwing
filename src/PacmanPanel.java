import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.*;

public class PacmanPanel extends JPanel {
    private JFrame outerFrame;
    private int score = 0;
    private boolean gameOn;
    private int amountOfLives, enemySleep, pacmanSpeed;
    private ImageIcon threeLivesIcon, twoLivesIcon, oneLifeIcon;
    private double playingTime = 0.0;


    public PacmanPanel(JFrame outerFrame, int size) {
        this.outerFrame = outerFrame;
        gameOn = true;
        amountOfLives = 3;
        enemySleep = 200;
        pacmanSpeed = 150;
        int scale = PacmanGameFrame.getScreenSize() / size * 2;

        JPanel boardPanel = new JPanel(new GridLayout(size, size));
        JLabel[][] labels = new JLabel[size][size];
        Board board = new Board(labels, size, outerFrame);
        setLayout(new BorderLayout());
        board.loadBoard(boardPanel);

        JPanel infoPanel = new JPanel();
        JLabel scoreLabel = new JLabel("Score " + score);

        threeLivesIcon = new ImageIcon(new ImageIcon("../Pacman/texturePac/3lives.png").getImage().getScaledInstance(scale, scale / 3, Image.SCALE_SMOOTH));
        twoLivesIcon = new ImageIcon(new ImageIcon("../Pacman/texturePac/2lives.png").getImage().getScaledInstance(scale, scale / 3, Image.SCALE_SMOOTH));
        oneLifeIcon = new ImageIcon(new ImageIcon("../Pacman/texturePac/lastlife.png").getImage().getScaledInstance(scale, scale / 3, Image.SCALE_SMOOTH));
        JLabel livesLabel = new JLabel(threeLivesIcon);
        JLabel timeLabel = new JLabel("" + playingTime);
        if (size == 10)
            scoreLabel.setPreferredSize(new Dimension(scale, scale / 3));
        else scoreLabel.setPreferredSize(new Dimension((int) (scale * 1.5), scale / 3));
        livesLabel.setPreferredSize(new Dimension((int) (scale * 2.6), scale / 3));
        timeLabel.setPreferredSize(new Dimension(scale, scale / 3));
        infoPanel.add(scoreLabel);
        infoPanel.add(livesLabel);
        infoPanel.add(timeLabel);

        PacmanKeyAdapter pacmanKeyAdapter = new PacmanKeyAdapter(board);
        addKeyListener(pacmanKeyAdapter);
        setFocusable(true);


        Runnable pacmanMoving = () -> {
            while (gameOn) {
                board.moveCharacter(pacmanKeyAdapter.getDirection(), "pacman");
                if (board.isAllFoodEaten()) {
                    gameOn = false;
                    new FinishFrame(score * 10, playingTime);
                    outerFrame.dispose();
                }
                try {
                    Thread.sleep(pacmanSpeed);
                } catch (InterruptedException e) {
                    System.out.println("Pacman moving thread interrupted");
                }
            }
        };

        Runnable pacmanAnimation = () -> {
            while (gameOn) {
                if (pacmanKeyAdapter.getDirection() != Board.Direction.NULL)
                    board.changePacmanImage(pacmanKeyAdapter.getDirection());
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    System.out.println("Pacman animation thread interrupted");
                }
            }
        };

        Runnable redMoving = () -> {
            while (gameOn) {
                if (board.getTempOf(5) != 4 || board.getTempOf(6) != 4) {
                    try {
                        if (pacmanKeyAdapter.getDirection() != Board.Direction.NULL) {
                            if (board.isEnemyFreeze()) {
                                Thread.sleep(5000);
                                board.setEnemyFreeze(false);
                            }
                            board.moveEnemy(board.getRedDir(), 4);
                        }
                        Thread.sleep(enemySleep);
                    } catch (InterruptedException e) {
                        System.out.println("Red moving thread interrupted");
                    }
                }
            }
        };
        Runnable yellowMoving = () -> {
            while (gameOn) {
                if (board.getTempOf(4) != 5 || board.getTempOf(6) != 5) {
                    try {
                        if (pacmanKeyAdapter.getDirection() != Board.Direction.NULL) {
                            if (board.isEnemyFreeze())
                                Thread.sleep(5000);
                            board.moveEnemy(board.getYellowDir(), 5);
                        }
                        Thread.sleep(enemySleep);
                    } catch (InterruptedException e) {
                        System.out.println("Yellow moving thread interrupted");
                    }
                }
            }
        };
        Runnable greenMoving = () -> {
            while (gameOn) {
                if (board.getTempOf(4) != 6 || board.getTempOf(5) != 6) {
                    try {
                        if (pacmanKeyAdapter.getDirection() != Board.Direction.NULL) {
                            if (board.isEnemyFreeze())
                                Thread.sleep(5000);
                            board.moveEnemy(board.getGreenDir(), 6);
                        }
                        Thread.sleep(enemySleep);

                    } catch (InterruptedException e) {
                        System.out.println("Green moving thread interrupted");
                    }
                }
            }
        };
        Runnable perks = () -> {
            double perkTimer = 0;
            while (gameOn) {
                if (pacmanKeyAdapter.getDirection() != Board.Direction.NULL)
                    perkTimer += 0.2;
                if (perkTimer > 5) {
                    perkTimer = 0;
                    if (size > 10) {
                        if (Math.random() < 0.25)
                            board.spawnRandomPerk(board.findFreeEnemy());
                    } else if (Math.random() < 0.25) board.spawnRandomPerk(4);
                }
                try {
                    Thread.sleep(enemySleep);
                } catch (InterruptedException e) {
                    System.out.println("Perks thread interrupted");
                }
            }
        };
        Runnable speed = () -> {
            double slowTimer = 0;
            double fastTimer = 0;
            while (gameOn) {
                if (board.isEnemySlow()) {
                    enemySleep = 400;
                    if (slowTimer < 4)
                        slowTimer += 0.1;
                    else {
                        board.setEnemySlow(false);
                        slowTimer = 0;
                    }
                } else enemySleep = 200;

                if (board.isPacmanSpedUp()) {
                    pacmanSpeed = 100;
                    if (fastTimer < 3)
                        fastTimer += 0.1;
                    else {
                        board.setPacmanSpedUp(false);
                        fastTimer = 0;
                    }
                } else pacmanSpeed = 150;

                try {
                    Thread.sleep(100);

                } catch (InterruptedException e) {
                    System.out.println("Speed thread interrupted");
                }
            }
        };
        Runnable checkCollisionCount = () -> {
            try {

                while (gameOn) {
                    if (board.getPacmanEatenBool()) {
                        amountOfLives--;
                        board.setPacmanEatenBool(false);
                        Thread.sleep(500);
                    }
                    if (amountOfLives == 0) {
                        gameOn = false;
                        outerFrame.dispose();
                        new FinishFrame(score * 10, playingTime);
                    }
                    switch (amountOfLives) {
                        case 2 -> livesLabel.setIcon(twoLivesIcon);
                        case 1 -> livesLabel.setIcon(oneLifeIcon);
                    }
                    Thread.sleep(200);
                }
            } catch (InterruptedException e) {
                System.out.println("Collision thread interrupted");
            }
        };

        Runnable scoreReload = () -> {
            while (gameOn) {
                score = board.getFoodEaten();
                scoreLabel.setText("Score: " + score * 10);
                try {
                    Thread.sleep(200);

                } catch (InterruptedException e) {
                    System.out.println("Score reload thread interrupted");
                }
            }
        };
        Runnable time = () -> {
            while (gameOn) {
                try {
                    Thread.sleep(100);

                } catch (InterruptedException e) {
                    System.out.println("Time thread interrupted");
                }
                if (pacmanKeyAdapter.getDirection() != Board.Direction.NULL)
                    playingTime += 0.1;
                timeLabel.setText("Time: " + Math.round((playingTime * 10)) / 10.0);
            }
        };

        Thread pacMoveListenerThread = new Thread(pacmanMoving);
        pacMoveListenerThread.start();

        Thread pacmanAnimationThread = new Thread(pacmanAnimation);
        pacmanAnimationThread.start();

        Thread redMovingThread = new Thread(redMoving);
        Thread yellowMovingThread = new Thread(yellowMoving);
        Thread greenMovingThread = new Thread(greenMoving);
        redMovingThread.start();
        if (size > 10) {
            yellowMovingThread.start();
            greenMovingThread.start();
        }

        Thread perkThread = new Thread(perks);
        perkThread.start();

        Thread speedThread = new Thread(speed);
        speedThread.start();

        Thread collisionThread = new Thread(checkCollisionCount);
        collisionThread.start();

        Thread scoreThread = new Thread(scoreReload);
        scoreThread.start();

        Thread timeThread = new Thread(time);
        timeThread.start();

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    gameOn = false;
                    outerFrame.dispose();
                    new StartFrame();
                }
            }
        });

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                PacmanGameFrame.setScreenSize(outerFrame.getHeight());
                outerFrame.setSize(PacmanGameFrame.getScreenSize(), PacmanGameFrame.getScreenSize());
                board.rescaleImages();

                int scale = PacmanGameFrame.getScreenSize() / size * 2;
                if (size == 10)
                    scoreLabel.setPreferredSize(new Dimension(scale, scale / 3));
                else scoreLabel.setPreferredSize(new Dimension((int) (scale * 1.5), scale / 3));
                livesLabel.setPreferredSize(new Dimension((int) (scale * 2.6), scale / 3));
                timeLabel.setPreferredSize(new Dimension(scale, scale / 3));
                threeLivesIcon = new ImageIcon(new ImageIcon("../Pacman/texturePac/3lives.png").getImage().getScaledInstance(scale, scale / 3, Image.SCALE_SMOOTH));
                twoLivesIcon = new ImageIcon(new ImageIcon("../Pacman/texturePac/2lives.png").getImage().getScaledInstance(scale, scale / 3, Image.SCALE_SMOOTH));
                oneLifeIcon = new ImageIcon(new ImageIcon("../Pacman/texturePac/lastlife.png").getImage().getScaledInstance(scale, scale / 3, Image.SCALE_SMOOTH));
                switch (amountOfLives) {
                    case 3 -> livesLabel.setIcon(threeLivesIcon);
                    case 2 -> livesLabel.setIcon(twoLivesIcon);
                    case 1 -> livesLabel.setIcon(oneLifeIcon);
                }
                new ImageIcon(new ImageIcon("../Pacman/texturePac/3lives.png").getImage().getScaledInstance(scale, scale / 3, Image.SCALE_SMOOTH));
            }
        });

        add(boardPanel, BorderLayout.CENTER);
        add(infoPanel, BorderLayout.SOUTH);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }

}
