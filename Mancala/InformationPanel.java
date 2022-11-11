import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class InformationPanel extends JPanel{

//    private final int PANEL_WIDTH;
//    private final int PANEL_HEIGHT;
      private DataModel dataModel;
    
    public InformationPanel(final DataModel dataModel) {
//        PANEL_WIDTH = dataModel.getFrameWidth();                
//        PANEL_HEIGHT = dataModel.getFrameHeight() / 2 - 20;
//        
//        this.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        
        this.dataModel = dataModel;
        
        JButton resetButton = new JButton("RESET");          // This button potentially be used when game's over, announce winner, reset game
        resetButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dataModel.changeStage(1);
            }
        });
        
        // more components added here
        
        
        
        this.add(resetButton); 
    }
    
    
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
    }
}
