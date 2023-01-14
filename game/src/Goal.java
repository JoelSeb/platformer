package game.src;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.Color;

public class Goal extends GameObject {
  public float x, y, radius;
  public int cornerRadius;
  public Shape boundingBox;

  public Goal(float x, float y, float radius, int cornerRadius) {
    super(new Circle(x + radius, y + radius, cornerRadius));
    
    this.x = x;
    this.y = y;
    this.radius = radius;
    this.cornerRadius = cornerRadius;

    float centerX = x + radius;
    float centerY = y + radius;
    this.boundingBox = new Circle(centerX, centerY, radius);
  }

  /**
   * Responsible for drawing the Goal sprite.
   */
  public void draw(Graphics g) {
    float diameter = 2 * radius;
    g.setColor(Color.red);
    g.fillRoundRect(x, y, diameter, diameter, cornerRadius);
  }
}
