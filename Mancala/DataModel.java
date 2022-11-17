import java.util.*;

import javax.swing.event.ChangeListener;

public class DataModel {
    private int frameWidth;
    private int frameHeight;
    private int panel1Width;
    private int panel1Height;
    
//    private int numStones;
    
    private Pit[] playerAPits;
    private Pit[] playerBPits;
    
    private int[] stonesInPitsA;
    private int[] stonesInPitsB;
    
    private Pit playerAMancala;
    private Pit playerBMancala;
    
    private ArrayList<ChangeListener> listeners;
    
    private int stage;
    private int styleBoard;
    
    
    public DataModel(int frame_width, int frame_height) {
        this.frameWidth = frame_width;      // 800
        this.frameHeight = frame_height;    // 800
        
        this.panel1Width = frame_width;             // 800
        this.panel1Height = frame_height / 2;       // 400
        
        playerAPits = new Pit[6];
        playerBPits = new Pit[6];
        
        stonesInPitsA = new int[6];
        stonesInPitsB = new int[6];
        
        listeners = new ArrayList<>();
        stage = 1;
        styleBoard = 0;
    }
    
    public void initializeStonesInAllPits(int num) {
        for (int i =0; i < stonesInPitsA.length; i++) {
            stonesInPitsA[i] = num;
        }
        for (int i =0; i < stonesInPitsB.length; i++) {
            stonesInPitsB[i] = num;
        }
    }
    
    public int[] getStonesInPitsA() {
        return stonesInPitsA;
    }
    
    public int[] getStonesInPitsB() {
        return stonesInPitsB;
    }
    
    
    public void setStyleBoard(int styleBoard) {
        this.styleBoard = styleBoard;
        changeStage(2);
    }
    public int getStyleBoard(){
        return styleBoard;
    }
    public void attach(ChangeListener l) {
        listeners.add(l);
    }
    public int getStage() {
        return stage;
    }
    
    public void changeStage(int stage) {
        this.stage = stage;
        for (ChangeListener l : listeners) {
            l.stateChanged(null);
        }
        
    }
    
    public int getPanel1Width() {
        return panel1Width;
    }
    
    public int getPanel1Height() {
        return panel1Height;
    }
    
    public int getFrameWidth() {
        return frameWidth;
    }
    
    public int getFrameHeight() {
        return frameHeight;
    }
    
//    public int getNumStones() {
//        return numStones;
//    }
//    
//    public void setNumStones(int numStones) {
//        this.numStones = numStones;
//    }
    
    public void setPlayerAPit(int index, Pit p) {
        playerAPits[index] = p;
    }
    
    public void setPlayerBPit(int index, Pit p) {
        playerBPits[index] = p;
    }
    
    public void setPlayerAMancala(Pit m) {
        playerAMancala = m;
    }
    
    public void setPlayerBMancala(Pit m) {
        playerBMancala = m;
    }
}
