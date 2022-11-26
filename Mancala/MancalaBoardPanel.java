import java.awt.*;
import java.awt.Font;
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
        yPos = 24;
        
        playerAPits = new GeneralShape[6];
        playerBPits = new GeneralShape[6];
        
        
      //Initialize the pits for each player
        double startX = BOARD_WIDTH * 0.4 / 2;
        for(int i = 0; i < playerAPits.length; i++){
            if(dataModel.getStyleBoard() == 1){
                playerAPits[i] = new PitOrMancalaStyle1("pit", dataModel, dataModel.getStonesInPitsA()[i], xPos + startX + BOARD_WIDTH * i * 0.1, yPos + BOARD_HEIGHT * 0.72, BOARD_WIDTH * 0.08, BOARD_WIDTH * 0.08, BOARD_WIDTH * 0.08);
                playerBPits[i] = new PitOrMancalaStyle1("pit", dataModel, dataModel.getStonesInPitsB()[i], xPos + startX + BOARD_WIDTH * i * 0.1, yPos + BOARD_HEIGHT * 0.1, BOARD_WIDTH * 0.08, BOARD_WIDTH * 0.08, BOARD_WIDTH * 0.08);
            }
            else if (dataModel.getStyleBoard() == 2){
                playerAPits[i] = new PitOrMancalaStyle2("pit", dataModel, dataModel.getStonesInPitsA()[i], xPos + startX + BOARD_WIDTH * i * 0.1, yPos + BOARD_HEIGHT * 0.72, BOARD_WIDTH * 0.08, BOARD_WIDTH * 0.08, BOARD_WIDTH * 0.08);
                playerBPits[i] = new PitOrMancalaStyle2("pit", dataModel, dataModel.getStonesInPitsB()[i], xPos + startX + BOARD_WIDTH * i * 0.1, yPos + BOARD_HEIGHT * 0.1, BOARD_WIDTH * 0.08, BOARD_WIDTH * 0.08, BOARD_WIDTH * 0.08);
            }
        }

        //Initialize the Mancalas for each player
        if(dataModel.getStyleBoard() == 1){
            playerAMancala = new PitOrMancalaStyle1("mancala", dataModel, dataModel.getStonesInMancalaA(), xPos + BOARD_WIDTH * 0.85, yPos + BOARD_HEIGHT * 0.1, BOARD_WIDTH * 0.1, BOARD_HEIGHT * 0.8, BOARD_WIDTH* 0.08);
            playerBMancala = new PitOrMancalaStyle1("mancala", dataModel, dataModel.getStonesInMancalaB(), xPos + BOARD_WIDTH * 0.05, yPos + BOARD_HEIGHT * 0.1, BOARD_WIDTH * 0.1, BOARD_HEIGHT * 0.8, BOARD_WIDTH* 0.08);        }
        else if(dataModel.getStyleBoard() == 2){
            playerAMancala = new PitOrMancalaStyle2("mancala", dataModel, dataModel.getStonesInMancalaA(), xPos + BOARD_WIDTH * 0.85, yPos + BOARD_HEIGHT * 0.1, BOARD_WIDTH * 0.1, BOARD_HEIGHT * 0.8, BOARD_WIDTH* 0.08);
            playerBMancala = new PitOrMancalaStyle2("mancala", dataModel, dataModel.getStonesInMancalaB(), xPos + BOARD_WIDTH * 0.05, yPos + BOARD_HEIGHT * 0.1, BOARD_WIDTH * 0.1, BOARD_HEIGHT * 0.8, BOARD_WIDTH* 0.08);
        }
        dataModel.initializeGame();
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
                    if (!found && playerAPits[i].Clicked(e.getPoint()) && dataModel.getStonesInPitsA()[i] > 0) {
                        //System.out.println("CLICK ON A PIT OF PLAYER A, PIT# " + i);
                        // only distribute and switch player if that pit has > 0 stones
                        
                        dataModel.distributeStones("A", i);
                        break;
                    }
                    if (!found && playerBPits[i].Clicked(e.getPoint()) && dataModel.getStonesInPitsB()[i] > 0) {
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

        Font currentFont = g2.getFont();
        Font newFont = currentFont.deriveFont(currentFont.getSize() * 1.5F);
        g2.setFont(newFont);
        FontMetrics fm = g2.getFontMetrics();
        
        g2.drawString("Player B", (float)(PANEL_WIDTH - fm.stringWidth("Player B"))/2, fm.getAscent());
        g2.drawString("Player A", (float)(PANEL_WIDTH - fm.stringWidth("Player B"))/2, (float)(yPos + BOARD_HEIGHT + fm.getAscent()));

        String mB= "MANCALA B";
        String mA = "MANCALA A";
        for(int i = 0; i < mB.length(); i++){
            g2.drawString(mB.substring(i, i+1), (float)(xPos - fm.stringWidth("M")), (float)((yPos + (BOARD_HEIGHT - mB.length() * (fm.getAscent() + fm.getDescent()))/2 + (i + 1)  * (fm.getAscent() + fm.getDescent()))));
            g2.drawString(mA.substring(i, i+1), (float)(xPos + 1 + BOARD_WIDTH), (float)((yPos + (BOARD_HEIGHT - mB.length() * (fm.getAscent() + fm.getDescent()))/2 + (i + 1)  * (fm.getAscent() + fm.getDescent()))));
        }
        String [] PitALabels = {"A1", "A2", "A3", "A4", "A5", "A6"};
        String [] PitBLabels = {"B6", "B5", "B4", "B3", "B2", "B1"};
        for(int col = 0; col < 6; col++){
            g2.drawString(PitALabels[col], (float)(xPos + BOARD_WIDTH * 0.4 / 2 + BOARD_WIDTH * col * 0.1 + (BOARD_WIDTH * 0.08 - fm.stringWidth(PitALabels[col])) / 2), (float)(yPos + BOARD_HEIGHT * 0.72 + fm.getAscent() + BOARD_WIDTH * 0.08));
            g2.drawString(PitBLabels[col], (float)(xPos + BOARD_WIDTH * 0.4 / 2 + BOARD_WIDTH * col * 0.1 + (BOARD_WIDTH * 0.08 - fm.stringWidth(PitBLabels[col])) / 2), (float)(yPos + BOARD_HEIGHT * 0.1 - 3));
        }
        
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
    
   /* */ public void updateStones() {
        // update stone list of all pits of player A and B, as well as both mancala
        for (int i = 0; i < playerAPits.length; i++) {
            playerAPits[i].populateStones(dataModel.getStonesInPitsA()[i]);
            playerBPits[i].populateStones(dataModel.getStonesInPitsB()[i]);
        }
        
        playerAMancala.populateStones(dataModel.getStonesInMancalaA());
        playerBMancala.populateStones(dataModel.getStonesInMancalaB());
    }
}