package presentation;

import java.awt.*;
import java.awt.event.*;
import java.lang.reflect.*;

import javax.swing.*;

import domain.Gomoku;
import domain.GomokuException;
import domain.Log;

public class MenuOpponents extends JPanel{
    private static MenuOpponents menuOpponents = null;
    private String p1Name;
    private String p2Name;
    private JButton personaVPersona;
    private JButton personaVMaquina;
    
    /**
     * Constructor for menu opponents panel
     * @throws InvocationTargetException
     */
    private MenuOpponents(){
        this.setBackground(new Color(113, 197, 232));
    }
    
    /**
     * Returns the panel and creates one if null
     * @return panel for menuOpponents 
     * @throws InvocationTargetException
     */
    public static MenuOpponents getMenuOpponents(){
        if (menuOpponents == null) {
            menuOpponents = new MenuOpponents();
            menuOpponents.preparePanel();
            
        }
        return menuOpponents;
    }
    
    /**
     * Prepares the panel with his buttons
     * @throws InvocationTargetException
     */
    private void preparePanel(){
        menuOpponents.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS)); // Establece BoxLayout como layout del panel
    
        menuOpponents.setBackground(new Color(0, 0, 0, 35));
        Font italic = new Font("italic", 3, 38);

        JLabel title = new JLabel("Selecciona tu oponente");
        title.setFont(italic);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        personaVPersona = new JButton("Humano VS Humano");
        personaVMaquina = new JButton("Humano VS Maquina");
        prepareActions();
    
        // Centra los botones horizontalmente
        menuOpponents.setAlignmentX(Component.CENTER_ALIGNMENT);
    
        // Centra los botones verticalmente
        menuOpponents.setAlignmentY(Component.CENTER_ALIGNMENT);
    
        int gapSize = 20; // Ajusta el espacio entre los botones (píxeles)
        Dimension buttonSize = new Dimension(200, 40); // Ajusta el tamaño de los botones
    
        menuOpponents.add(Box.createVerticalStrut(gapSize)); // Agrega espacio vertical entre los botones
        setButtonAttributes(personaVPersona, buttonSize);
        menuOpponents.add(personaVPersona);
    
        menuOpponents.add(Box.createVerticalStrut(gapSize));
        setButtonAttributes(personaVMaquina, buttonSize);
        menuOpponents.add(personaVMaquina);
    }   
    
    /**
     * Sets the configuration for the buttons
     * @param button is the button to configure
     * @param size is the size of the button
     */
    private void setButtonAttributes(JButton button, Dimension size) {
        button.setPreferredSize(size);
        button.setMaximumSize(size);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
    }
    
    /**
     * Prepare the listeners for the buttons
     */
    private void prepareActions(){
        personaVPersona.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
            	Gomoku.getGomoku().setOpponent("pvp");
                askForNames();
                GomokuGUI.gameModes();
                try {
					Gomoku.getGomoku().createRivals();
				} catch (GomokuException e1) {
					Log.record(e1);
				}
            }});
        
        personaVMaquina.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
            	Gomoku.getGomoku().setOpponent("pve");
            	askForNames();
                GomokuGUI.gameModes();
				askForTypeOfMachine();
            	try {
					Gomoku.getGomoku().createRivals();
				} catch (GomokuException e1) {
					Log.record(e1);
				}
            }});
    }  

    /**
     * Ask the name of the players
     */
    public void askForNames(){
        do {
            p1Name = JOptionPane.showInputDialog("Inserta el nombre del Jugador 1", "");
            if (p1Name == null || p1Name.isEmpty()) {
                JOptionPane.showMessageDialog(null, "El nombre no puede estar vacío.");
            }
            } while(p1Name == null || p1Name.isEmpty());
        Gomoku.getGomoku().setNameP1(p1Name);
        if(Gomoku.getGomoku().getOpponent() != "pve") {
        	do { 
                p2Name = JOptionPane.showInputDialog("Inserta el nombre del Jugador 2", "");
                if (p2Name == null || p2Name.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "El nombre no puede estar vacío.");
                }else if (p2Name.equals(p1Name)){
                    JOptionPane.showMessageDialog(null, "El nombre no puede ser igual al del otro jugador");
                }
                } while(p2Name == null || p2Name.isEmpty() || p2Name.equals(p1Name));
            Gomoku.getGomoku().setNameP2(p2Name);
        }else {
        	Gomoku.getGomoku().setNameP2("machine");
        }
        
    }
    
    /**
     * Makes the panel invisible
     */
    public void setInvisible(){
        this.setVisible(false);
        menuOpponents.repaint();
    }
    
    /**
     * Makes the panel visible
     */
    public void setVisible(){
        this.setVisible(true);
        menuOpponents.repaint();
    }
    
    /**
     * Asks for the type of machine to create
     * @throws InvocationTargetException
     * @throws GomokuException 
     */
    public void askForTypeOfMachine(){
        JRadioButton agressive = new JRadioButton("Agresiva");
        JRadioButton expert = new JRadioButton("Experta");
        JRadioButton fearful = new JRadioButton("Miedosa");
        ButtonGroup sizeGroup = new ButtonGroup();
        sizeGroup.add(agressive);
        sizeGroup.add(expert);
        sizeGroup.add(fearful);
        Object[] options = {agressive, expert, fearful};
        JOptionPane.showOptionDialog(
                null,
                options,
                "Seleccione el tipo de oponente",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                null,
                null
        );
        if (agressive.isSelected()) {
            Gomoku.getGomoku().setMachineType("Agressive");
        } else if (expert.isSelected()) {
        	JOptionPane.showMessageDialog(null, "Pendiente, selecciona otra maquina porfavor.");
            askForTypeOfMachine();
        } else if (fearful.isSelected()) {
            Gomoku.getGomoku().setMachineType("Fearful");
        }
    }
}
