package presentation;

import java.awt.*;

import javax.swing.*;

import domain.Gomoku;

public class PvpQuick extends PvpNormal {

    private static PvpQuick pvpQuick = null;

    /**
     * Creates an instance of PvpQuick
     */
	private PvpQuick() {
		this.setOpaque(true);
	}

    public static PvpQuick getPvpQuick() {
        if (pvpQuick == null) {
            pvpQuick = new PvpQuick();
            pvpQuick.prepareElementsGameBoardPVP();
        }
        return pvpQuick;
    }
    private JPanel timeLeft;
	private JPanel timeInformation;
	private JPanel gameWrapper;
	private JPanel tiempoRestanteP1 = new RelojPanelSub(Gomoku.getGomoku().getTimer(GomokuGUI.returnP1(), "timerM"), Gomoku.getGomoku().getTimeP1("timeM"));
	private JPanel tiempoRestanteP2 = new RelojPanelSub(Gomoku.getGomoku().getTimer(GomokuGUI.returnP2(), "timerM"), Gomoku.getGomoku().getTimeP2("timeM"));
	
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
        super.informationPanel.remove(puntuacionJugador1);
        super.informationPanel.remove(puntuacionJugador2);
        boardGame.validate();
        boardGame.repaint();
    }
	
    public JPanel createBoardTokenInformation() {
        timeLeft = new JPanel();
        timeLeft.setLayout(new GridBagLayout());
        timeLeft.setBackground(new Color(166, 220, 242));
        timeLeft.setPreferredSize(new Dimension(200, 200));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(0, 10, 0, 0);
        Font arial = new Font("italic", 1, 14);
        tiempoRestanteP1.setFont(arial);
        timeLeft.add(tiempoRestanteP1, gbc);
        gbc.gridy = 1;
        timeLeft.add(Box.createVerticalStrut(5), gbc);
        gbc.gridy = 6;
        tiempoRestanteP2.setFont(arial);
        timeLeft.add(tiempoRestanteP2, gbc);
        return timeLeft;
    }
    
    @Override
    public void refreshTime(){
    	timeInformation.removeAll();
        gameWrapper.remove(timeInformation);
        timeInformation = createBoardTokenInformation();
        informationPanel.remove(puntuacionJugador1);
        informationPanel.remove(puntuacionJugador2);
        gameWrapper.add(timeInformation);
        boardGame.validate();
        boardGame.repaint();
    }
}

    
