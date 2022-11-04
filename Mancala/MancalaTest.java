import java.util.*;
import java.awt.*;
import javax.swing.*;

public class MancalaTest {
	
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setSize(400, 400);
//		frame.setPreferredSize(new Dimension(400, 400));
		MancalaBoard newGame = new MancalaBoard(frame.getWidth(), frame.getHeight(), 3);
		frame.setLayout(new BorderLayout());
		frame.add(newGame, BorderLayout.CENTER);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}
