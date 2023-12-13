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

	@Override
	protected void act() {
		
	}

	@Override
	public void deleteToken() {
		this.token = null;
    	this.setBackground(this.backgroundColor);
    	updateAppearance();
	}

}
