package domain;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import presentation.GomokuGUI;

public abstract class Box extends JButton implements ActionListener  {
	protected JButton button;
    protected Token token;
    protected Color backgroundColor;
    protected Color borderColor;

    /**
     * Creates an instance of Box|
     */
    public Box() {
        super();
        this.token = null; 
        this.backgroundColor = Color.WHITE; 
        updateAppearance();
    }

    public void setToken(Token token) {
        this.token = token;
        act();
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
    
    public void addActionListener(ActionListener listener) {
        button.addActionListener(listener);
    }

    public void removeActionListener(ActionListener listener) {
        button.removeActionListener(listener);
    }

    public void doClick() {
        button.doClick();
    }
    
    public void setJButton(JButton button) {
    	this.button = button;
    }
       
}