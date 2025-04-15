import java.awt.*;

public class Ball {
    private String type;
    private int xLoc, yLoc;
    private Image ballImage;
    private boolean hasScored;
    private int xSpeed, ySpeed;
    private boolean isStriped;

    public Ball(String type, int xLoc, int yLoc, Image ballImage, boolean isStriped) {
        this.type = type;
        this.xLoc = xLoc;
        this.yLoc = yLoc;
        this.ballImage = ballImage;
        hasScored = false;
        xSpeed = 0;
        ySpeed = 0;
        this.isStriped = isStriped;
    }
}
