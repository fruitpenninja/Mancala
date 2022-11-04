import java.util.*;
import java.awt.*;
import javax.swing.*;

public class MancalaTest {
    private static final int FRAME_WIDTH = 800;
    private static final int FRAME_HEIGHT = 800;
    
	
	public static void main(String[] args) {
	    
		JFrame frame = new JFrame();
		frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
		
		int numStones = 3;    // number of stones in each pit
		
		// Create a data model and pass in other panel that needs to access data later
		DataModel dataModel = new DataModel(FRAME_WIDTH, FRAME_HEIGHT, numStones);
		
		MancalaBoard newGame = new MancalaBoard(dataModel);
		frame.setLayout(new BorderLayout());
		frame.add(newGame, BorderLayout.NORTH);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}
