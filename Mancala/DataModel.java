import java.util.*;

import javax.swing.event.ChangeListener;

public class DataModel {
    private int frameWidth;
    private int frameHeight;
    private int panel1Width;
    private int panel1Height;
    
    private int[] prevStonesInPitsA;
    private int[] prevStonesInPitsB;
    private int[] stonesInPitsA;
    private int[] stonesInPitsB;
    
    private int prevStonesInMancalaA;
    private int prevStonesInMancalaB;
    private int stonesInMancalaA;
    private int stonesInMancalaB;
    
    private ArrayList<ChangeListener> listeners;
    
    private int stage;
    private int styleBoard;

    private boolean canUndo;
    private boolean gameOver;
    private int remainingUndoA;
    private int remainingUndoB;
    private String activePlayer;
    private String gameMessage;
    
    private boolean switchPlayers;
    
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

    public void initializeGame(){
        double player = Math.random();
        if(player > 0.5){
            activePlayer = "B";
            gameMessage = "Player B's turn";
        }
        else{
            activePlayer = "A";
            gameMessage = "Player A's turn";
        }
        canUndo = false;
        remainingUndoA = 3;
        remainingUndoB = 3;
        gameOver = false; 
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
    
    public void distributeStones(String player, int location){
        int stonesToDistribute = 0;
        int currentLocation = 0;
        int lastStoneDropLocation = 0;
        
        // ONLY DISTRIBUTE IF THE CORRECT PLAYER IS CHOOSING HIS/HER PIT
        if(activePlayer.equalsIgnoreCase(player)){
            //Store previous state of board
            prevStonesInMancalaA = stonesInMancalaA;
            prevStonesInMancalaB = stonesInMancalaB;
            prevStonesInPitsA = stonesInPitsA.clone();
            prevStonesInPitsB = stonesInPitsB.clone();

            switchPlayers = true;
            
            // DETERMINE THE STONES OF THE CORRECT PIT BASED ON THE ACTIVE PLAYER
            if(player.equalsIgnoreCase("A")){
                stonesToDistribute = stonesInPitsA[location];
                currentLocation = location + 1;
                stonesInPitsA[location] = 0;
            }
            
            else{   //If the player is B
                stonesToDistribute = stonesInPitsB[location];
                if(location == 0){
                    currentLocation = 13;
                }
                else{
                    currentLocation = 12 - location + 1;
                }
                stonesInPitsB[location] = 0;
            }
            
            // DISTRIBUTE WHILE THERE ARE STONES ON HAND
            while(stonesToDistribute > 0){
                
              // Keep track of pit or mancala the last stone is dropped into
                if(stonesToDistribute == 1){        
                    lastStoneDropLocation = currentLocation;
                }
                
              // Distribute each stone to correct pit/mancala
                if(currentLocation < 6){
                    //currentLocations 0-5, corresponds to PitsA 0 - 5
                    stonesInPitsA[currentLocation]++;
                    
                }
                else if(currentLocation == 6){
                    //currentLocation = 6 corresponds to Mancala A
                    stonesInMancalaA++;
                }
                else if(currentLocation < 13){
                    //currentLocations 7-12, corresponds to PitsB 5 - 0 since stone distribution must be in reverse order
                    stonesInPitsB[12 - currentLocation]++;
                }
                else{
                    //currentLocation = 13 corresponds to Mancala B
                    stonesInMancalaB++;
                }
                
                // REDUCE STONES ON HAND AFTER DISTRIBUTE ONE ON A PIT/MANCALA
                stonesToDistribute--;

                // Determine next (currentLocation) pit to drop next stone into
                if(currentLocation == 13){  // go back (start again) from first pit of A
                    currentLocation = 0;
                }
                else{
                    currentLocation++;
                }
            }
            
            // CHECK IF NEED TO PUT ALL STONES TO PLAYER'S MANCALA
            //If last pit to get stone belongs to player A
            if (activePlayer.equals("A")) {
                if(lastStoneDropLocation >= 0 && lastStoneDropLocation < 6){
                    //If that pit was empty, player A gets the last stone and the stones in opposite pit of Player B
                    if(stonesInPitsA[lastStoneDropLocation] == 1){
                        stonesInMancalaA += stonesInPitsA[lastStoneDropLocation];
                        stonesInMancalaA += stonesInPitsB[lastStoneDropLocation];
                        stonesInPitsA[lastStoneDropLocation] = 0;
                        stonesInPitsB[lastStoneDropLocation] = 0;
                    }
                }
                //If last stone distributed was in player A's mancala
                else if(lastStoneDropLocation == 6){
                    switchPlayers = false;
                    gameMessage = "Go again Player A";
                }
                remainingUndoB = 3;         // reset number of undo times for the opponent
            }
            // Last pit to get stone belongs to player B
            else if (activePlayer.equals("B")) {
                if(lastStoneDropLocation > 6 && lastStoneDropLocation < 13){
                    //If that pit was empty, player B gets the last stone and the stones in opposite pit of Player A
                    if(stonesInPitsB[12 - lastStoneDropLocation] == 1){
                        stonesInMancalaB += stonesInPitsA[12 - lastStoneDropLocation];
                        stonesInMancalaB += stonesInPitsB[12 - lastStoneDropLocation];
                        stonesInPitsA[12 - lastStoneDropLocation] = 0;
                        stonesInPitsB[12 - lastStoneDropLocation] = 0;
                    }
                }
                //If last pit to get stone is player B's mancala
                else if(lastStoneDropLocation == 13){
                    switchPlayers = false;
                    gameMessage = "Go again Player B";
                }
                remainingUndoA = 3;         // reset number of undo times for the opponent
            }
            
            // ACTIVATE undo feature here to avoid double undo in a row
            canUndo = true;
            
            // SWITCH PLAYER IF NEEDED
            if(switchPlayers){
                if(activePlayer.equals("A")){
                    activePlayer = "B";
                    gameMessage = "Player B's turn";
                }
                else{
                    activePlayer = "A";
                    gameMessage = "Player A's turn";
                }
            }
                
          // CHECK IF GAME IS OVER
          //Check if all of Player A's pits are empty
            if(check_A_All_Zero()){
                for(int i = 0; i < stonesInPitsB.length; i++){
                    stonesInMancalaB += stonesInPitsB[i];
                    stonesInPitsB[i] = 0;
                    gameOver = true;
                    determineWinner();
                }
            }
            //Check if all of Player B's pits are empty
            else if(check_B_All_Zero()){
                for(int i = 0; i < stonesInPitsA.length; i++){
                    stonesInMancalaA += stonesInPitsA[i];
                    stonesInPitsA[i] = 0;
                    gameOver = true;
                    determineWinner();
                }
            }
            
            // REFLECT CHANGE
            for (ChangeListener l : listeners) {
                l.stateChanged(null);
            }
        }
    }

    public void undoMove(){
        if(canUndo){
            // CHANGE active player if last stone wasn't in player's mancala. Reduce remaining undos of player appropriately
            if (activePlayer.equalsIgnoreCase("A")) {
                
                if (switchPlayers && getRemainingUndoB() > 0) {     // last stone wasn't in mancala, no double turn case
                    reverseStonesInPitsAndMancalas();
                
                    activePlayer = "B";
                    gameMessage = "Player B's turn";
                    
                    // reduce remainingUndo of B after choosing to reverse
                    remainingUndoB--;
                }
                else if (!switchPlayers && getRemainingUndoA() > 0) {   // last stone was in mancala, player got double turn
                    reverseStonesInPitsAndMancalas();
                    // reduce remainingUndo of A after choosing to reverse
                    remainingUndoA--;
                }
            }
            else if (activePlayer.equalsIgnoreCase("B")) {
                if (switchPlayers && getRemainingUndoA() > 0) {     // last stone wasn't in mancala, no double turn case
                    reverseStonesInPitsAndMancalas();
                    
                    activePlayer = "A";
                    gameMessage = "Player A's turn";
                    
                    // reduce remainingUndo of B after choosing to reverse
                    remainingUndoA--;
                }
                else if (!switchPlayers && getRemainingUndoB() > 0) {   // last stone was in mancala, player got double turn
                    reverseStonesInPitsAndMancalas();
                    // reduce remainingUndo of A after choosing to reverse
                    remainingUndoB--;
                }
            }
            
            // Reflect change
            for (ChangeListener l : listeners) {
                l.stateChanged(null);
            }
        }        
    }
    
    public void reverseStonesInPitsAndMancalas() {
     // REVERSE STONES IN ALL PITS AND MANCALA
        stonesInMancalaA = prevStonesInMancalaA;
        stonesInMancalaB = prevStonesInMancalaB;
        stonesInPitsA = prevStonesInPitsA.clone();
        stonesInPitsB = prevStonesInPitsB.clone();
        canUndo = false;
    }
    
    public int getRemainingUndoA() {
        return remainingUndoA;
    }
    
    public int getRemainingUndoB() {
        return remainingUndoB;
    }
    
    
    public boolean isGameOver(){
        return gameOver;
    }

    public boolean check_A_All_Zero(){
        for(int i = 0; i < stonesInPitsA.length; i++){
            if(stonesInPitsA[i] != 0){
                return false;
            }
        }
        return true;
    }

    public boolean check_B_All_Zero(){
        for(int i = 0; i < stonesInPitsB.length; i++){
            if(stonesInPitsB[i] != 0){
                return false;
            }
        }
        return true;
    }

    public void determineWinner(){
        if(stonesInMancalaA > stonesInMancalaB){
            gameMessage = "Player A wins!";
        }
        else if(stonesInMancalaB > stonesInMancalaA){
            gameMessage = "Player B wins!";
        }
        else{
            gameMessage = "It's a tie!";
        }
    }

    public String getGameMessage(){
        return gameMessage;
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
