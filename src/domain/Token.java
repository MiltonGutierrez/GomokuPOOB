package domain;
import java.awt.Color;
import java.io.Serializable;
public abstract class Token implements Serializable{
    protected Color color;
    protected int[] position;
    protected Player player;
    protected int value;
    protected Gomoku gomoku;
    protected char identifier;
    protected int ticksOnBoard;
    
    /**
     * Basic constructor of token used by its sub-clases
     * @param color is the color of the token
     * @param position is the position of the token
     * @param player is the player who owns this token
     * @param gomoku is the game
     */
    public Token(Color color, int[] position, Player player, Gomoku gomoku){
        this.color = color;
        this.position = position;
        this.player = player;
        this.gomoku = gomoku;
        this.ticksOnBoard = 0;
    }

    /**
     * Returns the array containing the position of the token.
     * @return position is the position of the token
     */
    public int[] getPosition(){
        return position;
    }

    /**
     * Returns the value of the token.
     * @return value is the value of the token
     */
    public int getValue(){
        return value;
    }

    /**
     * Returns the char identfier of the token
     * @return identifier is the first letter of the type of token
     */
    public char getIdentifier(){
        return identifier;
    }
    
    /**
     * Returns the name of the player the token belongs to.
     * @return the name of the owner of the token
     */
    public String getNameOfPlayer(){
        return player.getName();
    }
    
    /**
     * Returns the color of the token
     * @return is the color of the token
     */
    public Color getColor(){
        return color;
    }

    /**
     * Updates the ticks of the token when is necessary
     */
    public abstract void updateTicks();
    
    
    /**
     * Sets the first letter of the token
     * @param i is the first letter of the token
     */
    public void setIdentifier(char i) {
    	this.identifier = i;
    }
}