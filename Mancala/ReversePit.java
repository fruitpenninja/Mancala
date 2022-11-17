import java.awt.geom.*;
import java.awt.*;
import java.util.*;

public class ReversePit implements Pit{
    private int stones;
    private double x;
    private double y;
    private double diameter;
    private ArrayList<Ellipse2D> stoneList;
    private int currentRow = 0;
    private int currentCol = 0;

    public ReversePit(int stoneCount, double x, double y, double diameter){
        stones = 0;
        this.x = x;
        this.y = y;
        this.diameter = diameter;
        stoneList = new ArrayList<Ellipse2D>();
        addStones(stoneCount);
    }

    public void addStones(int amount){
        stones += amount;
        while(stoneList.size() < stones){
            if(currentCol > 8){
                currentRow++;
                currentCol = 0;
            }
            double tempX = x + diameter/12 + diameter / 10 * currentCol;
            double tempY = y + diameter/8 + diameter / 8 * currentRow;
            stoneList.add(new Ellipse2D.Double(tempX, tempY, diameter/15, diameter/15));
            currentCol++;
        }
    }

    public void removeStones(int amount){
        stones -= amount;
    }

    public int getStoneCount(){
        return stones;
    }
    
    public void draw(Graphics2D g2){
        RoundRectangle2D hole = new RoundRectangle2D.Double(x, y, diameter, diameter, 10, 10);
        g2.draw(hole);
        for(int i = 0; i < stoneList.size(); i++){
            g2.draw(stoneList.get(i));
            g2.fill(stoneList.get(i));
        }
    }
}