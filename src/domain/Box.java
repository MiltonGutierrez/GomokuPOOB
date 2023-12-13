package domain;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import presentation.GomokuGUI;

public abstract class Box extends JButton implements ActionListener {  
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
    
    private void initialize() {
        addActionListener(this); // Agrega esta instancia como ActionListener al Box
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
    }

    @Override
    public void doClick() {
        actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, null));
    }
       
}