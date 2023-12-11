package presentation;

import java.awt.*;

import javax.swing.*;

import domain.Gomoku;

public class PvpQuick extends PvpNormal {
	private static PvpQuick pvpQuick = null;

	
	private PvpQuick() {
		this.setOpaque(true);
	}
	
	public static PvpNormal getPvpNormal() {
        if (pvpQuick == null) {
            pvpQuick = new PvpQuick();
            pvpQuick.prepareElementsGameBoardPVP();
        }
        return pvpQuick;
    }
	
	
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
        JPanel gameWrapper = new JPanel();
        gameWrapper.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 30));
        gameWrapper.setBackground(new Color(113, 197, 232,128));
        gameWrapper.add(game);
        gameWrapper.add(tokensLeft);

        boardGame.add(gameWrapper, BorderLayout.CENTER);
        
        boardGame.validate();
        boardGame.repaint();
    }
	
	private JButton selectNormal;
	private JButton selectHeavy;
	private JButton selectTemporal;
	
	public JPanel createBoardInformation() {
        information = new JPanel();
        information.setLayout(new GridBagLayout());
        information.setBackground(new Color(166, 220, 242));
    
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(0, 10, 0, 0);
    
        Font arial = new Font("italic", 1, 14);
    
        JLabel fichasDisponibles = new JLabel("Fichas disponibles"); // center
        fichasDisponibles.setFont(arial);
        information.add(fichasDisponibles, gbc);
    
        gbc.gridy = 1;
        information.add(Box.createVerticalStrut(10), gbc);
    
        gbc.gridy = 2;
        selectNormal = new JButton("NORMAL");
        selectNormal.setFont(arial);
        information.add(selectNormal, gbc);
    
        gbc.gridy = 3;
        selectHeavy = new JButton("PESADA");
        selectHeavy.setFont(arial);
        information.add(selectHeavy, gbc);
    
        gbc.gridy = 4;
        selectTemporal = new JButton("TEMPORAL");
        selectTemporal.setFont(arial);
        information.add(selectTemporal, gbc);
        return information;
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
        JLabel fichasDisponiblesP1 = new JLabel("Fichas Restantes ");
        fichasDisponiblesP1.setFont(arial);
        tokensLeft.add(fichasDisponiblesP1, gbc);
        gbc.gridy = 1;
        tokensLeft.add(Box.createVerticalStrut(5), gbc);
        gbc.gridy = 2;
        JLabel normalTokenLeftP1 = new JLabel("Piedras normales restantes: " );
        normalTokenLeftP1.setFont(arial);
        tokensLeft.add(normalTokenLeftP1, gbc);
        gbc.gridy = 3;
        JLabel heavyTokenLeftP1 = new JLabel("Piedras pesadas restantes: ");
        heavyTokenLeftP1.setFont(arial);
        tokensLeft.add(heavyTokenLeftP1, gbc);
    
        gbc.gridy = 4;
        JLabel temporalTokenLeftP1 = new JLabel("Piedras temporales restantes: ");
        temporalTokenLeftP1.setFont(arial);
        tokensLeft.add(temporalTokenLeftP1, gbc);
    
        gbc.gridy = 5;
        tokensLeft.add(Box.createVerticalStrut(5), gbc);
    
        gbc.gridy = 6;
        JLabel fichasDisponiblesP2 = new JLabel("Fichas Restantes ");
        fichasDisponiblesP2.setFont(arial);
        tokensLeft.add(fichasDisponiblesP2, gbc);
    
        gbc.gridy = 7;
        tokensLeft.add(Box.createVerticalStrut(5), gbc);
        gbc.gridy = 8;
        JLabel normalTokenLeftP2 = new JLabel("Piedras normales restantes: ");
        normalTokenLeftP2.setFont(arial);
        tokensLeft.add(normalTokenLeftP2, gbc);
        gbc.gridy = 9;
        JLabel heavyTokenLeftP2 = new JLabel("Piedras pesadas restantes: ");
        heavyTokenLeftP2.setFont(arial);
        tokensLeft.add(heavyTokenLeftP2, gbc);
        gbc.gridy = 10;
        JLabel temporalTokenLeftP2 = new JLabel("Piedras temporales restantes: ");
        temporalTokenLeftP2.setFont(arial);
        tokensLeft.add(temporalTokenLeftP2, gbc);
        
        return tokensLeft;
    }
}
