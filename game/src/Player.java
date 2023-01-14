package game.src;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.Color;
import org.newdawn.slick.Input;
import org.newdawn.slick.GameContainer;
import java.lang.Math;

public class Player extends GameObject {
  /** Variables used to represent the current state of a Player object. */
  public float x, y, width, height, weight, velocityY;
  public boolean onPlatform;
  public Shape boundingBox;

  /**
   * The Player constructor. 
   * 
   * @param x : The current x coordinate of the Player relative to the game world.
   * @param y : The current y coordinate of the Player relative to the game world.
   * @param width : The width of the Player's sprite.
   * @param height : The height of the Player's sprite.
   * @param weight : The weight of the Player i.e. how much they are affected by gravity.
   */
  public Player(float x, float y, float width, float height, float weight) {
    super(new Rectangle(x, y, width, height)); // This is so that the bounding box of the Player object is not set to
                                               // null.

    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;
    this.weight = weight;
    this.velocityY = 0;
    this.onPlatform = false;
    this.boundingBox = new Rectangle(x, y, width, height);
  }

  /**
   * Responsible for drawing the Player sprite.
   */
  public void draw(Graphics g) {
    g.setColor(Color.gray);
    g.fillRect(x, y, width, height);
  }

  /**
   * Responsible for updating all the variables of a Player object before the next
   * frame update.
   * Calculates the next position of the object on-screen depending on the keys
   * pressed and if it is airborne.
   * 
   */
  public void playerUpdate(GameContainer gc, int delta, int floorHeight, Platform platform) {
    this.boundingBox = new Rectangle(x, y, width, height); // Updates Shape field representing the Player object.

    /** x-Axis Movement using A and D keys to move left and right respectively. */
    if (gc.getInput().isKeyDown(Input.KEY_D))
      this.x += 0.8 * (float) delta;
    else if (gc.getInput().isKeyDown(Input.KEY_A))
      this.x -= 0.8 * (float) delta;

    /** y-Axis Movement using Space to jump. */
    if (this.y >= floorHeight) // Ensure the player does not fall through the floor.
    {
      this.velocityY = 0;
      this.y = floorHeight;
      this.onPlatform = true;
    }

    if (gc.getInput().isKeyDown(Input.KEY_SPACE) && this.onPlatform) // Must be on the ground/platform to jump.
    {
      double jumpStrength = this.weight * -2 * -2f; // Based on the formula v = sqrt(-2gh) where g is the strength of gravity
                                            // and h is the jump height.
      this.velocityY = (float) Math.sqrt(jumpStrength); // Will result in the player moving upwards.
      this.onPlatform = false; // The player will be in the air so the boolean variable is set to false.
    }

    if (!this.onPlatform && this.velocityY > -2f) // If in the air and terminal velocity hasn't been reached.
      this.velocityY += -0.01f * delta; // Decelerates the player.

    this.y -= this.velocityY * delta; // Move the player on the y-axis based on their current Y velocity.

    collisionManager(platform); // Runs all the platform-based collision checks.
  }

  /**
   * Responsible for handling the collision physics between the player and
   * platforms.
   * 
   * @param gameObject : The platform to handle collsions with.
   */
  public void collisionManager(Platform gameObject) {
    if (this.boundingBox.intersects(gameObject.getBoundingBox())) { // If the player would be inside or touching the
                                                                    // platform.
      if (this.boundingBox.getMaxY() < gameObject.getBoundingBox().getMinY()) // If it is above the bottom of the
                                                                              // platform.
      {
        if (this.boundingBox.getMaxX() >= gameObject.getBoundingBox().getMinX() // If the right-side of the player is
                                                                                // past the left-side of the platform.
            && this.boundingBox.getMaxX() < gameObject.getBoundingBox().getMaxX()) // And it isn't past the right-side.
          this.x -= this.boundingBox.getMaxX() - gameObject.getBoundingBox().getMinX(); // Moves the player left by the
                                                                                        // cross-section.

        if (this.boundingBox.getMinX() <= gameObject.getBoundingBox().getMaxX() // If the left-side of the player is
                                                                                // past the right-side of the platform.
            && this.boundingBox.getMinX() > gameObject.getBoundingBox().getMinX()) // And it isn't past the left-side.
          this.x += gameObject.getBoundingBox().getMaxX() - this.boundingBox.getMinX(); // Moves the player right by the
                                                                                        // cross-section.
      }

      if (this.boundingBox.getMaxY() - 1 >= gameObject.getBoundingBox().getMinY() // If the bottom of player would be
                                                                                  // inside the platform.
          && this.boundingBox.getMaxY() < gameObject.getBoundingBox().getMaxY()) // And it isn't past the bottom of the
                                                                                 // platform.
      {
        this.velocityY = 0; // The player stops moving after landing on the platform.
        this.y = gameObject.getBoundingBox().getMinY() - this.height; // Makes sure that they stay above the platform.
        this.onPlatform = true; // Sets the boolean used for gravity physics to true.
      }

      if (this.boundingBox.getMinY() <= gameObject.getBoundingBox().getMaxY() // If the top of the player would be
                                                                              // inside the platform.
          && this.boundingBox.getMinY() > gameObject.getBoundingBox().getMinY()) // And it isn't past the top of the
                                                                                 // platform.
      {
        this.velocityY = 0; // The player losing their velocity from jumping.
        this.y = gameObject.getBoundingBox().getMaxY() + 10; // Makes the player fall.
        this.onPlatform = false; // Sets the boolean to false.
      }
    } else if (this.y != gameObject.getBoundingBox().getMinY() // If the bottom of the player isn't equal to the top of
                                                               // the platform (The player isn't on the platform).
        || this.x > gameObject.boundingBox.getMaxX() || this.x < gameObject.boundingBox.getMinX()) // If not in the
                                                                                                   // horizontal range
                                                                                                   // of the platform.
      this.onPlatform = false; // The player must not be on the platform.
  }

}
