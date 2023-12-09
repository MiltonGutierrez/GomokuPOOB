package domain;
import java.awt.Color;
public abstract class Token{
    protected Color color;
    protected int[] position;
    protected Player player;
    protected int value;
    //Nuevos metodos.
    protected Gomoku gomoku;
    protected char identifier;
    protected int ticksOnBoard;

    public Token(Color color, int[] position, Player player, Gomoku gomoku){
        this.color = color;
        this.position = position;
        this.player = player;
        this.gomoku = gomoku;
        this.ticksOnBoard = 0;
    }

    /**
     * 
     * @return position
     */
    public int[] getPosition(){
        return position;
    }

    /**
     * 
     * @return
     */
    public int getValue(){
        return value;
    }

    //nuevos metodos

    public char getIdentifier(){
        return identifier;
    }

    public String getNameOfPlayer(){
        return player.getName();
    }

    public Color getColor(){
        return color;
    }
    
    public abstract void updateTicks();
}