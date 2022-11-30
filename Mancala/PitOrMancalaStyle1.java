import java.awt.geom.*;
import java.awt.*;
import java.util.*;
import javax.swing.event.ChangeListener;

/**
 * This class (Concrete class) represent the shape for pit/mancala in style 1
 * @author Quang Le, Brian Tran, Moe Pyae Sone
 *
 */
public class PitOrMancalaStyle1 implements GeneralShape{
    private int numStones;
    private double x;
    private double y;
    private double diameterStone;
    private ArrayList<Ellipse2D> stoneList;
    private GeneralPath path;
    private String type;
    private DataModel dataModel;

    /**
     * Constructor
     * @param type - pit or mancala
     * @param data - data model that the class can reference
     * @param numStones - initial number of stones inside
     * @param x - initial x position
     * @param y - initial y position
     * @param width - width of shape
     * @param height - height of shape
     * @param diameter - diameter of each stone
     */
    public PitOrMancalaStyle1(String type, DataModel data, int numStones, double x, double y, double width, double height, double diameter){
        this.type = type;
        dataModel = data;
        path = new GeneralPath();
        
        this.numStones = numStones;
        this.x = x;
        this.y = y;
        this.diameterStone = diameter;
        stoneList = new ArrayList<Ellipse2D>();
        
        if (type.equalsIgnoreCase("mancala")) {     // initialize mancala shape in style 1
            RoundRectangle2D mancalaShape = new RoundRectangle2D.Double(x, y, width, height, 10, 10);
            path.append(mancalaShape, false);
        }
        else if (type.equalsIgnoreCase("pit")) {    // initialize pit shape in style 1
            Ellipse2D pitShape = new Ellipse2D.Double(x, y, width, height);
            path.append(pitShape, false);
        }
        
        populateStones(numStones);       
    }

    /**
     * Adds the specified number of stones to the pit/mancala
     * @param amount - number of stones to add
     */
    public void populateStones(int numStones){
        // remove all stones inside the pit/mancala
        while (stoneList.size() != 0) {
            stoneList.remove(stoneList.size()-1);
        }
        
        // start populating stones and store to array list
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

    /**
     * Check if this pit/mancala itself is clicked
     */
    public boolean Clicked(Point2D p){
        return path.contains(p);
    }

    /**
     * Drawing the shape for pit/mancala and stones
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