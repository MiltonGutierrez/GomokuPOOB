package domain;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Timer;
public class FearfulMachine extends Machine {

    private String name;
    private Color color;

    public FearfulMachine(String name, Gomoku gomoku){
    	super(name, gomoku);
        times.put("timeT", new TimePassed(0));
        times.put("timeM", new TimePassed(0));
        Timer timerTotal = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarTiempo();
            }
			private void actualizarTiempo() {
			}
        });
        Timer timerMissing = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarTiempo();
            }
			private void actualizarTiempo() {
			}
        });
		timers.put("timerT", timerTotal);
		timers.put("timerM", timerMissing);
        
    }
    
    

	@Override
	public void setToken(Token token, int xPos, int yPos) {
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
	/**
     * Returns the timer of the player
     */
    public javax.swing.Timer getTimer(String timer) {
    	return timers.get(timer);
    }

	
	/**
     * Set color of tokens
     * @param color: color of the tokens
     */
	@Override
    public void setColor(Color color){
        this.color = color;
    }

	@Override
	public Color getColor() {
		return color;
	}

	@Override
	public void addTokenToUse(String tokenType) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setPuntuacion(int score_) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getPuntuacion() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int tokensLeft() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void resetAll() {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void getTurn() {
		// TODO Auto-generated method stub
		
	}
    




}