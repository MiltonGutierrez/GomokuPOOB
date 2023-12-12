package domain;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;

public class Human implements Player {
    private String name;
    private Color color;
    private ArrayList<Token> tokens = new ArrayList<>();
    private Token[][] tokenMatrix;
    private int time;
    private ArrayList<String> tokensToUse = new ArrayList<>();
    private Gomoku gomoku;
	private int timeLeft;
    private long tiempoInicio = 0;
    private long tiempoFinal = 0;

    /**
     * Creates an instance of Human.
     * @param name
     */
    public Human(String name, Gomoku gomoku){
        this.name = name;
        this.gomoku = gomoku;
        time = 0;  
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
        tiempoInicio = System.currentTimeMillis();
    }
    
    /**
     * Stops the time of the player.
     */
    public void stopTime(){
        tiempoFinal = System.currentTimeMillis();
        calculateTotalTime();
    }

    /**
     * Calculates the time the player's spent.
     */
    public void calculateTotalTime(){
        int inicial = (int)(tiempoInicio * Math.pow(1000,-1));
        int finaltime = (int)(tiempoFinal * Math.pow(1000,-1));
        long segundos = (long) ((tiempoFinal-tiempoInicio) * Math.pow(1000,-1));
        time += Math.abs(segundos);
        tiempoInicio = 0;
        tiempoFinal = 0;
    }
    
    /**
     * Returns the total time the player's spent.
     */
    public int getTotalTime(){
        return time;
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
	public void setTime(int time) {
		timeLeft = time;
	}
	
	/**
	 * Validates the time left
	 */
	public int validateTime() {
		return (timeLeft - time);
	}


	/**
	 * Returns the tokens left to use by the player.
	 */
	public int getTokensLeft() {
		return this.tokensToUse.size();
	}
	

}
