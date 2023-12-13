package domain;

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
    
}
