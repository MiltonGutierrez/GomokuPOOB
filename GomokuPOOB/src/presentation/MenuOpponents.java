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

    private MenuOpponents() throws InvocationTargetException{
        this.setBackground(new Color(113, 197, 232));
    }

    public static MenuOpponents getMenuOpponents() throws InvocationTargetException {
        if (menuOpponents == null) {
            menuOpponents = new MenuOpponents();
            menuOpponents.preparePanel();
        }
        return menuOpponents;
    }

    private JButton personaVPersona;
    private JButton personaVMaquina;
    

    private void preparePanel() throws InvocationTargetException{
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

    private void setButtonAttributes(JButton button, Dimension size) {
        button.setPreferredSize(size);
        button.setMaximumSize(size);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
    }

    private void prepareActions(){
        personaVPersona.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
            	Gomoku.getGomoku().setOpponent("pvp");
                askForNames();
                try {
                    GomokuGUI.gameModes();
                } catch (InvocationTargetException e1) {
                    Log.record(e1);
                }
                Gomoku.getGomoku().createRivals();
            }});
        
        personaVMaquina.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                
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

        do { 
            p2Name = JOptionPane.showInputDialog("Inserta el nombre del Jugador 2", "");
            if (p2Name == null || p2Name.isEmpty()) {
                JOptionPane.showMessageDialog(null, "El nombre no puede estar vacío.");
            }else if (p2Name.equals(p1Name)){
                JOptionPane.showMessageDialog(null, "El nombre no puede ser igual al del otro jugador");
            }
            } while(p2Name == null || p2Name.isEmpty() || p2Name.equals(p1Name));
        Gomoku.getGomoku().setNameP2(p2Name);
    }

    public void setInvisible(){
        this.setVisible(false);
    }

    public void setVisible(){
        this.setVisible(true);
    }
}
