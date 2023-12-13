package domain;

import java.awt.Color;

import javax.swing.JButton;

public abstract class Box extends JButton {
    // Atributos privados
    protected Token token;
    protected Color backgroundColor;
    protected Color borderColor;

    // Constructor
    public Box() {
        super();
        this.token = null; 
        this.backgroundColor = Color.WHITE; 
        updateAppearance(); 
    }

    public void setToken(Token token) {
        this.token = token;
        updateAppearance(); 
    }

    public Token getToken() {
        return token;
    }

    public void setBackgroundColor(Color color) {
        this.backgroundColor = color;
        updateAppearance(); 
    }


    public Color getBackgroundColor() {
        return backgroundColor;
    }


    public void setTextInBox(String newText) {
    	setText(newText);
        updateAppearance(); 
    }


    public void setBorderColor(Color color) {
        this.borderColor = color;
        updateAppearance(); 
    }
    
    protected abstract void act();

    public abstract void deleteToken();
    
    public void updateAppearance() {
    	this.revalidate();
    	this.repaint();
    }
       
}