/**
 * The GameManager class uses the Singleton design pattern to ensure that only one instance of 
 * the GameManager exists throughout the application. This pattern provides a global point of 
 * access to the single instance, which is crucial for managing shared game state, such as the 
 * Snake and Food objects. By making the constructor private and controlling instance creation 
 * through the `getInstance()` method, the pattern ensures that only one GameManager instance 
 * is created and used, which helps in maintaining consistency and centralizing game management 
 * logic. The use of synchronized keyword in `getInstance()` method also ensures thread-safety 
 * when creating the instance in a multi-threaded environment.
 */


public class GameManager {
    private static GameManager instance; 
    private SnakePrototype snake; 
    private Food food; 
    
    private GameManager() { 
        snake = new SnakePrototype();
        food = new Food();
    }
    
    public static synchronized GameManager getInstance() { 
        if (instance == null) {
            instance = new GameManager(); 
        }
        return instance; 
    }
    
    public SnakePrototype getSnake() {
        return snake; 
    }
    
    public Food getFood() {
        return food; 
    }
}
