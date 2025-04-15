import javax.swing.*;
import java.awt.*;

public class GameView {
    private final int INTRO_SCREEN = 0, GAME_SCREEN = 1, END_SCREEN = 2;
    private Image instructions, table, stick, meter, intro, results;
    private int[] winX, winY;

    public GameView {
        instructions = new ImageIcon();
        table = new ImageIcon();
        stick = new ImageIcon();
        meter = new ImageIcon();
        intro = new ImageIcon();
        results = new ImageIcon();
        winX = new int[6];
        winY = new int[6];

        for (int i = 0; i < 6; i++) {
            winX[i] = (i%2)*300 + 100;
            winY[i] = (i/2)*300 + 50;
        }
    }
}
