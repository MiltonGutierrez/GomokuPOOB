package domain;
import java.awt.Color;
public class NormalToken extends Token {
	
	/**
	 * Is the constructor for the NormalToken
	 * @param color is the color of the token
	 * @param position is the position of the token
	 * @param player is the player who owns this token
	 * @param gomoku is the board to place
	 */
    public NormalToken(Color color, int[] position, Player player, Gomoku gomoku ){
        super(color, position, player, gomoku);
        super.value = 1;
        super.identifier = 'N';
    }

	/**
	 * Updates the ticks of the token
	 */
	public void updateTicks() {
		super.ticksOnBoard++;
	}

}
