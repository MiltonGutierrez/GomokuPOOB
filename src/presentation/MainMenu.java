package presentation;

import java.awt.event.*;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.awt.*;
import javax.swing.*;

import domain.Gomoku;
import domain.GomokuException;
import domain.Log;


public class MainMenu extends JPanel {
    private JButton newOptionButton;
    private JButton changeSizeButton;
    private JButton loadOptionButton;
    private JButton exitButton;
    public static MainMenu mainMenu = null;

    /**
     * Creates an instansce of Gomoku
     */
    private MainMenu() {
        this.setOpaque(true);
        this.setVisible(true);
    }

    /**
     * Returns the main menu and creates if null
     * @return the panel with the options for the main menu
     */
    public static MainMenu getMainMenu() {
        if (mainMenu == null) {
            mainMenu = new MainMenu();
            mainMenu.prepareElements();
            
        }
        return mainMenu;
    }

    
    /**
     * Prepare the elements of the panel
     */
    private void prepareElements() {

        mainMenu.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS)); // Establece BoxLayout como layout del panel
    
        mainMenu.setBackground(new Color(0, 0, 0, 35));
    
        newOptionButton = new JButton("Jugar");
        changeSizeButton = new JButton("Cambiar tamaño");
        loadOptionButton = new JButton("Cargar partida");
        exitButton = new JButton("Salir");
    
        prepareActions();
    
        // Centra los botones horizontalmente
        mainMenu.setAlignmentX(Component.CENTER_ALIGNMENT);
    
        // Centra los botones verticalmente
        mainMenu.setAlignmentY(Component.CENTER_ALIGNMENT);
    
        int gapSize = 20; // Ajusta el espacio entre los botones (píxeles)
        Dimension buttonSize = new Dimension(200, 40); // Ajusta el tamaño de los botones
    
        mainMenu.add(Box.createVerticalStrut(gapSize)); // Agrega espacio vertical entre los botones
        setButtonAttributes(newOptionButton, buttonSize);
        mainMenu.add(newOptionButton);
    
        mainMenu.add(Box.createVerticalStrut(gapSize));
        setButtonAttributes(changeSizeButton, buttonSize);
        mainMenu.add(changeSizeButton);
    
        mainMenu.add(Box.createVerticalStrut(gapSize));
        setButtonAttributes(loadOptionButton, buttonSize);
        mainMenu.add(loadOptionButton);
    
        mainMenu.add(Box.createVerticalStrut(gapSize));
        setButtonAttributes(exitButton, buttonSize);
        mainMenu.add(exitButton);
    }
    
    /**
     * Sets the configuration for the buttons
     * @param button is the button to set
     * @param size is the size for the button
     */
    private void setButtonAttributes(JButton button, Dimension size) {
        button.setPreferredSize(size);
        button.setMaximumSize(size);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
    }
    
    /**
     * Prepares the listeners for the buttons
     */
    private void prepareActions() {
        newOptionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    newOption();
                    
                } catch (InvocationTargetException e1) {
                    e1.printStackTrace();
                } catch (GomokuException e1) {
                    e1.printStackTrace();
                }
            }
        });
    
        changeSizeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changeSizeOption();
            }
        });
    
        loadOptionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
					loadOption();
				} catch (GomokuException e1) {
					Log.record(e1);
				}
            }
        });
    
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                confirmExit();
            }
        });
    }

    /**
     * Is the option to activate the new game panel
     * @throws InvocationTargetException
     * @throws GomokuException
     */
    public void newOption() throws InvocationTargetException, GomokuException{
        Gomoku.getGomoku();
        Gomoku.getGomoku().setDimension(15);
        GomokuGUI.newGame();
    }
    
    /**
     * Makes invisible the panel
     */
    public void setInvisible(){
        this.setVisible(false);
        mainMenu.repaint();
    }
    
    /**
     * Makes invisible the panel
     */
    public void setVisible(){
        this.setVisible(true);
        mainMenu.repaint();
    }
    

    /**
     * Changes the size of the Gomoku's board
     */
    public void changeSizeOption(){
    	int dimension = 0;
        try{
            dimension = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el numero de filas"));
            Gomoku.getGomoku().setDimension(dimension);
        }catch(Exception e){
            Log.record(e);
        }
        
        if(dimension < 10){
            dimension = 10;
            Gomoku.getGomoku().setDimension(dimension);
        }
    }
    
    /**
     * Shows the user a menu to load a file
     * @throws GomokuException 
     */
    public void loadOption() throws GomokuException{
    	JFileChooser load = new JFileChooser();
        load.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int result = load.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = load.getSelectedFile();
            if (file != null) {
            	Gomoku.getGomoku();
				Gomoku.open01(file);
            }
        }
        GomokuGUI.validatePanel();
    }

    /**
     * Asks if the user wants to close the JFrame
     *  */
    public void confirmExit(){
        int answer = JOptionPane.showConfirmDialog(null, "¿Desea cerrar la ventana?", "Cerrar ventana", JOptionPane.YES_NO_OPTION);
            if (answer == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
    }

}
