package game.src;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Color;

/**
 * A simple platforming game using Slick2D.
 */
public class Game extends BasicGame {
    /** Screen width */
    private static final int WIDTH = 640;
    /** Screen height */
    private static final int HEIGHT = 480;
    /** Height from bottom of screen to floor. */    
    public static int floorHeight = HEIGHT - 64;
    /** Array containing all GameObjetcs in the world. */
    public GameObject[] gameObjects = new GameObject[3];

    /** Variables used to timeout the player if too long is taken to reach the goal. */
    public long timeStarted;
    public long timeOut;
    
    public Game() {
        super("Simple Platformer");
    }

    /**
     * Renders the environment with all GameObjects and world every frame update.
     */
    public void render(GameContainer container, Graphics g) throws SlickException {
        g.setColor(Color.white); // Floor
        g.fillRect(0, HEIGHT - 32, WIDTH, 32);

        String timeLeft = "Time Elapsed: " + Long.toString((System.currentTimeMillis() - timeStarted)/1000);
        g.drawString(timeLeft, 490, 10);

        // Renders all GameObjects.
        for (GameObject gameObject: gameObjects) {
            gameObject.draw(g);
        }
    }

    /** Initial values for player variables. */
    @Override
    public void init(GameContainer container) throws SlickException {
        float x = 0;
        float y = floorHeight;
        float weight = 1.5f;
        timeStarted = System.currentTimeMillis();
        timeOut = 10000;

        // Creates the initial GameObjects including the player.
        Player player = new Player(x, y, 32, 32, weight);
        Goal goal1 = new Goal(250, 40, 10, 10);
        Platform platform1 = new Platform(320, 250, 100, 20);

        // Adds the GameObjects to the array.
        gameObjects[0] = player;
        gameObjects[1] = goal1;
        gameObjects[2] = platform1;
    }

    /** Function that runs after each frame update. 
     * Has a 'delta' variable which stores the number of milliseconds between frame updates. 
     * Constantly checks for button presses as well as collisions between objects in the world. */
    @Override
    public void update(GameContainer gc, int delta) throws SlickException {
        ((Player)gameObjects[0]).playerUpdate(gc, delta, floorHeight, (Platform)gameObjects[2]);
        // If the player collides with the goal or if they timeout, the game is quit.
        if (System.currentTimeMillis() - timeStarted >= timeOut || 
            ((Player)gameObjects[0]).boundingBox.intersects(gameObjects[1].getBoundingBox())) gc.exit();

        if (((Player)gameObjects[0]).x >= WIDTH) ((Player)gameObjects[0]).x = 0; // If past the right vertical boundary of the screen, the player seamlessly comes from the left side.

        if (((Player)gameObjects[0]).x < 0) ((Player)gameObjects[0]).x = WIDTH; // If past the left vertical boundary of the screen, the player seamlessly comes from the right side.
    }
    
    /**
     * Main function that runs the game.
     * The current time in milliseconds is stored within startTime.
     * After the game is exited, either by the goal being reached or by timeout, the time taken is calculated using startTime.
     * 
     * @param args
     * @throws SlickException
     */
    public static void main(String[] args) throws SlickException {
        AppGameContainer app = new AppGameContainer(new Game());
        app.setDisplayMode(WIDTH, HEIGHT, false);
        app.setForceExit(false);
        app.setTargetFrameRate(60);

        long startTime = System.currentTimeMillis();
        app.start();

        if (System.currentTimeMillis() - startTime > 10000)
            System.out.println("Timeout!");
        else
        {
            long score = 10000 - (System.currentTimeMillis() - startTime);
            System.out.println("Score: " + score);
        }
            
    }

}
