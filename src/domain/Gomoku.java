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
    private String nameP1;
    private String nameP2 = "machine";
    private String turn;
    private String machineType;
    private int timeLimit;
    private int cellsMissing;
    private int ticks = 0;
    
    //Nuevos atributos
    private ArrayList<String> typeOfTokens = new ArrayList<>(); 
    private GomokuVerifier verifier = new GomokuVerifier(this);
    
    /**
     * Creates an instansce of Gomoku
     */
    private Gomoku() {
        this.players = new HashMap<>();
    }

    public static Gomoku getGomoku() {
        if (board == null) {
            board = new Gomoku(); // Crea la instancia solo si no existe
        }
        return board;
    }
    
    
    public boolean validPlay(int xPos, int yPos) {
    	return verifier.validPlay(xPos, yPos);
    }
    
    
    public void setDimension(int dimension){
        this.dimension = dimension;
    }

    /**
     * 
     */
    public void createBoards(){
        tokenMatrix = new Token[dimension][dimension];
        boxMatrix = new Box[dimension][dimension];
        for(int i = 0; i < dimension; i++){
            for(int j = 0; j < dimension; j++){
                tokenMatrix[i][j] = null;
                boxMatrix[i][j] = null;
            }
        }
        setPlayerTokenMatrix(nameP1);
        setPlayerTokenMatrix(nameP2);
    }

    public void setPlayerTokenMatrix(String playerName){
        Token[][] tokens = new Token[dimension][dimension];
        for(int i = 0; i < dimension; i++){
            for(int j = 0; j < dimension; j++){
                tokens[i][j] = null;
                tokens[i][j] = null;
            }
        }
        players.get(playerName).setTokenMatrix(tokens);
    }


    /**
     * 
     * @param placeToX
     * @param placeToY
     * @param tokenType
     * @throws InvocationTargetException
     */
    public void play(int placeToX, int placeToY, String tokenType) throws InvocationTargetException{
        lastPositionTokens = null;
        String playerName = returnTurn();
        updateTicks();
        calculateLastPositionTokens(placeToX, placeToY);
        updateTokens();
        addToken(tokenType, playerName, new int[]{placeToX, placeToY});
        stopPlayerTimer();
        verifier.winner(placeToX, placeToY, dimension, players.get(turn).returnTokenMatrix());
        if(!verifier.getGomokuFinished()){
            nextTurn();
            startPlayerTimer();
            cellsMissing--;
        }
    }



	public String returnWinner(){
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
     */
    public void startPlayerTimer(){
        players.get(turn).startTime();
    }
    
    /**
     * Stops the player's timer
     */
    public void stopPlayerTimer(){
        players.get(turn).endTime();
    }

    /**
     * Returns the total time of a player.
     * @param playerName
     * @return
     */
    public int getPlayerTotalTime(String playerName){
        return players.get(playerName).getTotalTime();
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
     * Returns the name of the player's that has the turn to play.
     * @return
     */
    public String returnTurn(){
        return turn;
    }

    private Color lastColor;

    /**
     * Creates a new instance of Machine
     * @param type
     * @return
     * @throws java.lang.reflect.InvocationTargetException
     */
    public void addToken(String tokenType, String playerName, int[] position) throws java.lang.reflect.InvocationTargetException{
        try{
            Class<?> tokenChilds = Class.forName("domain."+tokenType+"Token");
            Constructor<?> constructorTokenChilds = tokenChilds.getConstructor(Color.class, int[].class, Player.class, Gomoku.class);
            Player player = players.get(playerName);
            Token token = (Token) constructorTokenChilds.newInstance(player.getColor(), position, player, this);
            lastColor = player.getColor();
            player.setToken(token, position[0], position[1], tokenType);
            tokenMatrix[position[0]][position[1]] = token;
            tokens.add(token);
        } catch(ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException e){
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
     * Returns the gameMode of gomoku
     * @return gameMode
     */
    public String getOpponent(){
        return opponent;
    }

    /**
     * Sets the gameMode of Gomoku
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
    public void createRivals() throws InvocationTargetException{
        if(opponent == "pvp"){
            players.put(nameP1, new Human(nameP1));
            players.put(nameP2, new Human(nameP2));
            players.get(nameP1).setQuantityTokens(dimension * dimension);
            players.get(nameP2).setQuantityTokens(dimension * dimension);
        }else if(gameMode == "pve"){ 
            players.put(nameP1, new Human(nameP1));
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
    public Machine createMachine(String type) throws java.lang.reflect.InvocationTargetException{
        Machine machine;
        try{
            Class<?> machineChilds = Class.forName("domain."+type+"Machine");
            Constructor<?> constructorMachineChilds = machineChilds.getConstructor();
            machine = (Machine) constructorMachineChilds.newInstance();
        } catch(ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException e){
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
    public void setColor(String jugador, Color color) throws GomokuException{
        Player player = players.get(jugador);
        if(player != null){
            player.setColor(color);
        }else{
           throw new GomokuException(GomokuException.PLAYER_NOT_FOUND);
        }
    }

    /**
     * Return the player's color.
     * @param jugador
     * @return colorString
     */
    public String getColor(String jugador){
        Player player = players.get(jugador);
        Color color = player.getColor();
        String colorString = String.format("#%06X", (0xFFFFFF & color.getRGB()));
        return colorString;
    }
    

    /**
     * Reuturn the last color used.
     * @return
     */
    public Color getColor(){
        return lastColor;
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
    
    /**
     * Changes the type of token that's being used, based on the players desition.
     * @param typeOfToken
     */
    public void changeTypeOfToken(String typeOfToken){
        players.get(turn).changeToken(typeOfToken);
    }

    /**
     * Returns the selected token of the player on turn.
     * @return
     */
    public String getCurrentToken(){
        return players.get(turn).getToken();
    }

    /**
     * Returns the quantity of tokens left.
     * @param player
     * @param typeOfToken
     * @return
     */
    public int getTokensLeft(String player, String typeOfToken){
        int cantidad = players.get(player).getTokensLeft(typeOfToken);
        return cantidad;
    }

    //Nuevos metodos.

    public int getTicks(){
        return ticks;
    }

    
    private ArrayList<int[]> lastPositionTokens;

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
    public void calculateLastPositionTokens(int placeToX, int placeToY){
        ArrayList<int[]> positionOfTokens = new ArrayList<>();
        positionOfTokens.add(new int[]{placeToX, placeToY});
        for(Token t: tokens){
            if(t.getIdentifier() == 'D'){
                positionOfTokens.add(t.getPosition());
            }
        }
        this.lastPositionTokens = positionOfTokens;
        for(int[] l: lastPositionTokens) {
        	for(int i: l) {
        		System.out.print(i + " ");
        	}
        	System.out.println();
        }
    }
    

    /**
     * 
     */
    public void updateTicks(){
        for(Token t: tokens){
            if(t instanceof TemporalToken){
                ((TemporalToken)t).updateTicks();
            }
        }
    }

    private void deleteToken(Token token){
        int[] position = token.getPosition();
        this.tokenMatrix[position[0]][position[1]] = null;
        players.get(token.getNameOfPlayer()).deleteToken(position[0], position[1]);
    }

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


    public Token getToken(int xPos, int yPos){
        return tokenMatrix[xPos][yPos];
    }
    
    public int getDimension() {
    	return dimension;
    }

	public Token[][] getTokenMatrix() {

		return tokenMatrix;
	}
	
	
    public void setTypeOfTokens() {
    	this.typeOfTokens.add("Normal");
    	this.typeOfTokens.add("Heavy");
    	this.typeOfTokens.add("Temporal");
    }
    
    public void createTokensToUse(String playerName) {
    	Random random = new Random();
    	ArrayList<String> tokens = new ArrayList<>(); 
    	int percentage = random.nextInt(11) + 20;
    	if(gameMode.equals("normal") || gameMode.equals("quicktime")) {
    		createTokensToUse(playerName, percentage, random, tokens);
    	}
    	else if(gameMode.equals("piedrasLimitadas")) {
    		createTokensToUse(playerName, tokens);
    	}
    	else {
    		createTokensToUse(playerName, tokens);
    	}
    }
    
    public void createTokensToUse(String playerName, ArrayList<String> tokens) {
		int quantityOfTokens = (dimension * dimension) / 2;
		for(int i = 0; i < quantityOfTokens; i++) {
			tokens.add(typeOfTokens.get(0));
		}
		players.get(playerName).setTokensToUse(tokens);
    	
    }
    
    public void createTokensToUse(String playerName, int percentage, Random random, ArrayList<String> tokens) {
		int quantityOfTokens = (dimension * dimension) / 2;
		for(int i = 0; i < quantityOfTokens - percentage; i++) {
			tokens.add(typeOfTokens.get(0));
		}
		for(int i = quantityOfTokens - percentage; i < quantityOfTokens; i++) {
			tokens.add(typeOfTokens.get(random.nextInt(2) + 1));
		}
		Collections.shuffle(tokens);
		players.get(playerName).setTokensToUse(tokens);
    	
    }
    

    /**
     * Creates the important elements of the game and starts the first player timer.
     */
    public void startGame(){
        turn = nameP1;
        createBoards();
        startPlayerTimer();
        setTypeOfTokens();
        createTokensToUse(nameP1);
        createTokensToUse(nameP2);
    }
}
