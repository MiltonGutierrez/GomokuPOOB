package domain;

import java.awt.event.ActionEvent;

import javax.management.BadStringOperationException;

public class NormalBox extends Box{
	
	public NormalBox() {
		super();
	}

	@Override
	protected void act() {
		
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
