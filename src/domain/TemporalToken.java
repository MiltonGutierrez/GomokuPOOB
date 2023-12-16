package domain;
import java.awt.Color;
public class TemporalToken extends Token{
    /**
     * Constructor for TemporalToken
     * @param color is the color of the token
     * @param position is the position of the token
     * @param player is the player who owns the token
     * @param gomoku is the board
     */
    public TemporalToken(Color color, int[] position, Player player, Gomoku gomoku){
        super(color, position, player, gomoku);
        this.value = 1;
        //Nuevos metodos.
        super.identifier = 'T';
    }

    /**
     * Updates the ticks the TemporalToken has been on the board
     */
    public void updateTicks(){
        ticksOnBoard++;
        if(ticksOnBoard == 3){
            identifier = 'D';
        }
    }
}
