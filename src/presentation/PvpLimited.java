package presentation;

import java.awt.*;

import javax.swing.*;

import domain.Gomoku;

public class PvpLimited extends PvpNormal{
	private static PvpLimited pvpLimited = null;

	private PvpLimited() {
		this.setOpaque(true);
	}
	
	public static PvpLimited getPvpLimited() {
        if (pvpLimited == null) {
            pvpLimited = new PvpLimited();
            pvpLimited.prepareElementsGameBoardPVP();
        }
        return pvpLimited;
    }
	private JPanel tokensLeft;
	private JPanel gameWrapper;
	/**
     * Prepares the elements of the board
     *  
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
	
	public JPanel createBoardTokenInformation() {
        tokensLeft = new JPanel();
        tokensLeft.setLayout(new GridBagLayout());
        tokensLeft.setBackground(new Color(166, 220, 242));
        tokensLeft.setPreferredSize(new Dimension(200, 200));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(0, 10, 0, 0);
        Font arial = new Font("italic", 1, 14);
        JLabel fichasDisponiblesP1 = new JLabel("Fichas Restantes " + GomokuGUI.returnP1());
        fichasDisponiblesP1.setFont(arial);
        tokensLeft.add(fichasDisponiblesP1, gbc);
        gbc.gridy = 1;
        tokensLeft.add(Box.createVerticalStrut(5), gbc);
        gbc.gridy = 2;
        JLabel normalTokenLeftP1 = new JLabel("Piedras normales restantes: " + "Gomoku.getGomoku().getTokensLeft(GomokuGUI.returnP1(), \"Normal\")");
        normalTokenLeftP1.setFont(arial);
        tokensLeft.add(normalTokenLeftP1, gbc);
        gbc.gridy = 3;
        JLabel heavyTokenLeftP1 = new JLabel("Piedras pesadas restantes: " + "Gomoku.getGomoku().getTokensLeft(GomokuGUI.returnP1(), \"Heavy\")");
        heavyTokenLeftP1.setFont(arial);
        tokensLeft.add(heavyTokenLeftP1, gbc);
    
        gbc.gridy = 4;
        JLabel temporalTokenLeftP1 = new JLabel("Piedras temporales restantes: " + "Gomoku.getGomoku().getTokensLeft(GomokuGUI.returnP1(), \"Temporal\")");
        temporalTokenLeftP1.setFont(arial);
        tokensLeft.add(temporalTokenLeftP1, gbc);
    
        gbc.gridy = 5;
        tokensLeft.add(Box.createVerticalStrut(5), gbc);
    
        gbc.gridy = 6;
        JLabel fichasDisponiblesP2 = new JLabel("Fichas Restantes " + GomokuGUI.returnP2());
        fichasDisponiblesP2.setFont(arial);
        tokensLeft.add(fichasDisponiblesP2, gbc);
    
        gbc.gridy = 7;
        tokensLeft.add(Box.createVerticalStrut(5), gbc);
        gbc.gridy = 8;
        JLabel normalTokenLeftP2 = new JLabel("Piedras normales restantes: " + "Gomoku.getGomoku().getTokensLeft(GomokuGUI.returnP2(), \"Normal\")");
        normalTokenLeftP2.setFont(arial);
        tokensLeft.add(normalTokenLeftP2, gbc);
        gbc.gridy = 9;
        JLabel heavyTokenLeftP2 = new JLabel("Piedras pesadas restantes: " + "Gomoku.getGomoku().getTokensLeft(GomokuGUI.returnP2(), \"Heavy\")");
        heavyTokenLeftP2.setFont(arial);
        tokensLeft.add(heavyTokenLeftP2, gbc);
        gbc.gridy = 10;
        JLabel temporalTokenLeftP2 = new JLabel("Piedras temporales restantes: " + "Gomoku.getGomoku().getTokensLeft(GomokuGUI.returnP2(), \"Temporal\")");
        temporalTokenLeftP2.setFont(arial);
        tokensLeft.add(temporalTokenLeftP2, gbc);
        
        return tokensLeft;
    }
	
	@Override
    public void refreshTime(){
    	tokensLeft.removeAll();
        gameWrapper.remove(tokensLeft);
        tokensLeft = createBoardTokenInformation();
        informationPanel.remove(puntuacionJugador1);
        informationPanel.remove(puntuacionJugador2);
        gameWrapper.add(tokensLeft);
        boardGame.validate();
        boardGame.repaint();
    }
	
	
}
