package domain;
import java.awt.Color;
public class HeavyToken extends Token {
    private char identifer;
    public HeavyToken(Color color, int[] position, Player player, Gomoku gomoku){
        super(color, position, player, gomoku);
        this.value = 2;
        //Nuevos metodos.
        super.identifier = 'H';

    }
    //Nuevos metodos.

}
