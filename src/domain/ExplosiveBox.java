package domain;
import java.awt.Color;
public class ExplosiveBox extends Box{
	
	private Color colorWithToken = new Color(255,0,0,125);
	
	/**
	 * 
	 */
    public ExplosiveBox() {
        super();
    }
    
    @Override
    public void setToken(Token token) {
        this.token = token;
        this.setBorderColor(colorWithToken);
        this.setTextInBox("" + token.getIdentifier());
        act();
        updateAppearance(); 
    }
    
    @Override
	protected void act(){
    	
    }
    
    @Override
    public void deleteToken() {
    	this.token = null;
    	this.setBackground(this.backgroundColor);
    	updateAppearance();
    }

      

}