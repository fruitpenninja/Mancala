import java.awt.geom.*;
import java.awt.*;
import java.util.*;
import javax.swing.event.ChangeListener;

// THIS IS STYLE 1
public class PitOrMancalaStyle1 implements GeneralShape{
    private int numStones;
    private double x;
    private double y;
    private double diameterStone;
    private ArrayList<Ellipse2D> stoneList;
    private GeneralPath path;
    private String type;
    private DataModel dataModel;
    private int index;

    /**
     * A pit of a given diameter contains an initial number of stones at a given location
     * @param stoneCount - Initial number of stones
     * @param x - Top left x position of drawing area of pit
     * @param y - Top left y positiion of drawing area of pit
     * @param diameter - diameter of pit
     */
    
    // need tp adjust, pass in the diameter of stone instead of diameterPit
    public PitOrMancalaStyle1(String type, DataModel data, int numStones, int index, double x, double y, double width, double height, double diameter){
        this.type = type;
        dataModel = data;
        path = new GeneralPath();
        
        this.numStones = numStones;
        this.x = x;
        this.y = y;
        this.diameterStone = diameter;
        stoneList = new ArrayList<Ellipse2D>();
        
        if (type.equalsIgnoreCase("mancala")) {
            // initialize mancala shape for style 1
            RoundRectangle2D mancalaShape = new RoundRectangle2D.Double(x, y, width, height, 10, 10);
            path.append(mancalaShape, false);
            
        }
        else if (type.equalsIgnoreCase("pit")) {
            // initialize pit shape for style 1
            Ellipse2D pitShape = new Ellipse2D.Double(x, y, width, height);
            path.append(pitShape, false);
            
        }
        
        this.index = index;
        populateStones(numStones);        // MIGHT NEED TO DELETE PARAMETER PASSED IN TO METHOD AND IN INTERFACE ALSO
    }

    /**
     * Adds the specified number of stones to the pit
     * @param amount - number of stones to add
     */
    public void populateStones(int numStones){    // MIGHT NEED TO DELETE PARAMETER numStones PASSED IN TO METHOD AND IN INTERFACE ALSO
//        if(stones < amount){
        
        // remove all stones inside the pit before set new value;
        while (stoneList.size() != 0) {
            stoneList.remove(stoneList.size()-1);
        }
        
        // start populate stones to arraylist
        int currentCol= 1;
        int currentRow = 1;
        while(stoneList.size() < numStones){
            if(type.equals("pit")){
                if(currentCol > 5){
                    currentRow++;
                    currentCol = 1;
                }
                double tempX = x + diameterStone/8 + diameterStone / 8 * currentCol;
                double tempY = y + diameterStone/8 + diameterStone / 8 * currentRow;
                stoneList.add(new Ellipse2D.Double(tempX, tempY, diameterStone/15, diameterStone/15));
                currentCol++;
            }
            else{
                if(currentRow > 12){
                    currentCol++;
                    currentRow = 1;
                }
                double tempX = x + 3*diameterStone/8 + diameterStone / 8 * currentCol;
                double tempY = y + diameterStone + diameterStone / 8 * currentRow;
                stoneList.add(new Ellipse2D.Double(tempX, tempY, diameterStone/15, diameterStone/15));
                currentRow++;
            }
        }
    }

    public boolean Clicked(Point2D p){
        return path.contains(p);
    }

    /**
     * Gives the current number of stones in a pit
     * @return - number of stones the pit currently has
     */
    public int getStoneCount(){
        return numStones;
    }

    /**
     * Drawing a pit and stones in it
     * @param g2 - Graphics2D object for drawing the
     */
    public void draw(Graphics2D g2){
        g2.draw(path);
        for(int i = 0; i < stoneList.size(); i++){
            g2.draw(stoneList.get(i));
            g2.fill(stoneList.get(i));
        }
    }

}