package domain;

import java.awt.Color;
import java.util.ArrayList;

public abstract class Machine implements Player{
    private int puntos;
    private Color color;
    private ArrayList<Token> tokens = new ArrayList<>();
    
    public void setColor(Color color){
        color = Color.WHITE;
    }

    /*
     * Return the color of the player
     */
    public Color getColor(){
        return color;
    }



    public void setToken(Token token){
        tokens.add(token);
    }
}
