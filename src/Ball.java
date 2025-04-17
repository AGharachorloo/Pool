import java.awt.*;

public class Ball {
    private final int RADIUS = 30;
    private int num;
    private int xLoc, yLoc;
    private Image ballImage;
    private boolean hasScored;
    private int xSpeed, ySpeed;
    private boolean isStriped;

    public Ball(int num, int xLoc, int yLoc, Image ballImage, boolean isStriped) {
        this.num = num;
        this.xLoc = xLoc;
        this.yLoc = yLoc;
        this.ballImage = ballImage;
        hasScored = false;
        xSpeed = 0;
        ySpeed = 0;
        this.isStriped = isStriped;
    }
    public boolean contains(int checkX, int checkY) {
        double dist = Math.sqrt((checkX - xLoc) * (checkX - xLoc) + (checkY - yLoc) * (checkY - yLoc));
        return dist < 30;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getxLoc() {
        return xLoc;
    }

    public void setxLoc(int xLoc) {
        this.xLoc = xLoc;
    }

    public int getyLoc() {
        return yLoc;
    }

    public void setyLoc(int yLoc) {
        this.yLoc = yLoc;
    }

    public Image getBallImage() {
        return ballImage;
    }

    public void setBallImage(Image ballImage) {
        this.ballImage = ballImage;
    }

    public boolean isHasScored() {
        return hasScored;
    }

    public void setHasScored(boolean hasScored) {
        this.hasScored = hasScored;
    }

    public int getxSpeed() {
        return xSpeed;
    }

    public void setxSpeed(int xSpeed) {
        this.xSpeed = xSpeed;
    }

    public int getySpeed() {
        return ySpeed;
    }

    public void setySpeed(int ySpeed) {
        this.ySpeed = ySpeed;
    }

    public boolean isStriped() {
        return isStriped;
    }

    public void setStriped(boolean striped) {
        isStriped = striped;
    }
}
