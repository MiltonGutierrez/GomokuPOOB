package domain;

import java.awt.Color;
import java.awt.event.ActionEvent;

import javax.management.BadStringOperationException;

public class NormalBox extends Box{
	
	public NormalBox(int[] position) {
		super(position);
	}

	@Override
	protected void act() {
		super.setBackground(super.token.getColor());
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
