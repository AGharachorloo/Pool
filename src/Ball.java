import java.awt.*;

public class Ball {
    private final int RADIUS = 10;
    private double x, y, vx, vy;
    private Color color;

    public Ball(double x, double y, Color color) {
        this.x = x;
        this.y = y;
        this.color = color;
    }

    public void setVelocity(double vx, double vy) {
        this.vx = vx;
        this.vy = vy;
    }

    // Move the ball & apply friction and wall collisions
    public void update() {
        x += vx;
        y += vy;
        vx *= 0.988;
        vy *= 0.988;
        if (x - RADIUS < 68 && vx < 0 || x + RADIUS > GameView.WINDOW_WIDTH - 68 && vx > 0) {
            vx = -vx;
        }
        if (y - RADIUS < 68 && vy < 0 || y + RADIUS > GameView.WINDOW_HEIGHT - 67 && vy > 0) {
            vy = -vy;
        }
        if (Math.abs(vx) < 0.02) {
            vx = 0.0;
        }
        if (Math.abs(vy) < 0.02) {
            vy = 0.0;
        }
    }

    // Draw ball on window
    public void draw(Graphics g) {
        g.setColor(color);
        g.fillOval((int)(x - RADIUS), (int)(y - RADIUS), RADIUS * 2, RADIUS * 2);
    }

    // Return true if this ball is touching another ball
    public boolean collidesWith(Ball other) {
        double dx = x - other.x;
        double dy = y - other.y;
        return Math.hypot(dx, dy) < 2 * RADIUS;
    }

    // Lots of funky physics here, handles collisions between 2 balls
    public void resolveCollision(Ball other) {
        double dx = other.x - x;
        double dy = other.y - y;
        double dist = Math.hypot(dx, dy);

        if (dist == 0) {
            return;
        }
        // Find and fix any overlap between the balls
        double overlap = (2*RADIUS) - dist;
        double pushX = (dx / dist) * (overlap / 2);
        double pushY = (dy / dist) * (overlap / 2);
        x -= pushX;
        y -= pushY;
        other.x += pushX;
        other.y += pushY;

        // Determine normal (line of impact) and tangent (direction perpendicular to impact) vectors
        // So we can worry about the physics separately along each axis
        double nx = dx / dist;
        double ny = dy / dist;
        double tx = -ny;
        double ty = nx;

        // Use dot product to project velocities onto normal & tangent
        double dpTan1 = vx * tx + vy * ty;
        double dpTan2 = other.vx * tx + other.vy * ty;

        double dpNorm1 = vx * nx + vy *ny;
        double dpNorm2 = other.vx * nx + other.vy * ny;

        // Elastic collision between balls of same mass (simplified for no energy loss),
        // Normal components of velocities are swapped
        double m1 = dpNorm2;
        double m2 = dpNorm1;

        // Now update the velocities
        // Combining tangential & new post-swap normal component
        vx = tx * dpTan1 + nx * m1;
        vy = ty * dpTan1 + ny * m1;
        other.vx = tx * dpTan2 + nx * m2;
        other.vy = ty * dpTan2 + ny * m2;

    }

    // Return true if a point is inside this ball
    public boolean contains(int checkX, int checkY) {
        double dist = Math.sqrt((checkX - x) * (checkX - x) + (checkY - y) * (checkY - y));
        return dist < RADIUS;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public Color getColor() {
        return color;
    }

    public double getVx() {
        return vx;
    }

    public void setVx(double vx) {
        this.vx = vx;
    }

    public double getVy() {
        return vy;
    }

    public void setVy(double vy) {
        this.vy = vy;
    }

    public final double getRadius() {
        return RADIUS;
    }
}
