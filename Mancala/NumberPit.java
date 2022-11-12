import java.awt.geom.*;
import java.awt.*;

public class NumberPit implements Pit{
    private int stones;
    private double x;
    private double y;
    private double diameter;

    public NumberPit(int stoneCount, double x, double y, double diameter){
        stones = 0;
        this.x = x;
        this.y = y;
        this.diameter = diameter;
        addStones(stoneCount);
    }

    public void addStones(int amount){
        stones += amount;
    }

    public void removeStones(int amount){
        stones -= amount;
    }

    public int getStoneCount(){
        return stones;
    }
    
    public void draw(Graphics2D g2){
        Ellipse2D hole = new Ellipse2D.Double(x, y, diameter, diameter);
        g2.draw(hole);
        String stringStone = Integer.toString(stones);
        FontMetrics fm = g2.getFontMetrics();
        double numberWidth = fm.getStringBounds(stringStone, g2).getWidth();
        g2.drawString(stringStone, (float)(x + diameter/2 - numberWidth/2), (float)(y + diameter/2 + fm.getAscent()/2));
    }
}