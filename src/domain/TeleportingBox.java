/**
 * 
 */
package domain;

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
	 * 
	 */
	@Override
	public void setToken(Token token) {
		this.token = token;
		act();
	}

	@Override
	protected void act() {
		setBackground(super.token.getColor());
        setBorderColor(colorWithToken);
        setTextInBox("" + token.getIdentifier());
	}
	
	@Override
	public void deleteToken() {
		if(this.token != null) {
			this.token = null;
	    	this.setBackground(this.backgroundColor);
	    	updateAppearance();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

}
