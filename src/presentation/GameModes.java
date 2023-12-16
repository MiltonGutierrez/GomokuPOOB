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
    private JButton normal;
    private JButton quickTime;
    private JButton limitedTokens;
    

    /**
     * Creates an instansce of GameMode
     */
    private GameModes() {
        this.setOpaque(true);
        this.setVisible(true);
    }
    
    /**
     * Returns the panel and creates it if null
     * @return GameModes panel
     */
    public static GameModes getGameModes() {
        if (gameModes == null) {
            gameModes = new GameModes();
            gameModes.prepareElements();
            
        }
        return gameModes;
    }

    /**
     * Makes invisible the panel
     */
    public void setInvisible(){
        this.setVisible(false);
        gameModes.repaint();
    }
    
    /**
     * Makes visible the panel
     */
    public void setVisible(){
        this.setVisible(true);
        gameModes.repaint();
    }


    
    /**
     * Prepares all the elements of the board
     */
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
    
    /**
     * Sets the configuration of the buttons
     * @param button is the button to set
     * @param size is the size of the button
     */
    private void setButtonAttributes(JButton button, Dimension size) {
        button.setPreferredSize(size);
        button.setMaximumSize(size);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
    }
    
    /**
     * Sets the percentage of special boxes
     */
    public void setSpecialBoxesPercentage() {
        boolean flag = false;
        while (!flag) {
            try {
                String input = JOptionPane.showInputDialog("Ingrese el porcentaje de casillas especiales");

                if (input == null) {
                    continue; 
                }
                int boxPercentage = Integer.parseInt(input);
                if(boxPercentage < 0) {
                	boxPercentage = 0;
                }else if(boxPercentage >100) {
                	boxPercentage = 100;
                }
                Gomoku.getGomoku().setBoxPercentage(boxPercentage);
                flag = true;
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Por favor, ingrese un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception e) {
                Log.record(e);
            }
        }
    }

    
    /**
     * Sets the percentage of special tokens
     */
    public void setSpecialTokensPercentage() {
    	boolean flag = false;
    	while(!flag) {
        	try{
        		String input = JOptionPane.showInputDialog("Ingrese el porcentage de tokens especiales");
        		if (input == null) {
                    continue; 
                }
        		int tokenPercentage = Integer.parseInt(input);
        		if(tokenPercentage < 0) {
        			tokenPercentage = 0;
                }else if(tokenPercentage >100) {
                	tokenPercentage = 100;
                }
        		Gomoku.getGomoku().setTokenPercentage(tokenPercentage);
                flag = true;
        	} catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Por favor, ingrese un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception e) {
                Log.record(e);
            }
    	}
    }
    
    /**
     * Prepares the listeners of the buttons
     */
    private void prepareActions(){
        normal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                try {
                    GomokuGUI.changeColor();
                    setSpecialBoxesPercentage();
                    setSpecialTokensPercentage();
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
                    setSpecialTokensPercentage();
					Gomoku.getGomoku().setGameMode("quicktime");
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
            	try {
					GomokuGUI.changeColor();
					Gomoku.getGomoku().setGameMode("limited");
					setSpecialBoxesPercentage();
	                Gomoku.getGomoku().startGame();
	                GomokuGUI.limitedGameSelected();
				} catch (GomokuException e1) {
					// TODO Auto-generated catch block
					Log.record(e1);
				}
            }});
    }
    
}   
