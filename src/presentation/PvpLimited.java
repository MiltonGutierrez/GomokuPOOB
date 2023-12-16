package presentation;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import domain.Gomoku;
import domain.GomokuException;
import domain.Log;

public class PvpLimited extends PvpNormal{
	private static PvpLimited pvpLimited = null;
	private JPanel tokensLeft;
	private JPanel gameWrapper;
	
	/**
	 * Constructor for panel PvpLimited
	 */
	private PvpLimited() {
		this.setOpaque(true);
	}
	
	/**
	 * Returns the panel and creates one if null
	 * @return is the panel for PvpLimited
	 * @throws GomokuException
	 */
	public static PvpLimited getPvpLimited(){
        if (pvpLimited == null) {
            pvpLimited = new PvpLimited();
            pvpLimited.prepareElementsGameBoardPVP();
        }
        return pvpLimited;
    }
	
	/**
     * Prepares the elements of the board
     */
	@Override
    public void prepareElementsGame() {
        boardGame.setLayout(new BorderLayout());
        boardGame.setSize(this.getWidth() - gameOptions.getWidth(), this.getHeight());
        boardGame.setBackground(new Color(113, 197, 232,128));
    
        game = createBoardGame();
		tokensLeft = createBoardTokenInformation();

        // Create a panel to hold the game component in the center
        gameWrapper = new JPanel();
        gameWrapper.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 30));
        gameWrapper.setBackground(new Color(113, 197, 232,128));
        gameWrapper.add(game);
        gameWrapper.add(tokensLeft);
        
        boardGame.add(gameWrapper, BorderLayout.CENTER);
        informationPanel.remove(puntuacionJugador1);
        informationPanel.remove(puntuacionJugador2);
        boardGame.validate();
        boardGame.repaint();
    }
	
	/**
	 * Creates the information for the tokens left for each player
	 * @return a panel with the information of the tokens
	 * @throws GomokuException 
	 */
	public JPanel createBoardTokenInformation(){
        tokensLeft = new JPanel();
        tokensLeft.setLayout(new GridBagLayout());
        tokensLeft.setBackground(new Color(166, 220, 242));
        tokensLeft.setPreferredSize(new Dimension(350, 350));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(0, 10, 0, 0);
        Font arial = new Font("italic", 1, 14);
        JLabel fichasDisponiblesP1 = new JLabel("Piedras Restantes para el jugador: " + Gomoku.getGomoku().getP1());
        fichasDisponiblesP1.setFont(arial);
        tokensLeft.add(fichasDisponiblesP1, gbc);
        gbc.gridy = 1;
        tokensLeft.add(Box.createVerticalStrut(5), gbc);
        gbc.gridy = 2;
        JLabel normalTokenLeftP1;
		try {
			normalTokenLeftP1 = new JLabel("Piedras restantes " + Gomoku.getGomoku().getTokensLeft(Gomoku.getGomoku().getP1()));
	        normalTokenLeftP1.setFont(arial);
	        tokensLeft.add(normalTokenLeftP1, gbc);
		} catch (GomokuException e) {
			Log.record(e);
			JOptionPane.showMessageDialog(null, e, "Advertencia", JOptionPane.INFORMATION_MESSAGE);
		}
        gbc.gridy = 5;
        tokensLeft.add(Box.createVerticalStrut(5), gbc);
    
        gbc.gridy = 6;
        JLabel fichasDisponiblesP2 = new JLabel("Fichas Restantes para el jugador: " + Gomoku.getGomoku().getP2());
        fichasDisponiblesP2.setFont(arial);
        tokensLeft.add(fichasDisponiblesP2, gbc);
    
        gbc.gridy = 7;
        tokensLeft.add(Box.createVerticalStrut(5), gbc);
        gbc.gridy = 8;
        JLabel normalTokenLeftP2;
		try {
			normalTokenLeftP2 = new JLabel("Piedras restantes " + Gomoku.getGomoku().getTokensLeft(Gomoku.getGomoku().getP2()));
	        normalTokenLeftP2.setFont(arial);
	        tokensLeft.add(normalTokenLeftP2, gbc);
		} catch (GomokuException e) {
			Log.record(e);
			JOptionPane.showMessageDialog(null, e, "Advertencia", JOptionPane.INFORMATION_MESSAGE);
		}

        
        return tokensLeft;
    }
	
	/**
	 * Refresh the time for each player
	 */
	@Override
    public void refreshTime(){
    	tokensLeft.removeAll();
        gameWrapper.remove(tokensLeft);
		tokensLeft = createBoardTokenInformation();
        gameWrapper.add(tokensLeft);
        boardGame.validate();
        boardGame.repaint();
    }
	
	
	/*
     * Actions for the finish button
     */
    @Override
    public void finishOption(){
    	this.removeAll();
        GomokuGUI.finishButtonLimited();
        pvpLimited = null;
    }
	
    /**
     * Makes the panel visible
     */
	protected void setVisible() {
    	this.setVisible(true);
    	pvpLimited.repaint();
    }
    
	/**
	 * Makes invisible the panel
	 */
    protected void setInvisible() {
    	this.setVisible(false);
    	pvpLimited.repaint();
    }
    
    
    
	
	
}
