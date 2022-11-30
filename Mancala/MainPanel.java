import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;

/**
 * This class represents the main panel to display everything for the game
 * @author Quang Le, Brian Tran, Moe Pyae Sone
 *
 */
public class MainPanel extends JPanel implements ChangeListener{
    private final int PANEL_WIDTH;      
    private final int PANEL_HEIGHT;   
    private DataModel dataModel;
    private ChooseStylePanel chooseStylePanel;
    private MancalaBoardPanel mancalaBoardPanel;
    private InformationPanel informationPanel;
    private int stage;
    
    /**
     * Constructor 
     * @param dataModel - a data model to reference when needed
     */
    public MainPanel(DataModel dataModel) {
        PANEL_WIDTH = dataModel.getFrameWidth();
        PANEL_HEIGHT = dataModel.getFrameHeight();
        
        this.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        
        this.dataModel = dataModel;
        
        // start with stage 1 - asking for a style of board game
        stage = dataModel.getStage();
        initializeStage1();
    }
    
    
    /**
     * Get notified when change happens in the model
     */
    public void stateChanged(ChangeEvent e) {
        // figure out what type of change first
        // stage of game changed
        if (dataModel.getStage() != this.stage) {
            this.stage = dataModel.getStage();
            if (this.stage == 1) {
                initializeStage1();
            }
            else if (this.stage == 2) {
                System.out.println("Change to stage 2 (playing game)");
                initializeStage2();
            }
        }
        // board game changing while two players playing game 
        else {
            //UPDATE STONE BEFORE VALIDATE AND REPAINT
            mancalaBoardPanel.updateStones();
            informationPanel.update();
            this.validate();        
            repaint();
        }
    }
    
    /**
     * Initialize stage 1 -  asking for a style
     */
    public void initializeStage1() {
        // remove all current components on this main panel
        this.removeAll();
        
       chooseStylePanel = new ChooseStylePanel(dataModel);
       this.add(chooseStylePanel, BorderLayout.NORTH);
       
       this.validate();        
       repaint();
    }
    
    /**
     * Initialize stage 2 - board game appears, starts the game
     */
    public  void initializeStage2() {
        // remove all current components before proceeding this stage
        this.removeAll();
        
        mancalaBoardPanel = new MancalaBoardPanel(dataModel);
        informationPanel = new InformationPanel(dataModel);
        
        this.add(mancalaBoardPanel, BorderLayout.NORTH);
        this.add(informationPanel, BorderLayout.CENTER);
        
        this.validate();
        repaint();
        
    }
}
