import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GameView extends JPanel implements MouseListener, MouseMotionListener, KeyListener {
    public final static int WINDOW_HEIGHT = 796;
    public final static int WINDOW_WIDTH = 468;
    public final static int BALL_DIAMETER = 15;
    private final int INTRO_SCREEN = 0, GAME_SCREEN = 1, END_SCREEN = 2;
    private Image instructions, table, intro, results;
    private Game back;

    // Initialize the window & set up mouseListeners
    public GameView(Game back) {
        this.back = back;
//        instructions = new ImageIcon();
        table = new ImageIcon("Resources/poolTable.png").getImage();
//        intro = new ImageIcon();
//        results = new ImageIcon();
        this.setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
        this.setBackground(Color.WHITE);
        addMouseListener(this);
        addMouseMotionListener(this);
        addKeyListener(this);
        this.setFocusable(true);
        this.setVisible(true);
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

    @Override
    // Draws table, balls, cueline, score, & playerTurn
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(table, 0, 0, WINDOW_WIDTH, WINDOW_HEIGHT, this);
        Point[] pockets = back.getPockets();

        for (Ball b : back.getBalls()) {
            b.draw(g);
        }

        Point[] cueLine = back.getCueLine();
        if (back.isAiming() && cueLine != null) {
            g.setColor(Color.WHITE);
            g.drawLine((int)cueLine[0].getX(), (int)cueLine[0].getY(), (int)cueLine[1].getX(), (int)cueLine[1].getY());
        }

        g.setColor(Color.BLACK);
        g.drawString("Score: " + back.getScore(), 10, 20);
        String player = "Player 2";
        if (back.isPlayerTurn()) {
            player = "Player 1";
        }
        g.drawString("Turn: " + player, 80, 20);

    }

    @Override
    // Start aiming when mouse pressed
    public void mousePressed(MouseEvent e) {
        back.setCueStart(e.getPoint());
        back.setAiming(true);
    }

    @Override
    // Shoot the cue ball when released
    public void mouseReleased(MouseEvent e) {
        back.shoot(e.getPoint());
    }

    @Override
    // Update mouse position while dragging
    public void mouseDragged(MouseEvent e) {
        back.setMousePosition(e.getX(), e.getY());
    }

    @Override
    // Update mouse position when mouse moved
    public void mouseMoved(MouseEvent e) {
        back.setMousePosition(e.getX(), e.getY());
    }

    @Override
    public void mouseClicked(MouseEvent e) {}
    @Override
    public void mouseEntered(MouseEvent e) {}
    @Override
    public void mouseExited(MouseEvent e) {}

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_R) {
            back.restartGame();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
