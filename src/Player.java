public class Player {
    private String name;
    private boolean isStripes;
    private int ballsLeft;
    private boolean hasWon;

    public Player(String name) {
        this.name = name;
        isStripes = false;
        ballsLeft = 8;
        hasWon = false;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isStripes() {
        return isStripes;
    }

    public void setStripes(boolean stripes) {
        isStripes = stripes;
    }

    public int getBallsLeft() {
        return ballsLeft;
    }

    public void setBallsLeft(int ballsLeft) {
        this.ballsLeft = ballsLeft;
    }

    public boolean isHasWon() {
        return hasWon;
    }

    public void setHasWon(boolean hasWon) {
        this.hasWon = hasWon;
    }
}
