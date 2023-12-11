package domain;
import java.awt.Color;
import java.util.ArrayList;

public class ExpertMachine extends Machine{
    private String name;
    private Color color;

    public ExpertMachine(String name){
        this.name = name;
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
	public void calculateTotalTime() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getTotalTime() {
		// TODO Auto-generated method stub
		return 0;
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

}


