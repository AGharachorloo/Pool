import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Game implements MouseListener, MouseMotionListener {
    private boolean isGameOver;
    private int power;
    private int hitDegree;
    private GameView front;
//    private Ball[][] balls;
    private Ball ball;

    public Game() {
        isGameOver = false;
        power = 0;
        hitDegree = 0;
//        balls = new Ball[2][7];
//        Image nineStripe = new ImageIcon("Resources/9Stripe.png").getImage();
//        ball = new Ball(9,200, 300, nineStripe, true);
        front = new GameView(this);
        this.front.addMouseListener(this);
        this.front.addMouseMotionListener(this);
    }

//    public Ball getBall() {
//        return ball;
//    }
//
//    public void setBall(Ball ball) {
//        this.ball = ball;
//    }

    public boolean isGameOver() {
        return isGameOver;
    }

    public void setGameOver(boolean gameOver) {
        isGameOver = gameOver;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public int getHitDegree() {
        return hitDegree;
    }

    public void setHitDegree(int hitDegree) {
        this.hitDegree = hitDegree;
    }

    public GameView getFront() {
        return front;
    }

    public void setFront(GameView front) {
        this.front = front;
    }

//    public Ball[][] getBalls() {
//        return balls;
//    }
//
//    public void setBalls(Ball[][] balls) {
//        this.balls = balls;
//    }

    public void playGame() {
        front.repaint();
    }

    public static void main(String[] args) {
        Game game = new Game();
        game.playGame();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getX() < front.getWindowWidth() - front.getBallDiameter() - 65 && e.getX() > 65) {
            front.setX(e.getX());
        }
        if (e.getY() < front.getWindowHeight() - front.getBallDiameter() - 65 && e.getY() > 65) {
            front.setY(e.getY());
        }
        front.repaint();
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
}