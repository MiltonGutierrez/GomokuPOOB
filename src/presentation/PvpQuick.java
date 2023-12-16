package presentation;

import java.awt.*;

import javax.swing.*;

import domain.Gomoku;
import domain.GomokuException;

public class PvpQuick extends PvpNormal {

    private static PvpQuick pvpQuick = null;

    /**
     * Creates an instance of PvpQuick
     */
	private PvpQuick() {
		this.setOpaque(true);
		this.setVisible(true);
	}

    public static PvpQuick getPvpQuick() throws GomokuException {
        if (pvpQuick == null) {
            pvpQuick = new PvpQuick();
            pvpQuick.prepareElementsGameBoardPVP();
        }
        return pvpQuick;
    }
    private JPanel timeLeft;
	private JPanel timeInformation;
	private JPanel gameWrapper;
	private JPanel tiempoRestanteP1 = new RelojPanelSub(Gomoku.getGomoku().getTimer(Gomoku.getGomoku().getP1(), "timerM"), Gomoku.getGomoku().getTimeP1("timeM"));
	private JPanel tiempoRestanteP2 = new RelojPanelSub(Gomoku.getGomoku().getTimer(Gomoku.getGomoku().getP2(), "timerM"), Gomoku.getGomoku().getTimeP2("timeM"));
	/**
     * Prepares the elements of the panel with labels with important information of the game
     * @return informationPanel
     */
	@Override
    public JPanel createInformationPanelGameOptions(){
        informationPanel = new JPanel(new GridBagLayout());
        Font arial = new Font("italic", 1, 18);
        turno = new JLabel("Siguiente en jugar: " + Gomoku.getGomoku().getTurn());
        nombreP1 = new JLabel("P1: " + Gomoku.getGomoku().getP1());
        nombreP2 = new JLabel("P2: " + Gomoku.getGomoku().getP2());
        colorP1 = new JLabel("ColorP1: " + Gomoku.getGomoku().getColor(Gomoku.getGomoku().getP1()));
        colorP2 = new JLabel("ColorP2: " + Gomoku.getGomoku().getColor(Gomoku.getGomoku().getP2()));
        turno.setFont(arial);
        nombreP1.setFont(arial);
        nombreP2.setFont(arial);
        colorP1.setForeground(hexToColor(Gomoku.getGomoku().getColor(Gomoku.getGomoku().getP1())));
        colorP2.setForeground(hexToColor(Gomoku.getGomoku().getColor(Gomoku.getGomoku().getP2())));
        tiempoP1.setFont(arial);
        tiempoP2.setFont(arial);
        informationPanel.setBackground(new Color(224,62,82));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(0, 10, 0, 0);
        informationPanel.add(turno, gbc);
        gbc.gridy = 1;
        informationPanel.add(Box.createRigidArea(new Dimension(0, 10)), gbc);
        gbc.gridy = 2;
        informationPanel.add(nombreP1, gbc);
        gbc.gridy = 3;
        informationPanel.add(colorP1, gbc);
        gbc.gridy = 5;
        informationPanel.add(tiempoP1, gbc);
        gbc.gridy = 6;
        informationPanel.add(nombreP2, gbc);
        gbc.gridy = 7;
        informationPanel.add(colorP2, gbc);
        gbc.gridy = 9;
        informationPanel.add(tiempoP2, gbc);
        return informationPanel;
    }
	
    @Override
    public void prepareElementsGame() {
        boardGame.setLayout(new BorderLayout());
        boardGame.setSize(this.getWidth() - gameOptions.getWidth(), this.getHeight());
        boardGame.setBackground(new Color(113, 197, 232,128));
        game = createBoardGame();
        timeInformation = createBoardTokenInformation();
        // Create a panel to hold the game component in the center
        gameWrapper = new JPanel();
        gameWrapper.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 30));
        gameWrapper.setBackground(new Color(113, 197, 232,128));
        gameWrapper.add(game);
        gameWrapper.add(timeInformation);
        boardGame.add(gameWrapper, BorderLayout.CENTER);
        boardGame.validate();
        boardGame.repaint();
    }
	
    public JPanel createBoardTokenInformation() {
        timeLeft = new JPanel();
        timeLeft.setLayout(new GridBagLayout());
        timeLeft.setBackground(new Color(166, 220, 242));
        timeLeft.setPreferredSize(new Dimension(200, 200));
        GridBagConstraints gbc = new GridBagConstraints();

        // Time left and timer for Player 1
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(0, 10, 0, 0);
        Font arial = new Font("italic", 1, 14);

        // Time left
        JLabel timeLeftP1 = new JLabel("Tiempo restante: " + Gomoku.getGomoku().getP1());
        timeLeftP1.setFont(arial);
        timeLeft.add(timeLeftP1, gbc);

        // Timer
        gbc.gridy = 1;
        timeLeft.add(tiempoRestanteP1, gbc);

        // Space
        gbc.gridy = 2;
        timeLeft.add(Box.createVerticalStrut(5), gbc);

        // Time left and timer for Player 2
        gbc.gridy = 3;

        // Time left
        JLabel timeLeftP2 = new JLabel("Tiempo restante: " + Gomoku.getGomoku().getP2());
        timeLeftP2.setFont(arial);
        timeLeft.add(timeLeftP2, gbc);

        // Timer
        gbc.gridy = 4;
        timeLeft.add(tiempoRestanteP2, gbc);

        return timeLeft;
    }
    
    /*
     * Actions for the finish button
     */
    @Override
    public void finishOption() throws GomokuException{
    	this.removeAll();
        GomokuGUI.finishButtonQuick();
        pvpQuick = null;
    }
    
    protected void setVisible() {
    	this.setVisible(true);
    	pvpQuick.repaint();
    }
    
    protected void setInvisible() {
    	this.setVisible(false);
    	pvpQuick.repaint();
    }
    
    
}

    
