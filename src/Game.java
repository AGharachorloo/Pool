import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Game {
    public static final int STATE_INTRO = 0;
    public static final int STATE_PLAYING = 1;
    public static final int STATE_GAME_OVER = 2;
    public final static int WINDOW_HEIGHT = 796;
    public final static int WINDOW_WIDTH = 468;
    private boolean isGameOver;
    private GameView front;
    private final ArrayList<Ball> balls = new ArrayList<>();
    private final Point[] pockets = {
            new Point(68, 68), new Point(WINDOW_WIDTH - 68, 68),
            new Point(68, WINDOW_HEIGHT/2), new Point(WINDOW_WIDTH - 68, WINDOW_HEIGHT/2),
            new Point(68, WINDOW_HEIGHT - 68), new Point(WINDOW_WIDTH - 68, WINDOW_HEIGHT - 52)
    };
    private Timer timer;
    private Point cueStart, cueEnd;
    private boolean isAiming;
    private int mouseX, mouseY;
    private int score;
    private boolean playerTurn;
    private boolean cueBallHit;
    private boolean scored;
    private int gameState;

    // Initialize the game, set up UI & start timer
    public Game() {
        isGameOver = false;
//        Image nineStripe = new ImageIcon("Resources/9Stripe.png").getImage();
//        ball = new Ball(9,200, 300, nineStripe, true);
        initializeBalls();
        front = new GameView(this);
        timer = new Timer(16, e -> updateGame());
        timer.start();
        playerTurn = true;
        cueBallHit = false;
        scored = false;
    }

    // Update the ball positions and game
    private void updateGame() {
        if (gameState != STATE_PLAYING) {
            return;
        }

        for (Ball b : balls) {
            b.update();
        }
        handleCollisions();
        checkPockets();

        if (front != null) {
            front.repaint();
        }
    }

    // Check if any collisions occur, if they do, resolve them
    private void handleCollisions() {
        for (int i = 0; i < balls.size(); i++) {
            for (int j = i + 1; j < balls.size(); j++) {
                Ball a = balls.get(i);
                Ball b = balls.get(j);
                if (a.collidesWith(b)) {
                    a.resolveCollision(b);
                    cueBallHit = true;
                }
            }
        }
    }

    // Check if any ball has entered a pocket & if it has update game state
    private void checkPockets() {
        boolean cuePocketed = false;
        boolean blackPocketed = false;

        for (int i = balls.size() - 1; i >= 0; i--) {
            Ball b = balls.get(i);
            for (Point p : pockets) {
                if (p.distance(b.getX(), b.getY()) < 20) {
                    if (b.getColor().equals(Color.WHITE)) {
                        cuePocketed = true;
                    } else if (b.getColor().equals(Color.BLACK)) {
                        gameState = STATE_GAME_OVER;
                        blackPocketed = true;
                        break;
                    } else {
                        scored = true;
                        score++;
                    }
                    balls.remove(i);
                    break;
                }
            }
        }
        // Check if cue pocketed or if turn change

        if (blackPocketed) {
            restartGame();
            return;
        }

        if (cuePocketed) {
            resetCueBall();
            cueBallHit = false;
        } else if (allBallsStopped() && !isAiming) {
            if (cueBallHit) {
                if (!scored) {
                    playerTurn = !playerTurn;
                }
                cueBallHit = false;
            }
        }
    }

    // Add cue ball back on table & swap turn post-scratch
    private void resetCueBall() {
        balls.add(0, new Ball(180, 437, Color.WHITE));
        playerTurn = !playerTurn;
    }

    // Set up the balls in a triangle formation
    private void initializeBalls() {
        balls.clear();
        balls.add(new Ball(233, 568, Color.WHITE));

        int startX = 233;
        int startY = 200;
        double spacing = balls.get(0).getRadius() * 2 + 5;
        int colorIndex = 0;

        Color[] colors = {Color.RED, Color.BLUE, Color.YELLOW, Color.ORANGE,
                Color.CYAN, Color.MAGENTA, Color.GREEN, Color.PINK,
                Color.LIGHT_GRAY
        };

        // construct each ball & color;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j <= i; j++) {
                double x = startX + i * spacing/2 - j * spacing;
                double y = startY - i * spacing;

                Color color;
                if (i == 2 && j == 1) {
                    color = Color.BLACK;
                } else {
                    color = colors[colorIndex];
                    colorIndex++;
                }

                if (colorIndex <= colors.length) {
                    balls.add(new Ball(x, y, color));
                }
            }
        }
    }

    // Shoot the cue ball based on aiming direction & power
    public void shoot(Point releasePoint) {
        if (cueStart != null && !balls.isEmpty() && allBallsStopped()) {
            double dx = cueStart.x - releasePoint.x;
            double dy = cueStart.y - releasePoint.y;
            double dist = Math.min(200, Math.hypot(dx, dy));
            double power = dist * 0.08;
            double angle = Math.atan2(dy, dx);
            balls.get(0).setVelocity(Math.cos(angle) * power, Math.sin(angle) * power);
        }
        isAiming = false;
    }

    // calculate the points for the cueLine
    public Point[] getCueLine() {
        if (cueStart == null || balls.isEmpty() || !allBallsStopped()) {
            return null;
        }

        Ball cueBall = balls.get(0);
        Point center = new Point((int)cueBall.getX(), (int)cueBall.getY());
        double dx = cueStart.x - mouseX;
        double dy = cueStart.y - mouseY;
        // Invert cueLine so it points in ball movement direction
        dx *= -1;
        dy *= -1;

        double dist = Math.sqrt(dx*dx + dy*dy);
        double maxLength = 200;

        if (dist > maxLength) {
            double ratio = maxLength / dist;
            dx *= ratio;
            dy *= ratio;
        }
        Point end = new Point((int)(center.x - dx), (int)(center.y - dy));
        return new Point[]{center, end};
    }

    public void restartGame() {
        score = 0;
        playerTurn = true;
        cueBallHit = false;
        isAiming = false;
        initializeBalls();
    }

    public boolean isGameOver() {
        return isGameOver;
    }

    public void setGameOver(boolean gameOver) {
        isGameOver = gameOver;
    }

    public GameView getFront() {
        return front;
    }

    public void setFront(GameView front) {
        this.front = front;
    }

    public ArrayList<Ball> getBalls() {
        return balls;
    }

    public Point[] getPockets() {
        return pockets;
    }

    public boolean isAiming() {
        if (isAiming) {
            scored = false;
        }
        return isAiming;
    }

    public void setAiming(boolean aiming) {
        this.isAiming = aiming;
    }

    public Point getCueStart() {
        return cueStart;
    }

    public void setCueStart(Point cueStart) {
        if (allBallsStopped()) {
            this.cueStart = cueStart;
            isAiming = true;
        }
    }

    public void setMousePosition(int x, int y) {
        this.mouseX = x;
        this.mouseY = y;
    }

    public Point getMousePosition() {
        return new Point(mouseX, mouseY);
    }

    public int getScore() {
        return score;
    }

    public boolean isPlayerTurn() {
        return playerTurn;
    }

    public boolean allBallsStopped() {
        for (Ball b : balls) {
            if (b.getVx() != 0.0 || b.getVy() != 0.0) {
                return false;
            }
        }
        return true;
    }

    public void playGame() {
        front.repaint();
    }

    public double getBallRadius() {
        return balls.get(0).getRadius();
    }


    public double getPocketRadius() {
        return getBallRadius() * 2;
    }

    public int getGameState() {
        return gameState;
    }

    public void setGameState(int gameState) {
        this.gameState = gameState;
    }

    public static void main(String[] args) {
        Game game = new Game();
        GameView front = new GameView(game);
        game.setFront(front);
        JFrame frame = new JFrame("Pool Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(front);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}