import java.awt.*;
import java.awt.geom.Point2D;

/**
 * This interface (Strategy interface) represents the shape for a pit or mancala depending on the style user choose
 * @author Quang Le, Brian Tran, Moe Pyae Sone
 *
 */
public interface GeneralShape{
    public void populateStones(int amount);         // populate stones to the pit or mancala
    public boolean Clicked(Point2D p);              // check if the pit or mancala it represents clicked
    public void draw(Graphics2D g2);                // draw the shape
}