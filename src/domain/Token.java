package domain;
import java.awt.Color;
import java.io.Serializable;
public abstract class Token implements Serializable{
    protected Color color;
    protected int[] position;
    protected Player player;
    protected int value;
    //Nuevos metodos.
    protected Gomoku gomoku;
    protected char identifier;
    protected int ticksOnBoard;
    
    /**
     * Basic constructor of token used by its sub-clases
     * @param color
     * @param position
     * @param player
     * @param gomoku
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
     * @return position
     */
    public int[] getPosition(){
        return position;
    }

    /**
     * Returns the value of the token.
     * @return value
     */
    public int getValue(){
        return value;
    }

    //nuevos metodos
    
    /**
     * Returns the char identfier of the token
     * @return identifier
     */
    public char getIdentifier(){
        return identifier;
    }
    
    /**
     * Returns the name of the player the token belongs to.
     * @return
     */
    public String getNameOfPlayer(){
        return player.getName();
    }

    public Color getColor(){
        return color;
    }
    
    public abstract void updateTicks();
    
    public void setIdentifier(char i) {
    	this.identifier = i;
    }
}