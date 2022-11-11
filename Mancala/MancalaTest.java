import javax.swing.*;

public class MancalaTest {
    private static final int FRAME_WIDTH = 800;
    private static final int FRAME_HEIGHT = 800;
    private static JFrame frame;
    private static MainPanel mainPanel;
    private static DataModel dataModel;
    private static ChooseStylePanel chooseStylePanel;
    
	public static void main(String[] args) {
	    
		frame = new JFrame();
		frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
		
		// Create a data model and pass in infor needed
        dataModel = new DataModel(FRAME_WIDTH, FRAME_HEIGHT);
        
        mainPanel = new MainPanel(dataModel);
        
        dataModel.attach(mainPanel);
        
        frame.add(mainPanel);
        frame.setTitle("MANCALA GAME");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
		
	}
}
