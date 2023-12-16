package domain;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Timer;
public class FearfulMachine extends Machine {

    private String name;
    private Color color;

    public FearfulMachine(String name, Gomoku gomoku){
    	super(name, gomoku);
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
    
    

    @Override
    /**
     * Gets the last token of gomoku
     */
	public void getTurn() {
    	int[] ultimaFichaOponente = Gomoku.getGomoku().returnLastPositionToken();
    	int dimensiones = Gomoku.getGomoku().getDimension();
    	int xPosLast = ultimaFichaOponente[0];
    	int yPosLast = ultimaFichaOponente[1];
    	try {
    		System.out.println("Voy a judar\n"+ xPosLast + "\n" + yPosLast);
			play(xPosLast, yPosLast, dimensiones);
		} catch (GomokuException e) {
			Log.record(e);
		}
	}
    
    /**
     * Plays placing tokens next to the last token of the player
     * @param xPosLast x coordinate of the last token
     * @param yPosLast y coordinate of the last token
     * @param dimensiones is the dimension of the board
     * @throws GomokuException
     */
    public void play(int xPosLast, int yPosLast, int dimensiones) throws GomokuException {
    	if(!(xPosLast + 3 >= dimensiones) && Gomoku.getGomoku().validPlay(xPosLast + 3, yPosLast)) {
    		Gomoku.getGomoku().play(xPosLast + 3, yPosLast);
    	}else if(!(xPosLast - 3 < 0) && Gomoku.getGomoku().validPlay(xPosLast - 3, yPosLast)) {
    		Gomoku.getGomoku().play(xPosLast - 3, yPosLast);
    	}else if(!(yPosLast - 3 < 0) && Gomoku.getGomoku().validPlay(xPosLast, yPosLast - 3)){
    		Gomoku.getGomoku().play(xPosLast, yPosLast-3);
    	}else if(!(yPosLast + 3 >= dimensiones) && Gomoku.getGomoku().validPlay(xPosLast, yPosLast + 3)) {
    		Gomoku.getGomoku().play(xPosLast, yPosLast+3);
    	}
    }


	@Override
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

	@Override
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

	@Override
	/**
	 * Returns the time that has passed between turns
	 */
	public TimePassed getTime(String time) {
		return this.times.get(time);
	}

	@Override
	/**
     * Sets the token matrix for the player.
     * @param matrix: Token matrix
     */
    public void setTokenMatrix(Token[][] matrix){
        this.tokenMatrix = matrix;
    }

	@Override
	/**
     * Returns the token matrix of the player
     */
    public Token[][] returnTokenMatrix(){
        return tokenMatrix;
    }
    

	@Override
	/**
     * Return the name of the player.
     */
    public String getName(){
        return name;
    }

	@Override
	/**
     * Deletes the token in the tokenMatrix and the arrayList tokens
     * @param xPos x position to delete
     * @param yPos y position to delete
     */
    public void deleteToken(int xPos, int yPos){
    	this.tokens.remove(this.tokenMatrix[xPos][yPos]);
        tokenMatrix[xPos][yPos] = null;
    }

	@Override
	/**
	 * Sets the arraylist containing string that determine the tokens the player's going to use.
	 * @param tokens are the tokens of the player
	 */
	public void setTokensToUse(ArrayList<String> tokens) {
		tokensToUse = tokens;
	}

	@Override
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
			try {
				gomoku.createTokensToUse(this.name);
			} catch (GomokuException e) {
				Log.record(e);
			} // evitamos que se quede sin fichas.
		}
		else if(tokensToUse.size() == 0 && gameMode.equals("limited")) {
			return null;
		}
		return tokenType;
	}

	@Override
	/**
	 * Sets the time left
	 */
	public void setTime(Integer time) {
		times.get("timeM").setTime(time);
		
	}

	@Override
	/**
	 * Validates the time left
	 */
	public boolean validateTime() {
		return times.get("timeM").getTime() <= 0;
	}

	@Override
	/**
	 * Returns the tokens left to use by the player.
	 */
	public int getTokensLeft() {
		return this.tokensToUse.size();
	}

	@Override
	/**
     * Returns the timer of the player
     */
    public javax.swing.Timer getTimer(String timer) {
    	return timers.get(timer);
    }

	
	/**
     * Set color of tokens
     * @param color: color of the tokens
     */
	@Override
    public void setColor(Color color){
        this.color = color;
    }

	@Override
	public Color getColor() {
		return color;
	}

	/**
	 * Adds a token to the player
	 * @param tokenType is the type of token to add
	 */
	@Override
	public void addTokenToUse(String tokenType) {
		this.tokensToUse.add(0, tokenType);
	}

	@Override
	/**
	 * Adds the score
	 * @Score is the score to add or substract to the player
	 */
	public void setPuntuacion(int score_) {
		int sumar = score_;
		this.score += sumar;
	}
	

	@Override
	/**
	 * Returns the current score of the player
	 */
	public int getPuntuacion() {
		return score;
	}

	@Override
	/**
	 * Returns the tokens left to use
	 * @return
	 */
	public int tokensLeft() {
		return tokensToUse.size();
	}

	@Override
	public void resetAll() {
		// TODO Auto-generated method stub
		
	}

	@Override
	/**
     * Sets the token in the respective lists and matrixes
     */
    public void setToken(Token token, int xPos, int yPos){
        tokens.add(token);
        tokenMatrix[xPos][yPos] = token;
    }

	
    




}