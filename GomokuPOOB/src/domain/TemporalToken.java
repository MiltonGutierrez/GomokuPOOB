package domain;
import java.awt.Color;
public class TemporalToken extends Token{
    /**
     * 
     * @param color
     * @param position
     * @param player
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
