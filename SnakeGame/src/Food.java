import java.awt.*;
import java.util.Random;

public class Food {
    private Point position;
    private Random random;
    
    public Food() {
        random = new Random();
        relocate();
    }
    
    public void relocate() {
        position = new Point(random.nextInt(SnakeGame.GRID_SIZE), random.nextInt(SnakeGame.GRID_SIZE));
    }
    
    public Point getPosition() {
        return position;
    }
    
    public void draw(Graphics g) {
        g.setColor(Color.blue);
        g.fillRect(position.x * SnakeGame.TILE_SIZE, position.y * SnakeGame.TILE_SIZE, SnakeGame.TILE_SIZE, SnakeGame.TILE_SIZE);
    }
}
