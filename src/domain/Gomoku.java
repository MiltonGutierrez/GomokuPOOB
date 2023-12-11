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
    private Color lastColor;
    private boolean ok;
    
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
    		ok = true;
    	}
        catch(GomokuException e) {
        	JOptionPane.showMessageDialog(null, e, "Advertencia", JOptionPane.INFORMATION_MESSAGE);
        	Log.record(e);
        	ok = false;
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
    	ok = true;
        try {
            lastPositionTokens = null;
            String playerName = getTurn();
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
        	ok = false;
        }
    }

    /**
     * Starts the player's timer.
     * @throws GomokuException 
     */
    public void startPlayerTimer(String playerName){
    	ok = true;
    	try {
    		loadPlayer(playerName).startTime();
    	}
        catch(GomokuException e) {
        	JOptionPane.showMessageDialog(null, e, "Advertencia", JOptionPane.INFORMATION_MESSAGE);
        	Log.record(e);
        	ok = false;
        }
    }
    
    /**
     * Stops the player's timer
     * @throws GomokuException 
     */
    public void stopPlayerTimer(String playerName){
    	ok = true;
    	try {
    		loadPlayer(playerName).stopTime();
    	}
        catch(GomokuException e) {
        	JOptionPane.showMessageDialog(null, e, "Advertencia", JOptionPane.INFORMATION_MESSAGE);
        	Log.record(e);
        	ok = false;
        }
    }

    /**
     * Returns the total time of a player.
     * @param playerName
     * @return
     * @throws GomokuException 
     */
    public int getPlayerTotalTime(String playerName){
    	ok = true;
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
    	ok = true;
        if(turn.equals(nameP1)){
            this.turn = nameP2;
        }
        else{
            this.turn = nameP1;
        }
    }
    
    /**
     * Create the instances of players
     * @throws InvocationTargetException
     */
    public void createRivals(){
    	ok = true;
        if(opponent == "pvp"){
            players.put(nameP1, new Human(nameP1, Gomoku.getGomoku()));
            players.put(nameP2, new Human(nameP2, Gomoku.getGomoku()));
        }else if(opponent == "pve"){ 
            players.put(nameP1, new Human(nameP1, Gomoku.getGomoku()));
            setNameP2("machine");
            players.put(nameP2, createMachine(machineType));
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
     * Creates a new instance of Machine
     * @param type
     * @return
     * @throws java.lang.reflect.InvocationTargetException
     */
    public void addToken(String tokenType, String playerName, int[] position){
    	ok = true;
        try{
            Class<?> tokenChilds = Class.forName("domain."+tokenType+"Token");
            Constructor<?> constructorTokenChilds = tokenChilds.getConstructor(Color.class, int[].class, Player.class, Gomoku.class);
            Player player = players.get(playerName);
            Token token = (Token) constructorTokenChilds.newInstance(player.getColor(), position, player, Gomoku.getGomoku());
            lastColor = player.getColor();
            player.setToken(token, position[0], position[1], tokenType);
            tokenMatrix[position[0]][position[1]] = token;
            tokens.add(token);
        } catch(ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException | java.lang.reflect.InvocationTargetException e){
        	JOptionPane.showMessageDialog(null, e, "Advertencia", JOptionPane.INFORMATION_MESSAGE);
        	Log.record(e);
        	ok = true;
        }
    }
    
    /**
     * Creates a new instance of Machine
     * @param type
     * @return
     * @throws java.lang.reflect.InvocationTargetException
     */
    public Machine createMachine(String type){
    	ok = true;
        Machine machine = null;
        try{
            Class<?> machineChilds = Class.forName("domain."+type+"Machine");
            Constructor<?> constructorMachineChilds = machineChilds.getConstructor(String.class);
            machine = (Machine) constructorMachineChilds.newInstance(nameP2);
        } catch(ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException | java.lang.reflect.InvocationTargetException e){
            Log.record(e);
            ok = false;
            return machine;
        }
		return machine;
    }
	/**
     * Returns the winner of gomoku if gomokuFinished.
     * @return
     */
	public String getWinner(){
		ok = true;
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
     * 
     * @return
     */
    public String getTurn() {
    	ok = true;
		return turn;
	}

    
    /**
     * Returns the value of gomokuFinished.
     * @return  gomokuFinshed
     */
    public boolean getGomokuFinished(){
    	ok = true;
        return verifier.getGomokuFinished();
    }

    
    /**
     * Sets the dimension of Gomoku
     * @param dimension
     */
    public void setDimension(int dimension){
        this.dimension = dimension;
    }
    
    /**
     * Return the dimension of Gomoku
     * @return dimension
     */
    public int getDimension() {
    	ok = true;
    	return dimension;
    }
    

    /**
     * Sets the gameMode of Gomoku
     * @param gameMode
     */
    public void setGameMode(String gameMode){
    	ok = true;
        this.gameMode = gameMode;
    }
    /**
     * Returns the gameMode of gomoku
     * @return gameMode
     */
    public String getGameMode(){
    	ok = true;
        return gameMode;
    }
    /**
     * Sets the opponent of Gomoku
     * @param gameMode
     */
    public void setOpponent(String oponente){
    	ok = true;
        this.opponent = oponente;
    }

    /**
     * Returns the opponent of gomoku
     * @return gameMode
     */
    public String getOpponent(){
    	ok = true;
        return opponent;
    }

    /**
     * Set the name of the player 1
     * @param nombre
     */
    public void setNameP1(String nombre){
    	ok = true;
        this.nameP1 = nombre;
    }
    
	/**
	 * 
	 * @return
	 */
	public static String getP1() {
		return nameP1;
	}
	
    /**
     * Set the name of the player 2
     * @param nombre
     */
    public void setNameP2(String nombre){
    	ok = true;
        this.nameP2 = nombre;
    }
	/**
	 * 
	 * @return
	 */
	public static String getP2() {
		return nameP2;
	}

    /**
     * Set the type of machine
     */
    public void setMachineType(String type){
    	ok = true;
        machineType = type;
    }


    /**
     * Set's the player's color.
     * @param jugador
     * @param color
     * @throws GomokuException
     */
    public void setColor(String jugador, Color color){
    	ok = true;
        try {
	    	Player player = loadPlayer(jugador);
	        player.setColor(color);
        }
        catch(GomokuException e) {
        	JOptionPane.showMessageDialog(null, e, "Advertencia", JOptionPane.INFORMATION_MESSAGE);
        	Log.record(e);
        	ok = false;
        }
    }

    /**
     * Return the player's color.
     * @param jugador
     * @return colorString
     * @throws GomokuException 
     */
    public String getColor(String jugador){
    	ok = true;
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
	 * Returns the color of the player that played in the last turn.
	 * @return Color.
	 */
	public Color getLastColor(){
		return lastColor;
	}
	
    
    /**
     * Return the HashMap of players.
     * @return players.
     */
    public HashMap<String, Player> getPlayers(){
    	ok = true;
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
    	ok = true;
        return time;
    }
    
    //Nuevos metodos.
    
    /**
     * 
     * @param placeToX
     * @param placeToY
     */ 
    public void calculateLastPositionTokens(int xPos, int yPos){
    	ok = true;
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
     * Returns the position of the last placed token.
     * @return lastPositionTOken
     */
    public ArrayList<int[]> getLastPositionTokens(){
    	ok = true;
        return lastPositionTokens;
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
    	ok = true;
        int[] position = token.getPosition();
        this.tokenMatrix[position[0]][position[1]] = null;
        try {
			loadPlayer(token.getNameOfPlayer()).deleteToken(position[0], position[1]);
		} catch (GomokuException e) {
        	JOptionPane.showMessageDialog(null, e, "Advertencia", JOptionPane.INFORMATION_MESSAGE);
        	Log.record(e);
        	ok = false;
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
    	ok = true;
        return tokenMatrix[xPos][yPos];
    }
    
    
    /**
     * Returns the tokenMatrix of Gomoku
     * @return
     */
	public Token[][] getTokenMatrix() {
		ok = true;
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
	 * 
	 * @return
	 */
	public ArrayList<String> getTypesOfTokens(){
		return this.typeOfTokens;
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
	 * 
	 * @return
	 */
	public ArrayList<Token> getTokens(){
		return this.tokens;
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
	 * 
	 * @return
	 */
	public boolean ok() {
		return this.ok;
	}
	
}
