/**
 * 
 */
package domain;

import java.awt.Color;

/**
 * 
 */
public class TeleportingBox extends Box{
	
	private Color colorWithToken = new Color(0, 255, 17, 125);
	/**
	 * Creates an instance of TeleportingBox
	 */
	public TeleportingBox() {
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
