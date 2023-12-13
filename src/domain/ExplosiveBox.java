package domain;
import java.awt.Color;
import java.awt.event.ActionEvent;
public class ExplosiveBox extends Box{
	
	private Color colorWithToken = new Color(255,0,0);
	
	/**
	 * 
	 */
    public ExplosiveBox(int[] position) {
        super(position);
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
        deleteContigousTokens(Gomoku.getGomoku().getBoxMatrix());
    }
    
    public void deleteContigousTokens(Box[][] matrix) {
    	int filas = matrix.length;
    	int columnas = matrix[0].length;
    	int xPos = this.position[0];
    	int yPos = this.position[1];
    	for (int i = xPos - 1; i <= xPos + 1; i++) {
    		for (int j = yPos - 1; j <= yPos + 1; j++) {
    			if (i >= 0 && i < filas && j >= 0 && j < columnas && !(i == xPos && j == yPos)) {
    				matrix[i][j].deleteToken();
                }
            }
    	}
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