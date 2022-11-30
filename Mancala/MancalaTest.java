import javax.swing.*;

/**
 * This class initializes the Mancala game
 * @author Quang Le, Brian Tran, Moe Pyae Sone
 */
public class MancalaTest {
    private static final int FRAME_WIDTH = 800;
    private static final int FRAME_HEIGHT = 800;
    private static JFrame frame;
    private static MainPanel mainPanel;
    private static DataModel dataModel;
    private static ChooseStylePanel chooseStylePanel;
    
	public static void main(String[] args) {
	    // Main frame
		frame = new JFrame();
		frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
		
		// Data model to hold all data for game
        dataModel = new DataModel(FRAME_WIDTH, FRAME_HEIGHT);
        
        // Main panel to contain other smaller panels
        mainPanel = new MainPanel(dataModel);
        
        // Attach main panel to data model
        dataModel.attach(mainPanel);
        
        frame.add(mainPanel);
        frame.setTitle("MANCALA GAME");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
	}
}
