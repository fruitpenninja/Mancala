import java.awt.*;
public interface Pit{
    public void initializeStones(int amount);
    public int getStoneCount();
    public void draw(Graphics2D g2);
}