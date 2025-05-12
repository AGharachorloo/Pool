import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GameView extends JPanel implements MouseListener, MouseMotionListener, KeyListener {
    public final static int WINDOW_HEIGHT = 796;
    public final static int WINDOW_WIDTH = 468;
    public final static int BALL_DIAMETER = 15;
    private Image table;
    private Game back;
    private Font titleFont, instructionFont;

    // Initialize the window & set up mouseListeners
    public GameView(Game back) {
        this.back = back;
        titleFont = new Font("SansSerif", Font.BOLD, 36);
        instructionFont = new Font("SanSerif", Font.PLAIN, 18);
        table = new ImageIcon("Resources/poolTable.png").getImage();
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
    // Draws table, balls, cueline, score, & playerTurn as well as intro & end screen depending on game state
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (back.getGameState() == Game.STATE_INTRO) {
            g.setColor(Color.BLACK);
            g.setFont(titleFont);
            g.drawString("POOL GAME", 130, 225);
            g.setFont(instructionFont);
            g.drawString("Press SPACE to Start", 155, 300);
        } else if (back.getGameState() == Game.STATE_PLAYING){
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
        } else if (back.getGameState() == Game.STATE_GAME_OVER) {
            g.setColor(Color.BLACK);
            g.setFont(titleFont);
            g.drawString("GAME OVER", 130, 225);
            g.setFont(instructionFont);
            g.drawString("Press SPACE to Restart", 155, 300);
        }
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
    // If a key is pressed check if it is R or the space bar & either reset game or start game
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_R) {
            back.restartGame();
            back.setGameState(Game.STATE_INTRO);
            repaint();
        }

        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            if (back.getGameState() == Game.STATE_INTRO || back.getGameState() == Game.STATE_GAME_OVER) {
                back.setGameState(Game.STATE_PLAYING);
                back.restartGame();
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
