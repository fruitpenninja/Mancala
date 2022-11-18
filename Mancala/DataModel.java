import java.util.*;

import javax.swing.event.ChangeListener;

public class DataModel {
    private int frameWidth;
    private int frameHeight;
    private int panel1Width;
    private int panel1Height;
    
    private int[] stonesInPitsA;
    private int[] stonesInPitsB;
    
    private int stonesInMancalaA;
    private int stonesInMancalaB;
    
    private ArrayList<ChangeListener> listeners;
    
    private int stage;
    private int styleBoard;
    
    public DataModel(int frame_width, int frame_height) {
        this.frameWidth = frame_width;      // 800
        this.frameHeight = frame_height;    // 800
        
        this.panel1Width = frame_width;             // 800
        this.panel1Height = frame_height / 2;       // 400
        
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
        
        stonesInMancalaA = 0;
        stonesInMancalaB = 0;
    }
    
    public void distributeStonesInPitA(int index) {
        int numStones = stonesInPitsA[index];
        stonesInPitsA[index] = 0;
        
        // PUT ALL THIS STONES TO THE NEXT PIT
        int nextPit = index + 1;
        System.out.println("This pit A: " + index + " next pit A: " + nextPit);
        if (nextPit < stonesInPitsA.length) {
            System.out.println("Move from pit A" + index + " to pit A" + nextPit);
            stonesInPitsA[nextPit] += numStones;
        }
        else {
            stonesInMancalaA += numStones;
            System.out.println("Add to mancala A, holding: " + stonesInMancalaA);
        }
        
        // reflect change
        for (ChangeListener l : listeners) {
            l.stateChanged(null);
        }
        
    }
    
    public void distributeStonesInPitB(int index) {
        int numStones = stonesInPitsB[index];
        stonesInPitsB[index] = 0;
        
        // PUT ALL THIS STONES TO THE NEXT PIT
        int nextPit = index - 1;
        System.out.println("This pit B: " + index + " next pit B: " + nextPit);
        if (nextPit >= 0) {
            System.out.println("Move from pit B" + index + " to pit B" + nextPit);
            stonesInPitsB[nextPit] += numStones;
        }
        else {
            stonesInMancalaB += numStones;
            System.out.println("Add to mancala B, holding: " + stonesInMancalaB);
        }
        
        // reflect change
        for (ChangeListener l : listeners) {
            l.stateChanged(null);
        }
        
    }
    
    
    public int[] getStonesInPitsA() {
        return stonesInPitsA;
    }
    
    public int[] getStonesInPitsB() {
        return stonesInPitsB;
    }
    
    public int getStonesInMancalaA() {
        return stonesInMancalaA;
    }
    public int getStonesInMancalaB() {
        return stonesInMancalaB;
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
}
