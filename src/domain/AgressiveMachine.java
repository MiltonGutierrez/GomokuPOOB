package domain;
import java.awt.Color;
import java.util.ArrayList;

import javax.swing.Timer;
public class AgressiveMachine extends Machine {
    
    private String name;
    private Color color;
    private Token[][] tokenMatrix;
    
    /**
     * 
     * @param name
     */
    public AgressiveMachine(String name){
        super(name);
    }

	@Override
	public void setToken(Token token, int xPos, int yPos, String tokenType) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void startTime() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void stopTime() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public TimePassed getTime(String time) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setTokenMatrix(Token[][] matrix) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Token[][] returnTokenMatrix() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteToken(int xPos, int yPos) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setTokensToUse(ArrayList<String> tokens) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getTokenToUse() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setTime(Integer time) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean validateTime() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getTokensLeft() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Timer getTimer(String timer) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setColor(Color color) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Color getColor() {
		// TODO Auto-generated method stub
		return null;
	}
    




}