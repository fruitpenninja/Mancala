import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class ChooseStylePanel extends JPanel{
    
    private final DataModel dataModel;
    
    public ChooseStylePanel(final DataModel dataModel) {
        
        this.dataModel = dataModel;
        
        JLabel label1 = new JLabel("Enter the number of stones for each pit");
        JLabel label2 = new JLabel("min:  - max:  ");
        JLabel label3 = new JLabel("and choose a style for board game");
        
        final JTextField textField = new JTextField();
        textField.setSize(50, 20);
        JButton button1 = new JButton("Style 1");
        JButton button2 = new JButton("Style 2");
        
        
        button1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String content = textField.getText().strip();
                try {
                    int num = Integer.parseInt(content);
//                    dataModel.setNumStones(num);
                    dataModel.initializeStonesInAllPits(num);
                    dataModel.setStyleBoard(1);
                } catch (Exception error){
                    System.out.println("");     // Do nothing until user enter a number
                } 
                
                
            }
        });
        
        button2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String content = textField.getText().strip();
                try {
                    int num = Integer.parseInt(content);
//                    dataModel.setNumStones(num);
                    dataModel.initializeStonesInAllPits(num);
                    dataModel.setStyleBoard(1);
                } catch (Exception error){
                    System.out.println("");     // Do nothing until user enter a number
                } 
                
                
            }
        });
        
        this.add(label1);
        this.add(label2);
        this.add(label3);
        this.add(textField);
        this.add(button1);
        this.add(button2);
        
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    }
    
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
    }

    
}
