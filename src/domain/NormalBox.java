package domain;

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
    	this.token = null;
    }

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
    
}
