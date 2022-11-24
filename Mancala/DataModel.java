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
    private int remainingUndo;
    private String activePlayer;
    private String gameMessage;
    
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
        remainingUndo = 3;
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
        int lastStoneDrop = 0;
        if(activePlayer.equals(player)){
            //Store previous state of board
            prevStonesInMancalaA = stonesInMancalaA;
            prevStonesInMancalaB = stonesInMancalaB;
            prevStonesInPitsA = stonesInPitsA.clone();
            prevStonesInPitsB = stonesInPitsB.clone();

            boolean switchPlayers = true;

            if(player.equals("A")){
                stonesToDistribute = stonesInPitsA[location];
                currentLocation = location + 1;
                stonesInPitsA[location] = 0;
            }
            else{
                //If the player is B, th
                stonesToDistribute = stonesInPitsB[location];
                if(location == 0){
                    currentLocation = 13;
                }
                else{
                    currentLocation = 12 - location + 1;
                }
                stonesInPitsB[location] = 0;
            }

            while(stonesToDistribute > 0){
                //Keep track of pit or mancala the last stone is dropped into
                if(stonesToDistribute == 1){
                    lastStoneDrop = currentLocation;
                }

                if(currentLocation < 6){
                    //currentLocations 0-5, corresponds to PitsA 0 - 5
                    stonesInPitsA[currentLocation]++;
                    stonesToDistribute--;
                }
                else if(currentLocation == 6){
                    //currentLocation = 6 corresponds to Mancala A
                    if(activePlayer.equals("A")){
                        stonesInMancalaA++;
                        stonesToDistribute--;
                    }
                }
                else if(currentLocation < 13){
                    //currentLocations 7-12, corresponds to PitsB 5 - 0 since stone distribution must be in reverse order
                    stonesInPitsB[12 - currentLocation]++;
                    stonesToDistribute--;
                }
                else{
                    //currentLocation = 13 corresponds to Mancala B
                    if(activePlayer.equals("B")){
                        stonesInMancalaB++;
                        stonesToDistribute--;
                    }
                }

                //Determine next pit to drop next stone into
                if(currentLocation == 13){
                    currentLocation = 0;
                }
                else{
                    currentLocation++;
                }
            }
            
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
            //Carry on with the game otherwise
            else{
                //If last pit to get stone belongs to player A
                if(lastStoneDrop >= 0 && lastStoneDrop < 6){
                    //If that pit was empty, player A gets the last stone and the stones in opposite pit of Player B
                    if(stonesInPitsA[lastStoneDrop] == 1 && activePlayer.equals("A")){
                        stonesInMancalaA += stonesInPitsA[lastStoneDrop];
                        stonesInMancalaA += stonesInPitsB[lastStoneDrop];
                        stonesInPitsA[lastStoneDrop] = 0;
                        stonesInPitsB[lastStoneDrop] = 0;
                    }
                }
                //If last pit to get stone is player A's mancala
                else if(lastStoneDrop == 6){
                    switchPlayers = false;
                    gameMessage = "Go again Player A";
                }
                //If last pit to get stone belongs to player B
                else if(lastStoneDrop > 6 && lastStoneDrop < 13){
                    //If that pit was empty, player B gets the last stone and the stones in opposite pit of Player A
                    if(stonesInPitsA[12 - lastStoneDrop] == 1  && activePlayer.equals("B")){
                        stonesInMancalaB += stonesInPitsA[12 - lastStoneDrop];
                        stonesInMancalaB += stonesInPitsB[12 - lastStoneDrop];
                        stonesInPitsA[12 - lastStoneDrop] = 0;
                        stonesInPitsB[12 - lastStoneDrop] = 0;
                    }
                }
                //If last pit to get stone is player B's mancala
                else{
                    switchPlayers = false;
                    gameMessage = "Go again Player B";
                }
                canUndo = true;

                if(switchPlayers){
                    if(activePlayer.equals("A")){
                        activePlayer = "B";
                        gameMessage = "Player B's turn";
                        remainingUndo = 3;
                    }
                    else{
                        activePlayer = "A";
                        gameMessage = "Player A's turn";
                        remainingUndo = 3;
                    }
                }
            }
            // reflect change
            for (ChangeListener l : listeners) {
                l.stateChanged(null);
            }
        }
    }

    public void undoMove(){
        if(canUndo && remainingUndo > 0){
            stonesInMancalaA = prevStonesInMancalaA;
            stonesInMancalaB = prevStonesInMancalaB;
            stonesInPitsA = prevStonesInPitsA.clone();
            stonesInPitsB = prevStonesInPitsB.clone();
            canUndo = false;
            remainingUndo--;

            for (ChangeListener l : listeners) {
                l.stateChanged(null);
            }
        }        
    }

    public int undosLeft(){
        return remainingUndo;
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
