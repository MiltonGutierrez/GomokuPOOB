package domain;

import java.awt.Color;
import java.awt.event.ActionEvent;

import javax.management.BadStringOperationException;

public class NormalBox extends Box{
	
	/**
	 * Constructor for NormalBox
	 * @param position is the position of the box
	 */
	public NormalBox(int[] position) {
		super(position);
	}
	
	/**
	 * Is the behavior of the box
	 */
	@Override
	protected void act() {
		super.setBackground(super.token.getColor());
		this.setTextInBox("" + token.getIdentifier());
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
     * is the behavior when clicked
     */
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
    
}
