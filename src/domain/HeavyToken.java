package domain;
import java.awt.Color;
public class HeavyToken extends Token {
    private char identifer;
    
    /**
     * Constructor for HeavyToken
     * @param color is the color for the token
     * @param position is the position of the token
     * @param player is the player which owns the token
     * @param gomoku is the board to place
     */
    public HeavyToken(Color color, int[] position, Player player, Gomoku gomoku){
        super(color, position, player, gomoku);
        this.value = 2;
        //Nuevos metodos.
        super.identifier = 'H';

    }

    /**
     * Updates the clicks
     */
	public void updateTicks() {
		super.ticksOnBoard++;
	}

}
