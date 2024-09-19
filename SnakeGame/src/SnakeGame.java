import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;

public class SnakeGame extends JPanel implements ActionListener, KeyListener {
    public static final int TILE_SIZE = 20;
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    private static final int NUM_TILES_X = WIDTH / TILE_SIZE;
    private static final int NUM_TILES_Y = HEIGHT / TILE_SIZE;
    private static final int INIT_LENGTH = 3;
    private static final int DELAY = 100;

    private ArrayList<Point> snake;
    private Point food;
    private boolean gameOver;
    private boolean gamePaused;
    private boolean gameStarted;
    private char direction;
    private Timer timer;

    public SnakeGame() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.BLACK); // Game background
        setFocusable(true);
        addKeyListener(this);
        initGame();
    }

    private void initGame() {
        snake = new ArrayList<>();
        direction = 'R'; // Initial direction
        for (int i = INIT_LENGTH - 1; i >= 0; i--) {
            snake.add(new Point(i, 0));
        }
        food = generateRandomFood();
        gameOver = false;
        gamePaused = false;
        gameStarted = false;

        if (timer != null) {
            timer.stop(); // Stop the timer if it is running
        }
        timer = new Timer(DELAY, this);
    }

    private Point generateRandomFood() {
        Random random = new Random();
        Point newFood;
        do {
            newFood = new Point(random.nextInt(NUM_TILES_X), random.nextInt(NUM_TILES_Y));
        } while (snake.contains(newFood)); // Ensure the food does not appear on the snake
        return newFood;
    }

    private void checkFoodCollision() {
        Point head = snake.get(0);
        if (head.equals(food)) {
            // Grow the snake
            snake.add(new Point(-1, -1)); // Add a new segment to the end (it will move in the next move)

            // Generate new food
            food = generateRandomFood();
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    private void draw(Graphics g) {
        // Draw food
        g.setColor(Color.blue);
        g.fillRect(food.x * TILE_SIZE, food.y * TILE_SIZE, TILE_SIZE, TILE_SIZE);

        // Draw the snake
        g.setColor(Color.gray);
        for (Point p : snake) {
            g.fillRect(p.x * TILE_SIZE, p.y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
        }

        // Draw the score counter
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.PLAIN, 20));
        g.drawString("Score: " + (snake.size() - INIT_LENGTH), 10, 20);

        // Game Over message
        if (gameOver) {
            g.setFont(new Font("Arial", Font.PLAIN, 40));
            String message = "Game Over. Press Enter to Restart";
            FontMetrics metrics = g.getFontMetrics(g.getFont());
            int x = (WIDTH - metrics.stringWidth(message)) / 2;
            int y = (HEIGHT - metrics.getHeight()) / 2 + metrics.getAscent();
            g.setColor(Color.WHITE);
            g.drawString(message, x, y);
        }

        // Start game message
        if (!gameStarted && !gameOver) {
            g.setFont(new Font("Arial", Font.PLAIN, 40));
            g.drawString("Press Enter to Start", WIDTH / 4, HEIGHT / 2);
        }
    }

    private void move() {
        if (gameOver || gamePaused || !gameStarted) return; // Do not move if the game has not started

        // Move the snake
        Point head = snake.get(0);
        Point newHead = (Point) head.clone();

        switch (direction) {
            case 'U': newHead.translate(0, -1); break;
            case 'D': newHead.translate(0, 1); break;
            case 'L': newHead.translate(-1, 0); break;
            case 'R': newHead.translate(1, 0); break;
        }

        // Check collision with borders
        if (newHead.x < 0 || newHead.x >= NUM_TILES_X || newHead.y < 0 || newHead.y >= NUM_TILES_Y || snake.contains(newHead)) {
            gameOver = true;
            timer.stop();
        } else {
            snake.add(0, newHead);
            snake.remove(snake.size() - 1);
            checkFoodCollision();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyCode == KeyEvent.VK_ENTER) {
            if (gameOver) {
                initGame(); // Restart the game if in Game Over state
                gameStarted = true;
                timer.start();
            } else if (!gameStarted) {
                gameStarted = true;
                timer.start(); // Start the game if it has not started
            }
        } else if (keyCode == KeyEvent.VK_UP && direction != 'D') {
            direction = 'U';
        } else if (keyCode == KeyEvent.VK_DOWN && direction != 'U') {
            direction = 'D';
        } else if (keyCode == KeyEvent.VK_LEFT && direction != 'R') {
            direction = 'L';
        } else if (keyCode == KeyEvent.VK_RIGHT && direction != 'L') {
            direction = 'R';
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}
}
