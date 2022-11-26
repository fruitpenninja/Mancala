import java.awt.*;
import java.awt.geom.Point2D;
public interface GeneralShape{
    public void populateStones(int amount);
    public boolean Clicked(Point2D p);
    public void draw(Graphics2D g2);
}