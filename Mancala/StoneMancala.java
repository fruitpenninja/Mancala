import java.awt.geom.*;
import java.awt.*;
import java.util.*;

public class StoneMancala implements Mancala{
    private int stones;
    private double x;
    private double y;
    private double length;
    private double width;
    private double diameter;
    private ArrayList<Ellipse2D> stoneList;
    private int currentRow = 1;
    private int currentCol = 1;

    /**
     * A mancala is an elongated rectangle that holds the stones a player has won
     * @param x - x coordinate of drawing rectangle
     * @param y - y coordinate of drawing rectangle
     * @param length - length of Mancala
     * @param width - width of Mancala
     * @param diameter - diameter of pits to help draw stones uniformly
     */
    public StoneMancala(double x, double y, double length, double width, double diameter){
        stones = 0;
        this.x = x;
        this.y = y;
        this.length = length;
        this.width = width;
        this.diameter = diameter;
        stoneList = new ArrayList<Ellipse2D>();
    }

    /**
     * Adds a specifid number of stones to the Mancala
     * @param amount - number of stones to add
     */
    public void addStones(int amount){
        stones += amount;
        while(stoneList.size() < stones){
            if(currentRow > 12){
                currentCol++;
                currentRow = 1;
            }
            double tempX = x + diameter/8 + diameter / 8 * currentCol;
            double tempY = y + diameter/8 + diameter / 8 * currentRow;
            stoneList.add(new Ellipse2D.Double(tempX, tempY, diameter/15, diameter/15));
            currentRow++;
        }
    }

    /**
     * Tells how many stones the Mancala has
     * @return - number of stones in Mancala
     */
    public int getStoneCount(){
        return stones;
    }

    /**
     * Drawing a Mancala and any stones it has in it
     * @param g2
     */
    public void draw(Graphics2D g2){
        RoundRectangle2D mancala = new RoundRectangle2D.Double(x, y, length, width, 10, 10);
        g2.draw(mancala);
        for(int i = 0; i < stoneList.size(); i++){
            g2.draw(stoneList.get(i));
            g2.fill(stoneList.get(i));
        }
    }
}