import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Game {
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

    public Game() {
        isGameOver = false;
//        Image nineStripe = new ImageIcon("Resources/9Stripe.png").getImage();
//        ball = new Ball(9,200, 300, nineStripe, true);
        initializeBalls();
        front = new GameView(this);
        timer = new Timer(16, e -> updateGame());
        timer.start();
        playerTurn = true;
    }

    private void updateGame() {
        for (Ball b : balls) {
            b.update();
        }
        handleCollisions();
        checkPockets();

        if (front != null) {
            front.repaint();
        }
    }

    private void handleCollisions() {
        for (int i = 0; i < balls.size(); i++) {
            for (int j = i + 1; j < balls.size(); j++) {
                Ball a = balls.get(i);
                Ball b = balls.get(j);
                if (a.collidesWith(b)) {
                    a.resolveCollision(b);
                }
            }
        }
    }

    private void checkPockets() {
        boolean cuePocketed = false;

        for (int i = balls.size() - 1; i >= 0; i--) {
            Ball b = balls.get(i);
            for (Point p : pockets) {
                if (p.distance(b.getX(), b.getY()) < 15) {
                    if (b.getColor().equals(Color.WHITE)) {
                        cuePocketed = true;
                    } else {
                        score++;
                    }
                    balls.remove(i);
                    break;
                }
            }
        }
        if (cuePocketed) {
            resetCueBall();
        }
    }

    private void resetCueBall() {
        balls.add(0, new Ball(180, 437, Color.WHITE));
        playerTurn = !playerTurn;
    }

    private void initializeBalls() {
        balls.clear();
        balls.add(new Ball(180, 437, Color.WHITE));
        Color[] colors = {Color.RED, Color.BLUE, Color.YELLOW, Color.ORANGE, Color.MAGENTA, Color.CYAN};
        for (int i = 0; i < colors.length; i++) {
            balls.add(new Ball(180 + i * 20, 200 + (i % 2) * 20, colors[i]));
        }

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

    public void shoot(Point releasePoint) {
        if (cueStart != null && !balls.isEmpty() && allBallsStopped()) {
            double dx = cueStart.x - releasePoint.x;
            double dy = cueStart.y - releasePoint.y;
            double dist = Math.min(350, Math.hypot(dx, dy));
            double power = dist * 0.08;
            double angle = Math.atan2(dy, dx);
            balls.get(0).setVelocity(Math.cos(angle) * power, Math.sin(angle) * power);
        }
        isAiming = false;
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

    public Point[] getCueLine() {
        if (cueStart == null || balls.isEmpty() || !allBallsStopped()) {
            return null;
        }

        Ball cueBall = balls.get(0);
        Point center = new Point((int)cueBall.getX(), (int)cueBall.getY());
        double dx = cueStart.x - mouseX;
        double dy = cueStart.y - mouseY;
//        dx *= -1;
//        dy *= -1;

        double dist = Math.sqrt(dx*dx + dy*dy);
            double maxLength = 400;

        if (dist > maxLength) {
            double ratio = maxLength / dist;
            dx *= ratio;
            dy *= ratio;
        }
        Point end = new Point((int)(center.x - dx), (int)(center.y - dy));
        return new Point[]{center, end};
    }

    public void playGame() {
        front.repaint();
    }

    public double getBallRadius() {
        return balls.isEmpty() ? 0 : balls.get(0).getRadius();
    }


    public double getPocketRadius() {
        return getBallRadius() * 2;
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