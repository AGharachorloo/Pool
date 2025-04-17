import javax.swing.*;
import java.awt.*;

public class GameView extends JFrame {
    private final static int WINDOW_HEIGHT = 796;
    private final static int WINDOW_WIDTH = 468;
    private final static int BALL_DIAMETER = 30;
    private final int INTRO_SCREEN = 0, GAME_SCREEN = 1, END_SCREEN = 2;
    private Image instructions, table, stick, meter, intro, results;
    private int[] winX, winY;
    private Game back;
    private int x, y;

    public GameView(Game back) {
        this.back = back;
        this.setTitle("Pool!");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        this.setVisible(true);
//        instructions = new ImageIcon();
        table = new ImageIcon("Resources/poolTable.png").getImage();
//        stick = new ImageIcon();
//        meter = new ImageIcon();
//        intro = new ImageIcon();
//        results = new ImageIcon();
        winX = new int[6];
        winY = new int[6];

        for (int i = 0; i < 6; i++) {
            winX[i] = (i%2)*300 + 100;
            winY[i] = (i/2)*300 + 50;
        }
        x = 200;
        y = 300;
    }

    @Override
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    @Override
    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWindowHeight() {
        return WINDOW_HEIGHT;
    }

    public int getWindowWidth() {
        return WINDOW_WIDTH;
    }

    public int getBallDiameter() {
        return BALL_DIAMETER;
    }

    public void paint(Graphics g) {
        g.drawImage(table, 0, 0, WINDOW_WIDTH, WINDOW_HEIGHT, this);
//        g.drawImage(back.getBall().getBallImage(), back.getBall().getxLoc(), back.getBall().getyLoc(), BALL_DIAMETER, BALL_DIAMETER, this);
        g.setColor(Color.RED);
        g.fillOval(x, y, BALL_DIAMETER, BALL_DIAMETER);
    }
}
