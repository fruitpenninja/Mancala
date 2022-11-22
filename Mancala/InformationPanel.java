import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class InformationPanel extends JPanel{

//    private final int PANEL_WIDTH;
//    private final int PANEL_HEIGHT;
      private DataModel dataModel;
      private JLabel undoCount;
      private JLabel gameState;
      private JPanel undoPanel;
      private JButton resetButton;
    
    public InformationPanel(final DataModel dataModel) {
//        PANEL_WIDTH = dataModel.getFrameWidth();                
//        PANEL_HEIGHT = dataModel.getFrameHeight() / 2 - 20;
//        
//        this.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        
        this.dataModel = dataModel;
        
        resetButton = new JButton("RESET");          // This button potentially be used when game's over, announce winner, reset game
        resetButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dataModel.changeStage(1);
            }
        });
        
        gameState = new JLabel(dataModel.getGameMessage());
        undoPanel = new JPanel();

        undoCount = new JLabel("Remaining undos: " + dataModel.undosLeft());

        JButton undoButton = new JButton("UNDO");
        undoButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dataModel.undoMove();
            }
        });
        undoPanel.add(undoButton);
        undoPanel.add(undoCount);
        
        // more components added here
        
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        
        //this.add(resetButton);
        this.add(gameState);
        this.add(undoPanel); 
    }
    
    public void update(){
        if(dataModel.isGameOver()){
            remove(undoPanel);
            add(resetButton);
            gameState.setText(dataModel.getGameMessage() + " Press reset to start a new game.");
        }
        else{
            undoCount.setText("Remaining undos: " + dataModel.undosLeft());
            gameState.setText(dataModel.getGameMessage());
        }
    }
    
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
    }
}
