/**
 * 
 */
package domain;

import java.awt.Color;
import java.awt.event.ActionEvent;

/**
 * 
 */
public class GoldenBox extends Box{
	
	private Color colorWithToken = new Color(255,222,0);
	/**
	 * Creates an instance of GoldenBox
	 */
	public GoldenBox(int[] position) {
		super(position);
	}
	
	/**
	 * 
	 */
	@Override
	public void setToken(Token token) {
		this.token = token;
		act();
	}
	
	@Override
	protected void act() {
		super.setBackgroundColor(super.token.getColor());
        this.setBorderColor(colorWithToken);
        this.setTextInBox("" + token.getIdentifier());
		
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
