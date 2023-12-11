package presentation;

import java.awt.event.*;
import java.lang.reflect.InvocationTargetException;
import java.awt.*;
import javax.swing.*;

import domain.Gomoku;
import domain.GomokuException;
import domain.Log;

public class GameModes extends JPanel{
    private static GameModes gameModes = null;

    /**
     * Creates an instansce of Gomoku
     */
    private GameModes() {
        this.setOpaque(true);
       
    }

    public static GameModes getGameModes() {
        if (gameModes == null) {
            gameModes = new GameModes();
            gameModes.prepareElements();
            
        }
        return gameModes;
    }

    public void setInvisible(){
        this.setVisible(false);
    }

    public void setVisible(){
        this.setVisible(true);
    }


    private JButton normal;
    private JButton quickTime;
    private JButton limitedTokens;
    
    public void prepareElements(){
        gameModes.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS)); // Establece BoxLayout como layout del panel
    
        gameModes.setBackground(new Color(0, 0, 0, 35));
    
        normal = new JButton("Normal");
        quickTime = new JButton("QuickTime");
        limitedTokens = new JButton("Piedras limitadas");
    
        prepareActions();
    
        // Centra los botones horizontalmente
        gameModes.setAlignmentX(Component.CENTER_ALIGNMENT);
    
        // Centra los botones verticalmente
        gameModes.setAlignmentY(Component.CENTER_ALIGNMENT);
    
        int gapSize = 20; // Ajusta el espacio entre los botones (píxeles)
        Dimension buttonSize = new Dimension(200, 40); // Ajusta el tamaño de los botones
    
        gameModes.add(Box.createVerticalStrut(gapSize)); // Agrega espacio vertical entre los botones
        setButtonAttributes(normal, buttonSize);
        gameModes.add(normal);
    
        gameModes.add(Box.createVerticalStrut(gapSize));
        setButtonAttributes(quickTime, buttonSize);
        gameModes.add(quickTime);
    
        gameModes.add(Box.createVerticalStrut(gapSize));
        setButtonAttributes(limitedTokens, buttonSize);
        gameModes.add(limitedTokens);
    }
    
    private void setButtonAttributes(JButton button, Dimension size) {
        button.setPreferredSize(size);
        button.setMaximumSize(size);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
    }
    
    private void prepareActions(){
        normal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                try {
                    GomokuGUI.changeColor();
                    Gomoku.getGomoku().setGameMode("normal");
                    Gomoku.getGomoku().startGame();
                    GomokuGUI.normalGameSelected();
                } catch (GomokuException e1) {
                    Log.record(e1);
                }
            }});
        
        quickTime.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
            	try {
            		GomokuGUI.askForTime();
					GomokuGUI.changeColor();
					Gomoku.getGomoku().setGameMode("normal");
	                Gomoku.getGomoku().startGame();
	                GomokuGUI.quickTimeGameSelected();
				} catch (GomokuException e1) {
					// TODO Auto-generated catch block
					Log.record(e1);
				}
                
            }});

        limitedTokens.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                
            }});
    }
    
}   
