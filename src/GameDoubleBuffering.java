import java.awt.*;
import java.awt.image.BufferStrategy;

public class GameDoubleBuffering extends GameView{
    // TODO: modify this constructor to accept an array of Balls, not just one Ball.
    public GameDoubleBuffering(Game game) {
        super(game);
        createBufferStrategy(2);
    }

    @Override
    public void paint(Graphics g) {
        BufferStrategy bf = this.getBufferStrategy();
        if (bf == null)
            return;

        Graphics g2 = null;

        try {
            g2 = bf.getDrawGraphics();
            super.paint(g2);
        }
        finally {
            g2.dispose();
        }

        bf.show();

        Toolkit.getDefaultToolkit().sync();
    }
}