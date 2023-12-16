/**
 * 
 */
package domain;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.util.Random;

/**
 * 
 */
public class GoldenBox extends Box{
	private Random random;
	private Color colorWithToken = new Color(255,222,0);
	/**
	 * Creates an instance of GoldenBox
	 */
	public GoldenBox(int[] position) {
		super(position);
		this.random = new Random();
	}
	
	/**
	 * Sets the token in the box
	 */
	@Override
	public void setToken(Token token) {
		this.token = token;
		act();
	}
	
	/**
	 * Is the behavior of the box
	 */
	@Override
	protected void act() {
		super.setBackgroundColor(super.token.getColor());
        this.setBorderColor(colorWithToken);
        this.setTextInBox("" + token.getIdentifier());
        int index = random.nextInt(2) + 1;
        String tokenType = Gomoku.getGomoku().getTypeOfTokens().get(index);
        try {
			Gomoku.getGomoku().loadPlayer(Gomoku.getGomoku().getTurn()).addTokenToUse(tokenType);
		} catch (GomokuException e) {
			Log.record(e);
		}
		
	}
	
	/**
	 * Deletes a token
	 */
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

	/**
	 * Behavior of the box when clicked
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

}
