import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;

public class MancalaBoard extends JPanel{
    private DataModel dataModel;
    
    private Pits[] Player1Pits;
    private Pits[] Player2Pits;
    
    private Mancala Player1Mancala;
    private Mancala Player2Mancala;
    
    final double PANEL_WIDTH;
    final double PANEL_HEIGHT;
    
    final double BOARD_WIDTH;   // width of board rectangle
    final double BOARD_HEIGHT;  // height of board rectangle
    private double xPos;
    private double yPos;
    
    private int stoneCount;

    /**
     * A Mancala Board sizes according to the size of the window containing it and initializes how many stones all pits
     * @param dataModel - the data model holding all necessary data of the game
     */
    public MancalaBoard(DataModel dataModel){
        this.dataModel = dataModel;
        this.PANEL_WIDTH = dataModel.getPanel1Width();      // 800  
        this.PANEL_HEIGHT = dataModel.getPanel1Height();    // 400
        
        BOARD_WIDTH = PANEL_WIDTH * 3 / 4;                  // 600
        BOARD_HEIGHT = PANEL_HEIGHT - 100;                  // 300
        
        setPreferredSize(new Dimension(dataModel.getFrameWidth(), dataModel.getFrameHeight() / 2));
        //Calculate coordiantes to draw board from
        xPos = (PANEL_WIDTH - BOARD_WIDTH)/2;
        yPos = 20;

        Player1Pits = new Pits[6];
        Player2Pits = new Pits[6];
        
        // Get the number of stones for each pit
        stoneCount = dataModel.getNumStones();

        //Initialize the pits for each player
        double startX = BOARD_WIDTH * 0.4 / 2;
        for(int i = 0; i < Player1Pits.length; i++){
            Player1Pits[i] = new Pits(stoneCount, xPos + startX + BOARD_WIDTH * i * 0.1, yPos + BOARD_HEIGHT * 0.72, BOARD_WIDTH * 0.08);
        }

        for(int i = 0; i < Player2Pits.length; i++){
            Player2Pits[i] = new Pits(stoneCount, xPos + startX + BOARD_WIDTH * i * 0.1, yPos + BOARD_HEIGHT * 0.1, BOARD_WIDTH* 0.08);
        }

        //Initialize the Mancalas for each player
        Player1Mancala = new Mancala(xPos + BOARD_WIDTH * 0.85, yPos + BOARD_HEIGHT * 0.1, BOARD_WIDTH * 0.1, BOARD_HEIGHT * 0.8, BOARD_WIDTH* 0.08);
        Player2Mancala = new Mancala(xPos + BOARD_WIDTH * 0.05, yPos + BOARD_HEIGHT * 0.1, BOARD_WIDTH * 0.1, BOARD_HEIGHT * 0.8, BOARD_WIDTH* 0.08);
        
    }

    /**
     * Paint component method for drawing the board and it's pits and Mancalas
     */
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        RoundRectangle2D board = new RoundRectangle2D.Double(xPos, yPos, BOARD_WIDTH, BOARD_HEIGHT, 10, 10);
        g2.draw(board);
        for(int pit = 0; pit < Player1Pits.length; pit++){
            Player1Pits[pit].draw(g2);
            Player2Pits[pit].draw(g2);
        }
        Player1Mancala.draw(g2);
        Player2Mancala.draw(g2);
    }
}