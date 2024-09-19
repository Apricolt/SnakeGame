/**
 * The Snake class implements the Prototype design pattern to allow the creation
 * of copies of the current snake with its current state. This is done through the
 * `clone()` method, which creates a deep copy of the `Snake` object.
 */

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;

public class SnakePrototype implements Cloneable {
    private ArrayList<Point> body;
    private Point direction;
    private boolean growing;
    
    public SnakePrototype() {
        body = new ArrayList<>();
        body.add(new Point(10, 10)); // starting point
        direction = new Point(1, 0); // moving right
        growing = false;
    }
    
    public void move() {
        Point head = body.get(0);
        Point newHead = new Point(head.x + direction.x, head.y + direction.y);
        body.add(0, newHead);
        
        if (!growing) {
            body.remove(body.size() - 1);
        } else {
            growing = false;
        }
    }
    
    public void grow() {
        growing = true;
    }
    
    public void handleKeyPress(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
                if (direction.y == 0) direction = new Point(0, -1);
                break;
            case KeyEvent.VK_DOWN:
                if (direction.y == 0) direction = new Point(0, 1);
                break;
            case KeyEvent.VK_LEFT:
                if (direction.x == 0) direction = new Point(-1, 0);
                break;
            case KeyEvent.VK_RIGHT:
                if (direction.x == 0) direction = new Point(1, 0);
                break;
        }
    }
    
    public boolean checkCollisionWithFood(Food food) {
        return body.get(0).equals(food.getPosition());
    }
    
    public boolean checkCollisionWithSelf() {
        Point head = body.get(0);
        return body.subList(1, body.size()).contains(head);
    }
    
    public boolean checkCollisionWithWalls() {
        Point head = body.get(0);
        return head.x < 0 || head.x >= SnakeGame.GRID_SIZE || head.y < 0 || head.y >= SnakeGame.GRID_SIZE;
    }
    
    public void draw(Graphics g) {
        g.setColor(Color.GREEN);
        for (Point p : body) {
            g.fillRect(p.x * SnakeGame.TILE_SIZE, p.y * SnakeGame.TILE_SIZE, SnakeGame.TILE_SIZE, SnakeGame.TILE_SIZE);
        }
    }

    @Override
    public SnakePrototype clone() {
        try {
            SnakePrototype cloned = (SnakePrototype) super.clone();
            cloned.body = new ArrayList<>(this.body);
            cloned.direction = (Point) this.direction.clone();
            return cloned;
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }
}
