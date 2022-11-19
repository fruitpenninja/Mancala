import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.*;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class MancalaBoardPanel extends JPanel implements ChangeListener{
    private DataModel dataModel;
    
    private GeneralShape[] playerAPits;
    private GeneralShape[] playerBPits;
    
    private GeneralShape playerAMancala;
    private GeneralShape playerBMancala;
    
    final double PANEL_WIDTH;
    final double PANEL_HEIGHT;
    
    final double BOARD_WIDTH;   // width of board rectangle
    final double BOARD_HEIGHT;  // height of board rectangle
    private double xPos;
    private double yPos;

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
        
        playerAPits = new GeneralShape[6];
        playerBPits = new GeneralShape[6];
        
        
      //Initialize the pits for each player
        double startX = BOARD_WIDTH * 0.4 / 2;
        for(int i = 0; i < playerAPits.length; i++){
            if(dataModel.getStyleBoard() == 1){
                playerAPits[i] = new PitOrMancalaStyle1("pit", dataModel, dataModel.getStonesInPitsA()[i], i, xPos + startX + BOARD_WIDTH * i * 0.1, yPos + BOARD_HEIGHT * 0.72, BOARD_WIDTH * 0.08, BOARD_WIDTH * 0.08, BOARD_WIDTH * 0.08);
                playerBPits[i] = new PitOrMancalaStyle1("pit", dataModel, dataModel.getStonesInPitsB()[i], i, xPos + startX + BOARD_WIDTH * i * 0.1, yPos + BOARD_HEIGHT * 0.1, BOARD_WIDTH * 0.08, BOARD_WIDTH * 0.08, BOARD_WIDTH * 0.08);
            }
            else if (dataModel.getStyleBoard() == 2){
                playerAPits[i] = new PitOrMancalaStyle2("pit", dataModel, dataModel.getStonesInPitsA()[i], i, xPos + startX + BOARD_WIDTH * i * 0.1, yPos + BOARD_HEIGHT * 0.72, BOARD_WIDTH * 0.08, BOARD_WIDTH * 0.08, BOARD_WIDTH * 0.08);
                playerBPits[i] = new PitOrMancalaStyle2("pit", dataModel, dataModel.getStonesInPitsB()[i], i, xPos + startX + BOARD_WIDTH * i * 0.1, yPos + BOARD_HEIGHT * 0.1, BOARD_WIDTH * 0.08, BOARD_WIDTH * 0.08, BOARD_WIDTH * 0.08);
            }
        }

        //Initialize the Mancalas for each player
        if(dataModel.getStyleBoard() == 1){
            playerAMancala = new PitOrMancalaStyle1("mancala", dataModel, dataModel.getStonesInMancalaA(), 10, xPos + BOARD_WIDTH * 0.85, yPos + BOARD_HEIGHT * 0.1, BOARD_WIDTH * 0.1, BOARD_HEIGHT * 0.8, BOARD_WIDTH* 0.08);
            playerBMancala = new PitOrMancalaStyle1("mancala", dataModel, dataModel.getStonesInMancalaB(), 20, xPos + BOARD_WIDTH * 0.05, yPos + BOARD_HEIGHT * 0.1, BOARD_WIDTH * 0.1, BOARD_HEIGHT * 0.8, BOARD_WIDTH* 0.08);        }
        else if(dataModel.getStyleBoard() == 2){
            playerAMancala = new PitOrMancalaStyle2("mancala", dataModel, dataModel.getStonesInMancalaA(), 10, xPos + BOARD_WIDTH * 0.85, yPos + BOARD_HEIGHT * 0.1, BOARD_WIDTH * 0.1, BOARD_HEIGHT * 0.8, BOARD_WIDTH* 0.08);
            playerBMancala = new PitOrMancalaStyle2("mancala", dataModel, dataModel.getStonesInMancalaB(), 20, xPos + BOARD_WIDTH * 0.05, yPos + BOARD_HEIGHT * 0.1, BOARD_WIDTH * 0.1, BOARD_HEIGHT * 0.8, BOARD_WIDTH* 0.08);
        }

        // Mouse Listener
        MouseAdapter listenersForPits = mouseListeners();
        this.addMouseListener(listenersForPits);
        this.addMouseMotionListener(listenersForPits);
    }
    
    public MouseAdapter mouseListeners() {
        return new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                boolean found = false;
                for (int i = 0; i < playerAPits.length; i++) {
                    if (!found && playerAPits[i].Clicked(e.getPoint())) {
                        //System.out.println("CLICK ON A PIT OF PLAYER A, PIT# " + i);
                        //dataModel.distributeStonesInPitA(i);
                        dataModel.distributeStones("A", i);
                        break;
                    }
                    if (!found && playerBPits[i].Clicked(e.getPoint())) {
                        //System.out.println("CLICK ON A PIT OF PLAYER B, PIT# " + i);
                        //dataModel.distributeStonesInPitB(i);
                        dataModel.distributeStones("B", i);
                        break;
                    }
                }
                
            }
        };
    }

    /**
     * Paint component method for drawing the board and it's pits and Mancalas
     */
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        RoundRectangle2D board = new RoundRectangle2D.Double(xPos, yPos, BOARD_WIDTH, BOARD_HEIGHT, 10, 10);
        g2.draw(board);
        for(int pit = 0; pit < playerAPits.length; pit++){
            playerAPits[pit].draw(g2);
            playerBPits[pit].draw(g2);
        }
        playerAMancala.draw(g2);
        playerBMancala.draw(g2);
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        // TODO Auto-generated method stub
        this.repaint();
        
    }
    
    public void updateStones() {
        // update stone list of all pits of player A and B, as well as both mancala
        for (int i = 0; i < playerAPits.length; i++) {
            playerAPits[i].populateStones(dataModel.getStonesInPitsA()[i]);
            playerBPits[i].populateStones(dataModel.getStonesInPitsB()[i]);
        }
        
        playerAMancala.populateStones(dataModel.getStonesInMancalaA());
        playerBMancala.populateStones(dataModel.getStonesInMancalaB());
    }
}