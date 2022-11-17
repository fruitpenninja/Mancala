import java.awt.geom.*;
import java.awt.*;
import java.util.*;

public class ReverseMancala implements Pit{
    private int stones;
    private double x;
    private double y;
    private double length;
    private double width;
    private double diameter;
    private ArrayList<Ellipse2D> stoneList;
    private int currentRow = 1;
    private int currentCol = 1;

    public ReverseMancala(double x, double y, double length, double width, double diameter){
        stones = 0;
        this.x = x;
        this.y = y;
        this.length = length;
        this.width = width;
        this.diameter = diameter;
        stoneList = new ArrayList<Ellipse2D>();
    }

    public void addStones(int amount){
        stones += amount;
        while(stoneList.size() < stones){
            if(currentRow > 24){
                currentCol++;
                currentRow = 1;
            }
            double tempX = x + length/2 - diameter/4 + diameter / 8 * currentCol;
            double tempY = y + width/6 + diameter / 8 * currentRow;
            stoneList.add(new Ellipse2D.Double(tempX, tempY, diameter/15, diameter/15));
            currentRow++;
        }
    }

    public int getStoneCount(){
        return stones;
    }

    public void draw(Graphics2D g2){
        Ellipse2D mancala = new Ellipse2D.Double(x, y, length, width);
        g2.draw(mancala);
        for(int i = 0; i < stoneList.size(); i++){
            g2.draw(stoneList.get(i));
            g2.fill(stoneList.get(i));
        }
    }
}