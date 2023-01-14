package game.src;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.Color;

public class Platform extends GameObject{
  /** Variables used to represent the current state of a platform object. */
  public float x, y, width, height;
  public Shape boundingBox;

  /**
   * The Platform constructor.
   * 
   * @param x : The current x coordinate of the Platform relative to the game world.
   * @param y : The current y coordinate of the Platform relative to the game world.
   * @param width : The width of the Platform's sprite.
   * @param height : The height of the Platform's sprite.
   */
  public Platform(float x, float y, float width, float height) {
    super(new Rectangle(x, y, width, height)); // This is so that the bounding box of the Player object is not set to
                                               // null.

    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;
    this.boundingBox = new Rectangle(x, y, width, height);
  }

  /**
   * Responsible for drawing the Platform sprite.
   */
  public void draw(Graphics g) {
    g.setColor(Color.blue);
    g.fillRect(x, y, width, height);
  }
}
