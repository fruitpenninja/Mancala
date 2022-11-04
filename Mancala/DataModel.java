import java.util.*;

public class DataModel {
    private int frameWidth;
    private int frameHeight;
    private int panel1Width;
    private int panel1Height;
    private int numStones;
    
    private Pit[] playerAPits;
    private Pit[] playerBPits;
    
    
    public DataModel(int frame_width, int frame_height, int numStones) {
        this.frameWidth = frame_width;      // 800
        this.frameHeight = frame_height;    // 800
        
        this.panel1Width = frame_width;             // 800
        this.panel1Height = frame_height / 2;       // 400
        
        this.numStones = numStones;
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
    
    public int getNumStones() {
        return numStones;
    }
    
    public void setNumStones(int numStones) {
        this.numStones = numStones;
    }
}
