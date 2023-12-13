/**
 * 
 */
package domain;

import java.awt.Color;

/**
 * 
 */
public class GoldenBox extends Box{
	
	private Color colorWithToken = new Color(255,222,0,125);
	/**
	 * Creates an instance of GoldenBox
	 */
	public GoldenBox() {
		super();
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
        this.setBorderColor(colorWithToken);
        this.setTextInBox("" + token.getIdentifier());
		
	}

	@Override
	public void deleteToken() {
		this.token = null;
    	this.setBackground(this.backgroundColor);
    	updateAppearance();
	}

}
