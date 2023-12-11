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
    //Nuevos atributos
    private ArrayList<String> tokensToUse = new ArrayList<>();
    private Gomoku gomoku;

    /**
     * Creates an instance of Human.
     * @param name
     */
    public Human(String name, Gomoku gomoku){
        this.name = name;
        this.gomoku = gomoku;
        time = 0;  
    }
    
    
    public void setTokenMatrix(Token[][] matrix){
        this.tokenMatrix = matrix;
    }
    /**
     * Set color of tokens
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
     * 
     */
    public void setToken(Token token, int xPos, int yPos, String tokenType){
        tokens.add(token);
        tokenMatrix[xPos][yPos] = token;
    }
    
    public Token[][] returnTokenMatrix(){
        return tokenMatrix;
    }

    private long tiempoInicio = 0;
    private long tiempoFinal = 0;

    public void startTime(){
        tiempoInicio = System.currentTimeMillis();
    }

    public void stopTime(){
        tiempoFinal = System.currentTimeMillis();
        calculateTotalTime();
    }


    public void calculateTotalTime(){
        int inicial = (int)(tiempoInicio * Math.pow(1000,-1));
        int finaltime = (int)(tiempoFinal * Math.pow(1000,-1));
        long segundos = (long) ((tiempoFinal-tiempoInicio) * Math.pow(1000,-1));
        time += Math.abs(segundos);
        tiempoInicio = 0;
        tiempoFinal = 0;
    }

    public int getTotalTime(){
        return time;
    }

    //Nuevos metodos

    public String getName(){
        return name;
    }
    
    /**
     * 
     */
    public void deleteToken(int xPos, int yPos){
        tokenMatrix[xPos][yPos] = null;
    }


	/**
	 * 
	 */
	public void setTokensToUse(ArrayList<String> tokens) {
		tokensToUse = tokens;
	}


	/**
	 * 
	 */
	public String getTokenToUse() {
		String tokenType = tokensToUse.get(0);
		if(tokensToUse.size() > 0 ){
			tokensToUse.remove(0);
		}
		else {
			gomoku.createTokensToUse(this.name); // evitamos que se quede sin fichas.
		}
		return tokenType;
	}
	
	/**
	 * 
	 * @return
	 */
	public ArrayList<String> getTokensToUse(){
		return this.tokensToUse;
	}
	
	private int timeLeft;
	
	public void setTime(int time) {
		timeLeft = time;
	}
	
	public int validateTime() {
		return (timeLeft - time);
	}
	

}
