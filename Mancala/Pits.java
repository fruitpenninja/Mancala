import java.awt.geom.*;
import java.awt.*;

public class Pits{
    private int stones;
    private double x;
    private double y;
    private double diameter;

    public Pits(int stoneCount, double x, double y, double diameter){
        stones = stoneCount;
        this.x = x;
        this.y = y;
        this.diameter = diameter;
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
    }

}