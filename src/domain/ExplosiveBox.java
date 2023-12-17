package domain;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
public class ExplosiveBox extends Box{
	private Color colorWithToken = new Color(255,0,0);
	
	/**
	 * Constructor for ExplosiveBox
	 */
    public ExplosiveBox(int[] position) {
        super(position);
    }
    
    /**
     * Set the token into the explosive box
     */
    @Override
    public void setToken(Token token) {
        this.token = token;
        act();
        updateAppearance(); 
    }
    
    /**
     * Behavior of the explosive box
     */
    @Override
	protected void act(){
    	super.setBackground(super.token.getColor());
        this.setBorderColor(colorWithToken);
        this.setTextInBox("" + token.getIdentifier());
        deleteContigousTokens(Gomoku.getGomoku().getBoxMatrix());
    }
    
    /**
     * Deletes the tokens around the box
     * @param matrix are the boxes of gomoku
     */
    public void deleteContigousTokens(Box[][] matrix) {
    	int filas = matrix.length;
    	int columnas = matrix[0].length;
    	int xPos = this.position[0];
    	int yPos = this.position[1];
    	for (int i = xPos - 1; i <= xPos + 1; i++) {
    		for (int j = yPos - 1; j <= yPos + 1; j++) {
    			if (i >= 0 && i < filas && j >= 0 && j < columnas && !(i == xPos && j == yPos)) {
    				if(matrix[i][j].getToken() != null) {
        				matrix[i][j].getToken().setIdentifier('D');
        				matrix[i][j].deleteToken();
    				}
                }
            }
    	}
    }
    
    /**
     * Delete a token
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
     * Behavior when clicked
     */
	@Override
	public void actionPerformed(ActionEvent e) {
		
	}
	
	

      

}