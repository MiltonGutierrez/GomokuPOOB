package domain;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;

public class Human implements Player {
    private String name;
    private Color color;
    private ArrayList<Token> tokens = new ArrayList<>();
    private Token[][] tokenMatrix;
    private HashMap<String, Integer> quantityOfTokens;
    private int time;
    //Nuevos atributos
    private ArrayList<String> tokensToUse = new ArrayList<>();

    /**
     * Creates an instance of Human.
     * @param name
     */
    public Human(String name){
        this.name = name;
        time = 0;
        createHashMapTokens();
    
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
        substractAToken(tokenType);
    }
    

    public void substractAToken(String tokenType){
        int currentQuantity = quantityOfTokens.get(tokenType);
        currentQuantity--;
        quantityOfTokens.put(tokenType, currentQuantity--);
    }

    public Token[][] returnTokenMatrix(){
        return tokenMatrix;
    }

    private long tiempoInicio = 0;
    private long tiempoFinal = 0;

    public void startTime(){
        tiempoInicio = System.currentTimeMillis();
    }

    public void endTime(){
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

    private String typeOfToken = "Normal";

    public void changeToken(String token){
        typeOfToken = token;
    }

    public String getToken(){
        return typeOfToken;
    }

    public void createHashMapTokens(){
        quantityOfTokens = new HashMap<>();
        quantityOfTokens.put("Normal", 0);
        quantityOfTokens.put("Heavy", 0);
        quantityOfTokens.put("Temporal", 0);
    }

    public void setQuantityTokens(int quantity){
        for (String type : quantityOfTokens.keySet()) {
            quantityOfTokens.put(type, quantity);
        }
    }


    public int getTokensLeft(String typeOfToken){
        int currentQuantity = quantityOfTokens.get(typeOfToken);
        return currentQuantity;
    }

    //Nuevos metodos

    public String getName(){
        return name;
    }
    
    @Override
    public void deleteToken(int xPos, int yPos){
        tokenMatrix[xPos][yPos] = null;
    }


	@Override
	public void setTokensToUse(ArrayList<String> tokens) {
		tokensToUse = tokens;
	}
}
