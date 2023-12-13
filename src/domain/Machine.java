package domain;

import java.awt.Color;
import java.util.ArrayList;

public abstract class Machine implements Player{
    private int puntos;
    private String name;
    private ArrayList<Token> tokens = new ArrayList<>();
    
    
    public Machine(String name) {
    	this.name = name;
    }


    public void setToken(Token token){
        tokens.add(token);
    }
}
