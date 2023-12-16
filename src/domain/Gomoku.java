package domain;
import java.util.*;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import java.awt.*;
import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class Gomoku implements Serializable{
    public static Gomoku board = null;
    private int dimension;
    private HashMap<String, Player> players;
    private ArrayList<Token> tokens = new ArrayList<>();
    private ArrayList<Box> boxes = new ArrayList<>();
    private Token[][] tokenMatrix;
    private Box[][] boxMatrix;
    private String gameMode;
    private String opponent; //"pvp" "pve"
    private String nameP1;
    private String nameP2 = "machine";
    private String turn;
    private String machineType;
    private int cellsMissing;

    //Nuevos atributos
    private ArrayList<String> typeOfTokens = new ArrayList<>();
    private ArrayList<String> typeOfBoxes = new ArrayList<>();
    private ArrayList<String> boxesToUse = new ArrayList<>();
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
    	this.setTypeOfBoxes();
    	this.createBoxesToUse();
        Box[][] boxMatrix = new Box[dimension][dimension];
        for(int i = 0; i < dimension; i++){
            for(int j = 0; j < dimension; j++){
                boxMatrix[i][j] = this.createBox(this.boxesToUse.get(0), new int[]{i, j});
                this.boxesToUse.remove(0);
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
     * Play the game
     * @param xPos
     * @param yPos
     * @throws InvocationTargetException
     * @throws GomokuException 
     */
    public void play(int xPos, int yPos){
    	ok = true;
        try {
            lastPositionTokens = null;
            String playerName = getTurn();
            updateTicks();
            int[] position = {xPos, yPos};
            Token t = addToken(getTokenType(), playerName, position);
            this.addToken(playerName, t, position);
            updateTokens();
            calculateLastPositionTokens();
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
    		return loadPlayer(playerName).getTime("timeT").getTime();
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
        	setNameP2("machine");
            players.put(nameP1, new Human(nameP1, Gomoku.getGomoku()));
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
     * Sets the token to the specified player and matrixes
     * @param playerName
     * @param token
     * @param position
     */
    public void addToken(String playerName, Token token, int[] position) {
    	ok = true;
    	Player player;
		try {
			player = loadPlayer(playerName);
			player.setToken(token, position[0], position[1]);
	        tokenMatrix[position[0]][position[1]] = token;
	        tokens.add(token);
	        boxMatrix[position[0]][position[1]].setToken(token);
	        if(!(token instanceof NormalToken)) {
            	setPuntuacion(playerName, 100); //
            }
		} catch (GomokuException e) {
			ok = false;
			Log.record(e);
		}

    }
    /**
     * Creates a new instance of Machine
     * @param type
     * @throws java.lang.reflect.InvocationTargetExceptions
     */
    public Token addToken(String tokenType, String playerName, int[] position){
    	ok = true;
    	Token token = null;
        try{
            Class<?> tokenChilds = Class.forName("domain."+tokenType+"Token");
            Constructor<?> constructorTokenChilds = tokenChilds.getConstructor(Color.class, int[].class, Player.class, Gomoku.class);
            Player player = players.get(playerName);
            token = (Token) constructorTokenChilds.newInstance(player.getColor(), position, player, Gomoku.getGomoku());
            lastColor = player.getColor();
        } catch(ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException | java.lang.reflect.InvocationTargetException e){
        	JOptionPane.showMessageDialog(null, e, "Advertencia", JOptionPane.INFORMATION_MESSAGE);
        	Log.record(e);
        	ok = true;
            return null;
        }
        return token;
        
    }
    
    
    /**
     * Creates a new instance of Machine
     * @param type
     * @return machine is the new instansce
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
        }
		return machine;
    }
    
    public Box createBox(String type, int[] position) {
    	ok = true;
    	Box box = null;
        try{
            Class<?> boxChilds = Class.forName("domain."+type+"Box");
            Constructor<?> constructorBoxChilds = boxChilds.getConstructor(int[].class);
            box = (Box) constructorBoxChilds.newInstance(position);
        } catch(ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException | java.lang.reflect.InvocationTargetException e){
            Log.record(e);
            ok = false;
        }
		return box; //hola
    	
    }
	/**
     * Returns the winner of gomoku if gomokuFinished.
     * @return winner's name of the game
     */
	public String getWinner(){
		ok = true;
        String result = "";
        boolean gomokuFinished = verifier.getGomokuFinished();
        if(gameMode == "quicktime" && gomokuFinished) {
        	if(players.get(turn).validateTime() != false) {
        		if(turn == nameP1) {
        			stopGame();
        			return nameP2;
        		}else {
        			stopGame();
        			return nameP1;
        		}
        	}
        }
        if(gomokuFinished){
        	stopGame();
            return turn;
        }
        else if(!gomokuFinished && cellsMissing == 0){
        	stopGame();
            return result + "Empate";
        }
        stopGame();
        return result;
    }
	
    /**
     * Returns the current player to play
     * @return name of the player playing
     */
    public String getTurn() {
    	ok = true;
		return turn;
	}

    
    /**
     * Returns the value of gomokuFinished.
     * @return gomokuFinshed is the status of the game
     */
    public boolean getGomokuFinished(){
    	ok = true;
        return verifier.getGomokuFinished();
    }

    
    /**
     * Sets the dimension of Gomoku
     * @param dimension size of the Gomoku's board
     */
    public void setDimension(int dimension){
    	
        this.dimension = dimension;
    }
    
    /**
     * Return the dimension of Gomoku
     * @return dimension is the dimention of the board
     */
    public int getDimension() {
    	ok = true;
    	return dimension;
    }
    

    /**
     * Sets the gameMode of Gomoku
     * @param gameMode set the current gamemode of Gomoku
     */
    public void setGameMode(String gameMode){
    	ok = true;
        this.gameMode = gameMode;
    }
    /**
     * Returns the gameMode of gomoku
     * @return gameMode the current gamemode of Gomoku
     */
    public String getGameMode(){
    	ok = true;
        return gameMode;
    }
    /**
     * Sets the opponent of Gomoku
     * @param oponente is the opponent of the game (Human or Machine) 
     */
    public void setOpponent(String oponente){
    	ok = true;
        this.opponent = oponente;
    }

    /**
     * Returns the opponent of gomoku
     * @return opponent is the current opponent
     */
    public String getOpponent(){
    	ok = true;
        return opponent;
    }

    /**
     * Set the name of the player 1
     * @param nombre is the name of player 1
     */
    public void setNameP1(String nombre){
    	ok = true;
        nameP1 = nombre;
    }
    
	/**
	 * Returns the name of player 1
	 * @return nameP1 is the name of the player 1
	 */
	public String getP1() {
		return nameP1;
	}
	
    /**
     * Set the name of the player 2
     * @param nombre is the name to set to player 2
     */
    public void setNameP2(String nombre){
    	ok = true;
        nameP2 = nombre;
    }
	/**
	 * Returns the name of the player 2
	 * @return nameP2 is the name of the player 2
	 */
	public String getP2() {
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
     * @param jugador is the player to set color
     * @param color is the player's color
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
     * @param jugador is the player to get his color
     * @return colorString is the color of the selected player in hex format
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
     * @param time is the time to split in quickmode 
     */
    public void setTime(Integer time){
        Integer timeForPlayers = (time / 2);
        for(Player p: players.values()) {
        	p.setTime(timeForPlayers);
        }
    }

    
    //Nuevos metodos.
    
    /**
     * Calculates the last position of the tokens
     * @param xPos is the x position
     * @param yPos is the y position
     */ 
    public void calculateLastPositionTokens(){
    	ok = true;
        ArrayList<int[]> positionOfTokens = new ArrayList<>();
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
     * @param token is the token to delete
     * @throws GomokuException
     */
    private void deleteToken(Token token) {
    	ok = true;
        int[] position = token.getPosition();
        this.tokenMatrix[position[0]][position[1]] = null;
        this.boxMatrix[position[0]][position[1]].deleteToken();
        try {
			loadPlayer(token.getNameOfPlayer()).deleteToken(position[0], position[1]);
			loadPlayer(token.getNameOfPlayer()).setPuntuacion(-50);
			if(token.getNameOfPlayer() != getTurn()) {
				loadPlayer(token.getNameOfPlayer()).setPuntuacion(100);
			}
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
     * @param xPos is the x position
     * @param yPos is the y position
     * @return token is the desired token
     */
    public Token getToken(int xPos, int yPos){
    	ok = true;
        return tokenMatrix[xPos][yPos];
    }
    
    /**
     * Set's the special token percentage appearance
     * @param percentage
     */
    public void setTokenPercentage(int percentage) {
    	if(percentage <= 0) {
    		this.tokensPercentage = 0;
    	}
    	else {
    		this.tokensPercentage = (int) ((int) Math.round(dimension * dimension / 2) * (percentage / 100f));
    	}
    	
    }
    
    /**
     * Set's the boxes token percentage appearance
     * @param percentage
     */
    public void setBoxPercentage(int percentage) {
    	if(percentage <= 0) {
    		this.boxesPercentage = 0;
    	}
    	else {
    		this.boxesPercentage = dimension * dimension  * (percentage / 100);
    	}
    	
    	
    } 
    
    /**
     * Returns the tokenMatrix of Gomoku
     * @return tokenMatrix is the tonkens matrix
     */
	public Token[][] getTokenMatrix() {
		ok = true;
		return tokenMatrix;
	}
	/**
	 * Adds the type of boxes used in gomoku
	 */
    public void setTypeOfBoxes() {
    	this.typeOfBoxes.add("Normal");
    	this.typeOfBoxes.add("Teleporting");
    	this.typeOfBoxes.add("Golden");
    	this.typeOfBoxes.add("Explosive");
    }
    
    /**
     * Returns the box position at (xPos, yPos) in the boxMatrix
     * @param xPos
     * @param yPos
     * @return
     */
    public Box getBox(int xPos, int yPos) {
    	return this.boxMatrix[xPos][yPos];
    	
    }
    
    public Box[][] getBoxMatrix(){
    	return this.boxMatrix;
    }
    /**
     * Creates the ArrayList containing strings that represent the type of boxes to use.
     */
    public void createBoxesToUse() {
    	Random random = new Random();
    	ArrayList<String> boxes = new ArrayList<>();
    	int quantityOfBoxes = dimension * dimension;
    	if(gameMode.equals("normal")) {
    		for(int i = 0; i < quantityOfBoxes - boxesPercentage; i++) {
    			boxes.add(typeOfBoxes.get(0));
    		}
    		for(int i = quantityOfBoxes - boxesPercentage; i < quantityOfBoxes; i++) {
    			boxes.add(typeOfBoxes.get(random.nextInt(3) + 1));
    		}
    		Collections.shuffle(boxes);
    		this.boxesToUse = boxes;
    	}
    	else {
    		for(int i = 0; i < quantityOfBoxes; i++) {
    			boxes.add(typeOfBoxes.get(0));
    		}
    		this.boxesToUse = boxes;
    	}
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
     * Returns the typeOfTokens array
     * @return
     */
    public ArrayList<String> getTypeOfTokens(){
    	return this.typeOfTokens;
    }
    
    /**
     * Creates the tokens list the players are gona use when playing.
     * @param playerName is the player name to create the token
     * @throws GomokuException 
     */
    public void createTokensToUse(String playerName){
    	Random random = new Random();
    	ArrayList<String> tokens = new ArrayList<>(); 
    	if(gameMode.equals("normal") || gameMode.equals("quicktime")) {
    		createTokensToUse(playerName, tokensPercentage, random, tokens);
    	}
    	else if(gameMode.equals("limited")) {
    		createTokensToUse(playerName, tokens);
    	}
    	else {
    		createTokensToUse(playerName, tokens);
    	}
    }
    
    /**
     * Creates the token to use when the gameMode is Normal.
     * @param playerName is the player name
     * @param tokens are the tokens to use
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
     * Creates the token to use when the gameMode is Normal.
     * @param playerName is the player name
     * @param tokensPercentage is the percentage of special tokens
     * @param random is the random object to select the next token
     * @param tokens is the array of tokens
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
     * Returns the token a player is next to use
     * @return the token to use in the next turn
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
	 * Returns the types of tokens available
	 * @return arraylist which contains the types of tokens
	 */
	public ArrayList<String> getTypesOfTokens(){
		return this.typeOfTokens;
	}
	
	
	/**
	 * Returns the tokens percentage the players have.
	 * @return the current percentage of special tokens
	 */
	public int getTokensPercentage(){
		return this.tokensPercentage;
	}
	
	/**
	 * Sets the tokens percentage.
	 * @param percentage is the percentage of special tokens to set
	 */
	public void setTokensPercentage(int percentage) {
		this.tokensPercentage = percentage;
	}
	/**
	 * Returns the tokens of the game
	 * @return arraylist of tokens
	 */
	public ArrayList<Token> getTokens(){
		return this.tokens;
	}
	
	/**
	 * Makes the validations to verify if there's a winner.
	 * @param xPos is the x coordinate
	 * @param yPos is the y coordinate
	 * @param dimension is the dimention of the board
	 * @param matrix is the token matrix
	 */
	public void winner(int xPos, int yPos, int dimension, Token[][] matrix){
		this.verifier.winner(xPos, yPos, dimension, matrix);
	}
	
	/**
	 * Returns the Player.
	 * @param playerName is the player to get
	 * @return Player is the desired player
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
	 * Its a flag method
	 * @return the last operation status (true is it was successful or false if it isn't)
	 */
	public boolean ok() {
		return this.ok;
	}
	
	/**
	 * Returns the time for the player 1 
	 * @return is the time left for the player 1
	 */
	public TimePassed getTimeP1(String typeOfTime){
		return players.get(nameP1).getTime(typeOfTime);
	}
	
	/**
	 * Returns the time for the player 2 
	 * @param typeOfTime
	 * @return
	 */
	public TimePassed getTimeP2(String typeOfTime){
		return players.get(nameP2).getTime(typeOfTime);
	}
		

	/**
	 * Returns the timer 
	 * @param playerName
	 * @param typeOfTimer
	 * @return
	 */
	public javax.swing.Timer getTimer(String playerName, String typeOfTimer) {
		try {
			return loadPlayer(playerName).getTimer(typeOfTimer);
		} catch (GomokuException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Stops the timer of the player when the game is finished.
	 */
	public void stopGame(){
		this.stopPlayerTimer(nameP1);
		this.stopPlayerTimer(nameP2);
	}
	
	/**
	 * Makes the time validation
	 */
	public void timeValidation() {
		verifier.timeValidation();
	}
	
	public void nullAll() {
		board.stopPlayerTimer(nameP1);
		board.stopPlayerTimer(nameP2);
		board = null;
	}
	
	/**
	 * Sets the score of the player
	 * @param name is the name of the player
	 * @param score is the score to add 
	 * @throws GomokuException
	 */
	public void setPuntuacion(String name, int score) throws GomokuException  {
		loadPlayer(name).setPuntuacion(score);
	}
	
	/**
	 * Returns the current score of the player
	 * @param name is the name of the player
	 * @return score of the player
	 * @throws GomokuException
	 */
	public int getPuntuacion(String name) throws GomokuException {
		return loadPlayer(name).getPuntuacion();
	} 
	
	/**
     * Saves the status of the game into a file
     * @param file
     * @throws ColonyException
     */
	public void save(File file) throws GomokuException {
	    try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file))) {
	        out.writeObject(this);
	    } catch (Exception e) {
	        Log.record(e);
	    }
	}
	
    /**
     * Opens a game from a data file
     * @param file
     * @return
     * @throws ColonyException
     */
    public static void open01(File file) throws GomokuException {
        board = null;
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
          	Gomoku loadGomoku= (Gomoku) in.readObject();
          	board = loadGomoku;
        } catch (Exception e) {
            Log.record(e);
        }
        System.out.println(board.getGameMode());
    }
    
    
    
    
    
}
