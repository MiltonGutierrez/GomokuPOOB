package domain;
import java.awt.Color;
import java.awt.event.ActionEvent;
public class ExplosiveBox extends Box{
	
	private Color colorWithToken = new Color(255,0,0);
	
	/**
	 * 
	 */
    public ExplosiveBox() {
        super();
    }
    
    @Override
    public void setToken(Token token) {
        this.token = token;
        act();
        updateAppearance(); 
    }
    
    @Override
	protected void act(){
    	super.setBackground(super.token.getColor());
        this.setBorderColor(colorWithToken);
        this.setTextInBox("" + token.getIdentifier());
    }
    
    @Override
    public void deleteToken() {
    	this.token = null;
    	this.setBackground(this.backgroundColor);
    	updateAppearance();
    }

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

      

}