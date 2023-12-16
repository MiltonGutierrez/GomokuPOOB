/**
 * 
 */
package domain;
import java.util.Random;
import java.awt.Color;
import java.awt.event.ActionEvent;

/**
 * 
 */
public class TeleportingBox extends Box{
	private Color colorWithToken = new Color(0, 255, 17);
	
	/**
	 * Creates an instance of TeleportingBox
	 */
	public TeleportingBox(int[] position) {
		super(position);
	}
	
	/**
	 * Set the token into another box
	 */
	@Override
	public void setToken(Token token) {
		this.token = token;
		act();
	}
	
	/**
	 * Sets the token into a random box
	 */
	@Override
	protected void act() {
        boolean flag = false;
        while(!flag) {
        	int maxValue = Gomoku.getGomoku().getDimension(); 
            int xPos = generateRandomNumber(maxValue);
            int yPos = generateRandomNumber(maxValue);
            int[] newPosition = {xPos, yPos};
            Token newToken = Gomoku.getGomoku().getToken(position[0], position[1]);
            Gomoku.getGomoku().deleteToken(Gomoku.getGomoku().getToken(position[0], position[1]));
            Gomoku.getGomoku().addToken(Gomoku.getGomoku().getTurn(), newToken, newPosition);
            try {
				Gomoku.getGomoku().setPuntuacion(Gomoku.getGomoku().getTurn(), 50);
			} catch (GomokuException e) {
				Log.record(e);
			}
            flag = true;
        }
	}
	
	/**
	 * Generates a random position for the token
	 * @param maxValue is the dimension of the board
	 * @return a random number
	 */
	private static int generateRandomNumber(int maxValue) {
        Random random = new Random();
        return random.nextInt(maxValue);
    }
	
	@Override
	public void deleteToken() {
		if(this.token != null) {
			this.token = null;
	    	this.setBackgroundColor(Color.white);
	    	this.setBorderColor(Color.black);
	    	this.setTextInBox(null);
	    	updateAppearance();
		}

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

}
