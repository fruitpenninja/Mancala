import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;

public class MancalaBoardPanel extends JPanel{
    private DataModel dataModel;
    
    private Pit[] PlayerAPits;
    private Pit[] PlayerBPits;
    
    private Pit PlayerAMancala;
    private Pit PlayerBMancala;
    
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
    public MancalaBoardPanel(DataModel dataModel){
        this.dataModel = dataModel;
        this.PANEL_WIDTH = dataModel.getPanel1Width();      // 800  
        this.PANEL_HEIGHT = dataModel.getPanel1Height();    // 400
        
        BOARD_WIDTH = PANEL_WIDTH * 3 / 4;                  // 600
        BOARD_HEIGHT = PANEL_HEIGHT - 100;                  // 300
        
        setPreferredSize(new Dimension(dataModel.getFrameWidth(), dataModel.getFrameHeight() / 2));
        //Calculate coordiantes to draw board from
        xPos = (PANEL_WIDTH - BOARD_WIDTH)/2;
        yPos = 20;

        PlayerAPits = new Pit[6];
        PlayerBPits = new Pit[6];
        
        // Get the number of stones for each pit
//        stoneCount = dataModel.getNumStones();

        //Initialize the pits for each player
        double startX = BOARD_WIDTH * 0.4 / 2;
        for(int i = 0; i < PlayerAPits.length; i++){
            if(dataModel.getStyleBoard() == 1){
                PlayerAPits[i] = new StonePit(dataModel.getStonesInPitsA()[i], xPos + startX + BOARD_WIDTH * i * 0.1, yPos + BOARD_HEIGHT * 0.72, BOARD_WIDTH * 0.08);
            }
            else{
                PlayerAPits[i] = new ReversePit(dataModel.getStonesInPitsA()[i], xPos + startX + BOARD_WIDTH * i * 0.1, yPos + BOARD_HEIGHT * 0.72, BOARD_WIDTH * 0.08);
            }
            // save the ref of this pit to the data model
            dataModel.setPlayerAPit(i, PlayerAPits[i]);
        }

        for(int i = 0; i < PlayerBPits.length; i++){
            if(dataModel.getStyleBoard() == 1){
                PlayerBPits[i] = new StonePit(dataModel.getStonesInPitsB()[i], xPos + startX + BOARD_WIDTH * i * 0.1, yPos + BOARD_HEIGHT * 0.1, BOARD_WIDTH* 0.08);
            }
            else{
                PlayerBPits[i] = new ReversePit(dataModel.getStonesInPitsB()[i], xPos + startX + BOARD_WIDTH * i * 0.1, yPos + BOARD_HEIGHT * 0.1, BOARD_WIDTH* 0.08);
            }
            // save the ref of this pit to the data model
            dataModel.setPlayerBPit(i, PlayerAPits[i]);
        }

        //Initialize the Mancalas for each player
        if(dataModel.getStyleBoard() == 1){
            PlayerAMancala = new StoneMancala(xPos + BOARD_WIDTH * 0.85, yPos + BOARD_HEIGHT * 0.1, BOARD_WIDTH * 0.1, BOARD_HEIGHT * 0.8, BOARD_WIDTH* 0.08);
            PlayerBMancala = new StoneMancala(xPos + BOARD_WIDTH * 0.05, yPos + BOARD_HEIGHT * 0.1, BOARD_WIDTH * 0.1, BOARD_HEIGHT * 0.8, BOARD_WIDTH* 0.08);
        }
        else{
            PlayerAMancala = new ReverseMancala(xPos + BOARD_WIDTH * 0.85, yPos + BOARD_HEIGHT * 0.1, BOARD_WIDTH * 0.1, BOARD_HEIGHT * 0.8, BOARD_WIDTH* 0.08);
            PlayerBMancala = new ReverseMancala(xPos + BOARD_WIDTH * 0.05, yPos + BOARD_HEIGHT * 0.1, BOARD_WIDTH * 0.1, BOARD_HEIGHT * 0.8, BOARD_WIDTH* 0.08);
        }
        
        // Save these two Mancala reference to data model
        dataModel.setPlayerAMancala(PlayerAMancala);
        dataModel.setPlayerAMancala(PlayerBMancala);
        
    }

    /**
     * Paint component method for drawing the board and it's pits and Mancalas
     */
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        RoundRectangle2D board = new RoundRectangle2D.Double(xPos, yPos, BOARD_WIDTH, BOARD_HEIGHT, 10, 10);
        g2.draw(board);
        for(int pit = 0; pit < PlayerAPits.length; pit++){
            PlayerAPits[pit].draw(g2);
            PlayerBPits[pit].draw(g2);
        }
        PlayerAMancala.draw(g2);
        PlayerBMancala.draw(g2);
    }
}