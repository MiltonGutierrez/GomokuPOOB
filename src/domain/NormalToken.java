package domain;
import java.awt.Color;
public class NormalToken extends Token {
    public NormalToken(Color color, int[] position, Player player, Gomoku gomoku ){
        super(color, position, player, gomoku);
        super.value = 1;
        super.identifier = 'N';
        //Nuevos metodos.
    }
    //Nuevos metodos.

	/**
	 * 
	 */
	public void updateTicks() {
		super.ticksOnBoard++;
	}

}
