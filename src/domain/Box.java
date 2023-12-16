package domain;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

import presentation.GomokuGUI;

public abstract class Box extends JButton implements ActionListener, Serializable {  
    protected Token token;
    protected Color backgroundColor;
    protected Color borderColor;
    protected int[] position;
    /**
     * Creates an instance of Box|
     */
    public Box(int[] position) {
        super();
        this.token = null; 
        this.backgroundColor = Color.WHITE;
        this.borderColor = Color.BLACK;
        this.setBorder(BorderFactory.createLineBorder(borderColor, 2));
        this.setBackground(backgroundColor);
        this.position = position;
        updateAppearance();
    }
    
    /**
     * Set the token into the box
     * @param token is the token to set
     */
    public void setToken(Token token) {
        this.token = token;
        act();
        updateAppearance(); 
    }

    /**
     * Return the token of the box
     * @return return the token which is in the box
     */
    public Token getToken() {
        return token;
    }
    
    /**
     * Sets the color of the box
     * @param color is the color of the box
     */
    public void setBackgroundColor(Color color) {
        this.backgroundColor = color;
        this.setBackground(color);
        updateAppearance(); 
    }

    /**
     * Returns the color of the box
     * @return is the color of the box
     */
    public Color getBackgroundColor() {
        return backgroundColor;
    }

    /**
     * Place some text into the box
     * @param newText is the text to set
     */
    public void setTextInBox(String newText) {
    	setText(newText);
        updateAppearance(); 
    }

    /**
     * Sets the border color of the box to difference it 
     * @param color is the color of the box
     */
    public void setBorderColor(Color color) {
        this.borderColor = color;
        this.setBorder(BorderFactory.createLineBorder(color, 2));
        updateAppearance(); 
    }
    
    /**
     * Is the behavior of each box
     */
    protected abstract void act();

    /**
     * Delete a token (depends of the type of box)
     */
    public abstract void deleteToken();
    
    /**
     * Updates the appearance of the box
     */
    public void updateAppearance() {
    	this.revalidate();
    	this.repaint();
    }

    /*
     * Creates the box
     */
    private void initialize() {
        addActionListener(this); // Agrega esta instancia como ActionListener al Box
    }
    
    /**
     * Is the action to perform
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        
    }
    
    /**
     * Is the behavior when the boc is clicked
     */
    @Override
    public void doClick() {
        actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, null));
    }
    
    /**
     * Returns the position of the box
     * @return
     */
    public int[] getPosition() {
    	return this.position;
    }
       
}