package domain;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Human implements Player {
    private String name;
    private Color color;
    private ArrayList<Token> tokens = new ArrayList<>();
    private Token[][] tokenMatrix;
    private ArrayList<String> tokensToUse = new ArrayList<>();
    private Gomoku gomoku;
    private HashMap<String, TimePassed> times = new HashMap<>();
    private HashMap<String, Timer> timers = new HashMap<>();
    
    /**
     * Creates an instance of Human.
     * @param name
     */
    public Human(String name, Gomoku gomoku){
        this.name = name;
        this.gomoku = gomoku;
        times.put("timeT", new TimePassed(0));
        times.put("timeM", new TimePassed(0));
        Timer timerTotal = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarTiempo();
            }
			private void actualizarTiempo() {
			}
        });
        Timer timerMissing = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarTiempo();
            }
			private void actualizarTiempo() {
			}
        });
		timers.put("timerT", timerTotal);
		timers.put("timerM", timerMissing);
    }
    
    public javax.swing.Timer getTimer(String timer) {
    	return timers.get(timer);
    }
    
    
    /**
     * Sets the token matrix for the player.
     * @param matrix: Token matrix
     */
    public void setTokenMatrix(Token[][] matrix){
        this.tokenMatrix = matrix;
    }
    /**
     * Set color of tokens
     * @param color: color of the tokens
     */
    public void setColor(Color color){
        this.color = color;
    }

    /*
     * Return the color of the player
     */
    public Color getColor(){
        return color;
    }

    /**
     * Sets the token in the respective lists and matrixes
     */
    public void setToken(Token token, int xPos, int yPos, String tokenType){
        tokens.add(token);
        tokenMatrix[xPos][yPos] = token;
    }
    
    /**
     * Returns the token matrix of the player
     */
    public Token[][] returnTokenMatrix(){
        return tokenMatrix;
    }
    
    /**
     * Starts the time the player takes to play
     */
    public void startTime(){
    	for(Timer timer: timers.values()) {
            if (!timer.isRunning()) {
                timer.start();
            }
    	}

    }
    
    /**
     * Stops the time the player takes to play
     */
    public void stopTime() {
    	for(Timer timer: timers.values()) {
            if (timer.isRunning()) {
                timer.stop();
            }
    	}

    }
    
    /**
     * Return the name of the player.
     */
    public String getName(){
        return name;
    }
    
    /**
     * Deletes the token in the tokenMatrix and the arrayList tokens
     * @param xPos
     * @param yPos
     */
    public void deleteToken(int xPos, int yPos){
    	this.tokens.remove(this.tokenMatrix[xPos][yPos]);
        tokenMatrix[xPos][yPos] = null;
    }


	/**
	 * Sets the arraylist containing string that determine the tokens the player's going to use.
	 * @param tokens
	 */
	public void setTokensToUse(ArrayList<String> tokens) {
		tokensToUse = tokens;
	}


	/**
	 * Returns the string containing the name of the token subclass the player's going to use.
	 */
	public String getTokenToUse() {
		String gameMode = Gomoku.getGomoku().getGameMode();
		String tokenType = tokensToUse.get(0);
		if(tokensToUse.size() > 0 ){
			tokensToUse.remove(0);
		}
		else if(gameMode.equals("normal") || gameMode.equals("quicktime") && tokensToUse.size() == 0) {
			gomoku.createTokensToUse(this.name); // evitamos que se quede sin fichas.
		}
		else if(tokensToUse.size() == 0 && gameMode.equals("limited")) {
			return null;
		}
		return tokenType;
	}
	
	/**
	 * Returns the arrayList containing the strings that represent the tokens subclasses names the player's going to use.
	 * @return
	 */
	public ArrayList<String> getTokensToUse(){
		return this.tokensToUse;
	}
	
	
	/**
	 * Sets the time left
	 */
	public void setTime(Integer time) {
		times.get("timeM").setTime(time);
		
	}
	
	/**
	 * Validates the time left
	 */
	public boolean validateTime() {
		return times.get("timeM").getTime() <= 0;
	}
	
	/**W
	 * Returns the tokens left to use by the player.
	 */
	public int getTokensLeft() {
		return this.tokensToUse.size();
	}

	public TimePassed getTime(String time) {

		return this.times.get(time);
	}


}
