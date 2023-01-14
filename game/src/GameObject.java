package game.src;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Shape;

public abstract class GameObject{
  private Shape boundingBox;

  /**
   * Returns the bounding box of the GameObject.
   * 
   * @return : Returns the Shape object representing the GameObject.
   */
  public Shape getBoundingBox() 
  {
    return this.boundingBox;
  }

  public GameObject(Shape boundingBox) {
    this.boundingBox = boundingBox;
  }

  protected void update()
  {
  }

  protected void draw(Graphics g)
  {
  }

  /**
   * Function to check if two bounding boxes intersect.
   * 
   * @param gameObject : The GameObject to check if intersected.
   * @return : Returns true if the GameObjects intersect each other.
   */
  public boolean intersects(GameObject gameObject) {
    return this.getBoundingBox().intersects(gameObject.getBoundingBox());
  }
}