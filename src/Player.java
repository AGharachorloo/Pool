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

}
