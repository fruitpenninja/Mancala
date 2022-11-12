import java.awt.geom.*;
import java.awt.*;
import java.util.*;

public class StonePit implements Pit{
    private int stones;
    private double x;
    private double y;
    private double diameter;
    private ArrayList<Ellipse2D> stoneList;
    private int currentRow = 1;
    private int currentCol = 1;

    /**
     * A pit of a given diameter contains an initial number of stones at a given location
     * @param stoneCount - Initial number of stones
     * @param x - Top left x position of drawing area of pit
     * @param y - Top left y positiion of drawing area of pit
     * @param diameter - diameter of pit
     */
    public StonePit(int stoneCount, double x, double y, double diameter){
        stones = 0;
        this.x = x;
        this.y = y;
        this.diameter = diameter;
        stoneList = new ArrayList<Ellipse2D>();
        addStones(stoneCount);
    }

    /**
     * Adds the specified number of stones to the pit
     * @param amount - number of stones to add
     */
    public void addStones(int amount){
        stones += amount;
        while(stoneList.size() < stones){
            if(currentCol > 5){
                currentRow++;
                currentCol = 1;
            }
            double tempX = x + diameter/8 + diameter / 8 * currentCol;
            double tempY = y + diameter/8 + diameter / 8 * currentRow;
            stoneList.add(new Ellipse2D.Double(tempX, tempY, diameter/15, diameter/15));
            currentCol++;
        }
    }

    /**
     * Removes a specified number of stones from the pit
     * @param amount - number of stones to remove
     */
    public void removeStones(int amount){
        stones -= amount;
        for(int i = 0; i < amount; i++){
            stoneList.remove(stoneList.size() - 1);
        }
    }

    /**
     * Gives the current number of stones in a pit
     * @return - number of stones the pit currently has
     */
    public int getStoneCount(){
        return stones;
    }

    /**
     * Drawing a pit and stones in it
     * @param g2 - Graphics2D object for drawing the
     */
    public void draw(Graphics2D g2){
        Ellipse2D hole = new Ellipse2D.Double(x, y, diameter, diameter);
        g2.draw(hole);
        for(int i = 0; i < stoneList.size(); i++){
            g2.draw(stoneList.get(i));
            g2.fill(stoneList.get(i));
        }
    }

}