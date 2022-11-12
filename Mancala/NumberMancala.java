import java.awt.geom.*;
import java.awt.*;

public class NumberMancala implements Mancala{
    private int stones;
    private double x;
    private double y;
    private double length;
    private double width;

    public NumberMancala(double x, double y, double length, double width){
        stones = 0;
        this.x = x;
        this.y = y;
        this.length = length;
        this.width = width;
    }

    public void addStones(int amount){
        stones += amount;
    }

    public int getStoneCount(){
        return stones;
    }

    public void draw(Graphics2D g2){
        RoundRectangle2D mancala = new RoundRectangle2D.Double(x, y, length, width, 10, 10);
        g2.draw(mancala);
        FontMetrics fm = g2.getFontMetrics();
        String stringStone = Integer.toString(stones);
        double numberWidth = fm.getStringBounds(stringStone, g2).getWidth();
        g2.drawString(stringStone, (float)(x + length/2 - numberWidth/2), (float)(y + width/2 + fm.getAscent()/2));
    }
}