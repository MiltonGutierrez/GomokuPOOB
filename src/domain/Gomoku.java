package domain;
import java.util.*;

import javax.swing.JOptionPane;

import java.awt.*;
import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class Gomoku {
    public static Gomoku board = null;
    private static int dimension;
    private HashMap<String, Player> players;
    private ArrayList<Token> tokens = new ArrayList<>();
    private ArrayList<Box> boxes = new ArrayList<>();
    private Token[][] tokenMatrix;
    private Box[][] boxMatrix;
    private String gameMode;
    private String opponent; //"pvp" "pve"
    private static String nameP1;
    private static String nameP2 = "machine";
    private String turn;
    private String machineType;
    private int timeLimit;
    private int cellsMissing;
    private int ticks = 0;

    //Nuevos atributos
    private ArrayList<String> typeOfTokens = new ArrayList<>(); 
    private GomokuVerifier verifier = new GomokuVerifier(this);
    private int tokensPercentage = 30; // by default
    private int boxesPercentage = 30; //by default
    private ArrayList<int[]> lastPositionTokens; //Calculates the token positions to change in the gui.
    private String lastTurn;
    
    /**
     * Creates an instansce of Gomoku
     */
    private Gomoku() {
        this.players = new HashMap<>();
    }
    
    /**
     * Returns a Gomoku instance if it hasn't been created
     * @return Gomoku instance
     */
    public static Gomoku getGomoku() {
        if (board == null) {
            board = new Gomoku(); // Crea la instancia solo si no existe
        }
        return board;
    }
    
    /**
     * Returns true if valid play, false otherwise
     * @param xPos
     * @param yPos
     * @return validPlay
     */
    public boolean validPlay(int xPos, int yPos) {
    	return verifier.validPlay(xPos, yPos);
    }
    
    /**
     * Sets the dimension of Gomoku
     * @param dimension
     */
    public void setDimension(int dimension){
        this.dimension = dimension;
    }
    
    /**
     * Returns a new Token matrix
     * @return tokenMatrix
     */
    private Token[][] createTokenMatrix(){
        Token[][] tokenMatrix = new Token[dimension][dimension];
        for(int i = 0; i < dimension; i++){
            for(int j = 0; j < dimension; j++){
                tokenMatrix[i][j] = null;
            }
        }
        return tokenMatrix;
    }
    
    /**
     * Returns a new Box matrix
     * @return boxMatrix
     */
    private Box[][] createBoxMatrix(){
        Box[][] boxMatrix = new Box[dimension][dimension];
        for(int i = 0; i < dimension; i++){
            for(int j = 0; j < dimension; j++){
                boxMatrix[i][j] = null;
            }
        }
        return boxMatrix;
    	
    }
    
    /**
     * Creates the tokensBoards for Gomoku and players 
     * @throws GomokuException 
     */
    public void createBoards(){
        tokenMatrix = createTokenMatrix();
        boxMatrix = createBoxMatrix();
        setPlayerTokenMatrix(nameP1);
        setPlayerTokenMatrix(nameP2);
    }
    
    /**
     * Creates and sets the tokenMatrix for a player.
     * @param playerName
     * @throws GomokuException 
     */
    public void setPlayerTokenMatrix(String playerName){
    	try {
    		loadPlayer(playerName).setTokenMatrix(createTokenMatrix());
    	}
        catch(GomokuException e) {
        	JOptionPane.showMessageDialog(null, e, "Advertencia", JOptionPane.INFORMATION_MESSAGE);
        	Log.record(e);
        }
    }

    /**
     * 
     * @param placeToX
     * @param placeToY
     * @param tokenType
     * @throws InvocationTargetException
     * @throws GomokuException 
     */
    public void play(int xPos, int yPos){
        try {
            lastPositionTokens = null;
            String playerName = getTurn();
            lastTurn = playerName;
            updateTicks();
            calculateLastPositionTokens(xPos, yPos);
            updateTokens();
            addToken(getTokenType(), playerName, new int[]{xPos, yPos});
            stopPlayerTimer(playerName);
            winner(xPos, yPos, dimension, loadPlayer(playerName).returnTokenMatrix());
            if(!verifier.getGomokuFinished()){
                nextTurn();
                startPlayerTimer(getTurn());
                cellsMissing--;
            }
        }
        catch(GomokuException e){
        	JOptionPane.showMessageDialog(null, e, "Advertencia", JOptionPane.INFORMATION_MESSAGE);
        	Log.record(e);
        }
    }
    
    /**
     * 
     * @return
     */
    public String getTurn() {
		return turn;
	}

	/**
     * Returns the winner of gomoku if gomokuFinished.
     * @return
     */
	public String getWinner(){
        String result = "";
        boolean gomokuFinished = verifier.getGomokuFinished();
        if(gomokuFinished){
            return turn;
        }
        else if(!gomokuFinished && cellsMissing == 0){
            return result + "Empate";
        }
        return result;
    }

    /**
     * Returns the value of gomokuFinished.
     * @return  gomokuFinshed
     */
    public boolean getGomokuFinished(){
        return verifier.getGomokuFinished();
    }


    /**
     * Starts the player's timer.
     * @throws GomokuException 
     */
    public void startPlayerTimer(String playerName){
    	try {
    		loadPlayer(playerName).startTime();
    	}
        catch(GomokuException e) {
        	JOptionPane.showMessageDialog(null, e, "Advertencia", JOptionPane.INFORMATION_MESSAGE);
        	Log.record(e);
        }
    }
    
    /**
     * Stops the player's timer
     * @throws GomokuException 
     */
    public void stopPlayerTimer(String playerName){
    	try {
    		loadPlayer(playerName).stopTime();
    	}
        catch(GomokuException e) {
        	JOptionPane.showMessageDialog(null, e, "Advertencia", JOptionPane.INFORMATION_MESSAGE);
        	Log.record(e);
        }
    }

    /**
     * Returns the total time of a player.
     * @param playerName
     * @return
     * @throws GomokuException 
     */
    public int getPlayerTotalTime(String playerName){
    	try {
    		return loadPlayer(playerName).getTotalTime();
    	}
    	catch(GomokuException e) {
        	JOptionPane.showMessageDialog(null, e, "Advertencia", JOptionPane.INFORMATION_MESSAGE);
        	Log.record(e);
        	return 0;
    	}
        
    }

    /**
     * Changes the turn.
     */
    public void nextTurn(){
        if(turn.equals(nameP1)){
            this.turn = nameP2;
        }
        else{
            this.turn = nameP1;
        }
    }

    /**
     * Creates a new instance of Machine
     * @param type
     * @return
     * @throws java.lang.reflect.InvocationTargetException
     */
    public void addToken(String tokenType, String playerName, int[] position){
        try{
            Class<?> tokenChilds = Class.forName("domain."+tokenType+"Token");
            Constructor<?> constructorTokenChilds = tokenChilds.getConstructor(Color.class, int[].class, Player.class, Gomoku.class);
            Player player = players.get(playerName);
            Token token = (Token) constructorTokenChilds.newInstance(player.getColor(), position, player, Gomoku.getGomoku());
            Color lastColor = player.getColor();
            player.setToken(token, position[0], position[1], tokenType);
            tokenMatrix[position[0]][position[1]] = token;
            tokens.add(token);
        } catch(ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException | java.lang.reflect.InvocationTargetException e){
        	JOptionPane.showMessageDialog(null, e, "Advertencia", JOptionPane.INFORMATION_MESSAGE);
        	Log.record(e);
        }
    }

    /**
     * Returns the gameMode of gomoku
     * @return gameMode
     */
    public String getGameMode(){
        return gameMode;
    }

    /**
     * Sets the gameMode of Gomoku
     * @param gameMode
     */
    public void setGameMode(String gameMode){
        this.gameMode = gameMode;
    }

    /**
     * Returns the opponent of gomoku
     * @return gameMode
     */
    public String getOpponent(){
        return opponent;
    }

    /**
     * Sets the opponent of Gomoku
     * @param gameMode
     */
    public void setOpponent(String oponente){
        this.opponent = oponente;
    }

    /**
     * Set the name of the player 1
     * @param nombre
     */
    public void setNameP1(String nombre){
        this.nameP1 = nombre;
    }
    

    /**
     * Set the name of the player 2
     * @param nombre
     */
    public void setNameP2(String nombre){
        this.nameP2 = nombre;
    }

    /**
     * Set the type of machine
     */
    public void setMachineType(String type){
        machineType = type;
    }


    /**
     * Create the instances of players
     * @throws InvocationTargetException
     */
    public void createRivals(){
        if(opponent == "pvp"){
            players.put(nameP1, new Human(nameP1, Gomoku.getGomoku()));
            players.put(nameP2, new Human(nameP2, Gomoku.getGomoku()));
        }else if(opponent == "pve"){ 
            players.put(nameP1, new Human(nameP1, Gomoku.getGomoku()));
            setNameP2("machine");
            players.put(nameP2, createMachine(machineType));
        }
        turn = nameP1;
    }
    
    /**
     * Creates a new instance of Machine
     * @param type
     * @return
     * @throws java.lang.reflect.InvocationTargetException
     */
    public Machine createMachine(String type){
        Machine machine;
        try{
            Class<?> machineChilds = Class.forName("domain."+type+"Machine");
            Constructor<?> constructorMachineChilds = machineChilds.getConstructor(String.class);
            machine = (Machine) constructorMachineChilds.newInstance(nameP2);
        } catch(ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException | java.lang.reflect.InvocationTargetException e){
            Log.record(e);
            return null;
        }
        return machine;
    }


    /**
     * Set's the player's color.
     * @param jugador
     * @param color
     * @throws GomokuException
     */
    public void setColor(String jugador, Color color){
        try {
	    	Player player = loadPlayer(jugador);
	        player.setColor(color);
        }
        catch(GomokuException e) {
        	JOptionPane.showMessageDialog(null, e, "Advertencia", JOptionPane.INFORMATION_MESSAGE);
        	Log.record(e);
        }
    }

    /**
     * Return the player's color.
     * @param jugador
     * @return colorString
     * @throws GomokuException 
     */
    public String getColor(String jugador){
    	try {
            Player player = loadPlayer(jugador);
            Color color = player.getColor();
            String colorString = String.format("#%06X", (0xFFFFFF & color.getRGB()));
            return colorString;
    	}
        catch(GomokuException e) {
        	JOptionPane.showMessageDialog(null, e, "Advertencia", JOptionPane.INFORMATION_MESSAGE);
        	Log.record(e);
        	return null;
        }
    }
    
    /**
     * Return the HashMap of players.
     * @return players.
     */
    public HashMap<String, Player> getPlayers(){
        return players;
    }

    /**
     * Set's the time limit of the game when the quick mode is used.
     * @param time
     */
    public void setTime(int time){
        this.timeLimit = time;
    }

    /**
     * Returns the time when the quick mode is used.
     * @param time
     * @return
     */
    public int getTime(int time){
        return time;
    }
    
    //Nuevos metodos.


    /**
     * Returns the position of the last placed token.
     * @return lastPositionTOken
     */
    public ArrayList<int[]> getLastPositionTokens(){
        return lastPositionTokens;
    }

    /**
     * 
     * @param placeToX
     * @param placeToY
     */ 
    public void calculateLastPositionTokens(int xPos, int yPos){
        ArrayList<int[]> positionOfTokens = new ArrayList<>();
        positionOfTokens.add(new int[]{xPos, yPos});
        for(Token t: tokens){
            if(t.getIdentifier() == 'D'){
                positionOfTokens.add(t.getPosition());
            }
        }
        this.lastPositionTokens = positionOfTokens;
    }
   
    /**
     * Updates the ticks of the tokens.
     */
    public void updateTicks(){
        for(Token t: tokens){
              t.updateTicks();
        }
    }
    
    /**
     * Deletes the token in the tokenMatrix of both players and Gomoku
     * @param token
     * @throws GomokuException
     */
    private void deleteToken(Token token) {
        int[] position = token.getPosition();
        this.tokenMatrix[position[0]][position[1]] = null;
        try {
			loadPlayer(token.getNameOfPlayer()).deleteToken(position[0], position[1]);
		} catch (GomokuException e) {
        	JOptionPane.showMessageDialog(null, e, "Advertencia", JOptionPane.INFORMATION_MESSAGE);
        	Log.record(e);
		}
    }
    
    /**
     * Updates (verifies if deletion should be done) the tokens 
     * @throws GomokuException
     */
    private void updateTokens(){
        Iterator<Token> iterador = tokens.iterator();
        while (iterador.hasNext()) {
            Token t = iterador.next();
            if (t.getIdentifier() == 'D') {
                deleteToken(t);
                iterador.remove();
            }
        }
    }

    
    /**
     * Returns the token in the position xPos, yPos of the tokenMatrix
     * @param xPos
     * @param yPos
     * @return token
     */
    public Token getToken(int xPos, int yPos){
        return tokenMatrix[xPos][yPos];
    }
    
    /**
     * Return the dimension of Gomoku
     * @return dimension
     */
    public int getDimension() {
    	return dimension;
    }
    
    /**
     * Returns the tokenMatrix of Gomoku
     * @return
     */
	public Token[][] getTokenMatrix() {
		return tokenMatrix;
	}
	
	/**
	 * Adds the type of tokens used in gomolu
	 */
    public void setTypeOfTokens() {
    	this.typeOfTokens.add("Normal");
    	this.typeOfTokens.add("Heavy");
    	this.typeOfTokens.add("Temporal");
    }
    
    /**
     * Creates the tokens kList the players are gona use when playing.
     * @param playerName
     * @throws GomokuException 
     */
    public void createTokensToUse(String playerName){
    	Random random = new Random();
    	ArrayList<String> tokens = new ArrayList<>(); 
    	if(gameMode.equals("normal") || gameMode.equals("quicktime")) {
    		createTokensToUse(playerName, tokensPercentage, random, tokens);
    	}
    	else if(gameMode.equals("piedrasLimitadas")) {
    		createTokensToUse(playerName, tokens);
    	}
    	else {
    		createTokensToUse(playerName, tokens);
    	}
    }
    
    /**
     * Creates the token to use when the gameMode is Normal.
     * @param playerName
     * @param tokens
     * @throws GomokuException 
     */
    public void createTokensToUse(String playerName, ArrayList<String> tokens){
		int quantityOfTokens = (dimension * dimension) / 2;
		for(int i = 0; i < quantityOfTokens; i++) {
			tokens.add(typeOfTokens.get(0));
		}
		try {
			loadPlayer(playerName).setTokensToUse(tokens);
		}
		catch(GomokuException e) {
        	JOptionPane.showMessageDialog(null, e, "Advertencia", JOptionPane.INFORMATION_MESSAGE);
        	Log.record(e);
		}
    	
    }
    /**
     * 
     * @param playerName
     * @param tokensPercentage
     * @param random
     * @param tokens
     */
    public void createTokensToUse(String playerName, int tokensPercentage, Random random, ArrayList<String> tokens) {
		int quantityOfTokens = (dimension * dimension) / 2;
		for(int i = 0; i < quantityOfTokens - tokensPercentage; i++) {
			tokens.add(typeOfTokens.get(0));
		}
		for(int i = quantityOfTokens - tokensPercentage; i < quantityOfTokens; i++) {
			tokens.add(typeOfTokens.get(random.nextInt(2) + 1));
		}
		Collections.shuffle(tokens);
		try {
			loadPlayer(playerName).setTokensToUse(tokens);
		} catch (GomokuException e) {
        	JOptionPane.showMessageDialog(null, e, "Advertencia", JOptionPane.INFORMATION_MESSAGE);
        	Log.record(e);
		}
    	
    }
    

    /**
     * Creates the important elements of the game and starts the first player timer.
     * @throws GomokuException 
     */
    public void startGame(){
        turn = nameP1;
        createBoards();
        startPlayerTimer(getTurn());
        setTypeOfTokens();
        createTokensToUse(nameP1);
        createTokensToUse(nameP2);
    }
    
    /**
     * Restuns the token a player is next to use
     * @return
     * @throws GomokuException
     */
	public String getTokenType(){
		try {
			return loadPlayer(turn).getTokenToUse();
		} catch (GomokuException e) {
        	JOptionPane.showMessageDialog(null, e, "Advertencia", JOptionPane.INFORMATION_MESSAGE);
        	Log.record(e);
        	return null;
		}
	}
	
	/**
	 * Returns the tokens percentage the players have.
	 * @return
	 */
	public int getTokensPercentage(){
		return this.tokensPercentage;
	}
	
	/**
	 * Sets the tokens percentage.
	 * @param percentage
	 */
	public void setTokensPercentage(int percentage) {
		this.tokensPercentage = percentage;
	}
	
	/**
	 * Makes the validations to verify if there's a winner.
	 * @param xPos
	 * @param yPos
	 * @param dimension
	 * @param matrix
	 */
	public void winner(int xPos, int yPos, int dimension, Token[][] matrix){
		this.verifier.winner(xPos, yPos, dimension, matrix);
	}
	
	/**
	 * Returns the Player.
	 * @param playerName
	 * @return Player
	 * @throws GomokuException
	 */
	public Player loadPlayer(String playerName) throws GomokuException{
		Player p = players.get(playerName);
		if(p != null) {
			return p;
		}
		else {
			throw new GomokuException(GomokuException.PLAYER_NOT_FOUND);
		}
	}
	
	/**
	 * Returns the color of the player that played in the last turn.
	 * @return Color.
	 */
	public Color getLastColor(){
		try {
			return loadPlayer(lastTurn).getColor();
		} catch (GomokuException e) {
        	JOptionPane.showMessageDialog(null, e, "Advertencia", JOptionPane.INFORMATION_MESSAGE);
        	Log.record(e);
        	return null;
		}
	}

	/**
	 * 
	 * @return
	 */
	public static String returnP1() {
		// TODO Auto-generated method stub
		return nameP1;
	}
	
	/**
	 * 
	 * @return
	 */
	public static String returnP2() {
		// TODO Auto-generated method stub
		return nameP2;
	}
	
	
	
}
