package presentation;

import java.awt.*;

import javax.swing.*;

import domain.Gomoku;

public class PvpQuick extends PvpNormal {
	private static PvpQuick pvpQuick = null;

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
	
    private JPanel tiempoP1 = new RelojPanel(Gomoku.getGomoku().getTimer(GomokuGUI.returnP1()), "quicktime", Gomoku.getGomoku().getTimeLeftP1());
    private JPanel tiempoP2 = new RelojPanel(Gomoku.getGomoku().getTimer(GomokuGUI.returnP1()), "quicktime", Gomoku.getGomoku().getTimeLeftP2());
	
	@Override
    public JPanel createInformationPanelGameOptions(){
        informationPanel = new JPanel(new GridBagLayout());
        Font arial = new Font("italic", 1, 18);
        super.turno = new JLabel("Siguiente en jugar: " + Gomoku.getGomoku().getTurn());
        super.nombreP1 = new JLabel("P1: " + GomokuGUI.returnP1());
        super.nombreP2 = new JLabel("P2: " + GomokuGUI.returnP2());
        super.colorP1 = new JLabel("ColorP1: " + Gomoku.getGomoku().getColor(GomokuGUI.returnP1()));
        super.colorP2 = new JLabel("ColorP2: " + Gomoku.getGomoku().getColor(GomokuGUI.returnP2()));
        super.turno.setFont(arial);
        super.nombreP1.setFont(arial);
        nombreP2.setFont(arial);
        colorP1.setForeground(hexToColor(Gomoku.getGomoku().getColor(GomokuGUI.returnP1())));
        colorP2.setForeground(hexToColor(Gomoku.getGomoku().getColor(GomokuGUI.returnP2())));
        tiempoP1.setFont(arial);
        tiempoP2.setFont(arial);

        informationPanel.setBackground(new Color(224,62,82));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(0, 8, 0, 0);
        informationPanel.add(turno, gbc);
        gbc.gridy = 1;
        informationPanel.add(Box.createRigidArea(new Dimension(0, 8)), gbc);
        gbc.gridy = 2;
        informationPanel.add(nombreP1, gbc);
        gbc.gridy = 3;
        informationPanel.add(colorP1, gbc);
        gbc.gridy = 4;
        informationPanel.add(tiempoP1, gbc);
        gbc.gridy = 5;
        informationPanel.add(nombreP2, gbc);
        gbc.gridy = 6;
        informationPanel.add(colorP2, gbc);
        gbc.gridy = 7;
        informationPanel.add(tiempoP2, gbc);
        
        return informationPanel;
    }
    
}
