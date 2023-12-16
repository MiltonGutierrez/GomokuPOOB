package domain;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.Timer;

public abstract class Machine implements Player{
	protected String name;
	protected Color color;
	protected ArrayList<Token> tokens = new ArrayList<>();
	protected Token[][] tokenMatrix;
	protected ArrayList<String> tokensToUse = new ArrayList<>();
	protected Gomoku gomoku;
	protected HashMap<String, TimePassed> times = new HashMap<>();
	protected HashMap<String, Timer> timers = new HashMap<>();
	protected int score;

    
    
    public Machine(String name, Gomoku gomoku) {
    	this.name = name;
        this.gomoku = gomoku;
    }


    public void setToken(Token token){
        tokens.add(token);
    }
    
    
}
