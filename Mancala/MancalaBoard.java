import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;

public class MancalaBoard extends JPanel{
    private Pits[] Player1Pits;
    private Pits[] Player2Pits;
    private Mancala Player1Mancala;
    private Mancala Player2Mancala;
    final double BOARD_LENGTH = 200;
    final double BOARD_WIDTH = 100;
    private double xPos;
    private double yPos;
    public MancalaBoard(double windowHeight, double windowLength){
        xPos = (windowLength - BOARD_LENGTH)/2;
        yPos = (windowHeight -BOARD_WIDTH)/2;
        Player1Pits = new Pits[6];
        Player2Pits = new Pits[6];

        double startX = BOARD_LENGTH * 0.4 / 2;
        for(int i = 0; i < Player1Pits.length; i++){
            Player1Pits[i] = new Pits(0, xPos + startX + BOARD_LENGTH * i * 0.1, yPos + BOARD_WIDTH * 0.72, BOARD_LENGTH * 0.08);
        }

        for(int i = 0; i < Player2Pits.length; i++){
            Player2Pits[i] = new Pits(0, xPos + startX + BOARD_LENGTH * i * 0.1, yPos + BOARD_WIDTH * 0.1, BOARD_LENGTH* 0.08);
        }

        Player1Mancala = new Mancala(xPos + BOARD_LENGTH * 0.05, yPos + BOARD_WIDTH * 0.1, BOARD_LENGTH * 0.1, BOARD_WIDTH * 0.8);
        Player2Mancala = new Mancala(xPos + BOARD_LENGTH * 0.85, yPos + BOARD_WIDTH * 0.1, BOARD_LENGTH * 0.1, BOARD_WIDTH * 0.8);
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        RoundRectangle2D board = new RoundRectangle2D.Double(xPos, yPos, BOARD_LENGTH, BOARD_WIDTH, 10, 10);
        g2.draw(board);
        for(int pit = 0; pit < Player1Pits.length; pit++){
            Player1Pits[pit].draw(g2);
            Player2Pits[pit].draw(g2);
        }
        Player1Mancala.draw(g2);
        Player2Mancala.draw(g2);
    }
}