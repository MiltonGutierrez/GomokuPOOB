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
    private int dimension = 15;
    private JFileChooser load;
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


    public static MainMenu getMainMenu() {
        if (mainMenu == null) {
            mainMenu = new MainMenu();
            mainMenu.prepareElements();
            
        }
        return mainMenu;
    }

    

    private void prepareElements() {

        mainMenu.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS)); // Establece BoxLayout como layout del panel
    
        mainMenu.setBackground(new Color(0, 0, 0, 35));
    
        newOptionButton = new JButton("Jugar");
        changeSizeButton = new JButton("Cambiar tama�o");
        loadOptionButton = new JButton("Cargar partida");
        exitButton = new JButton("Salir");
    
        prepareActions();
    
        // Centra los botones horizontalmente
        mainMenu.setAlignmentX(Component.CENTER_ALIGNMENT);
    
        // Centra los botones verticalmente
        mainMenu.setAlignmentY(Component.CENTER_ALIGNMENT);
    
        int gapSize = 20; // Ajusta el espacio entre los botones (p�xeles)
        Dimension buttonSize = new Dimension(200, 40); // Ajusta el tama�o de los botones
    
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
    
    private void setButtonAttributes(JButton button, Dimension size) {
        button.setPreferredSize(size);
        button.setMaximumSize(size);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
    }
    
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
                loadOption();
            }
        });
    
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                confirmExit();
            }
        });
    }

    
    public void newOption() throws InvocationTargetException, GomokuException{
        Gomoku.getGomoku();
        Gomoku.getGomoku().setDimension(dimension);
        GomokuGUI.newGame();
    }

    public void setInvisible(){
        this.setVisible(false);
        mainMenu.repaint();
    }

    public void setVisible(){
        this.setVisible(true);
        mainMenu.repaint();
    }
    

    /**
     * Changes the size of the Gomoku's board
     */
    public void changeSizeOption(){
        try{
            dimension = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el numero de filas"));
            GomokuGUI.setNewDimension(dimension);
        }catch(Exception e){
            Log.record(e);
        }
        
        if(dimension < 10){
            dimension = 10;
            GomokuGUI.setNewDimension(dimension);
        }
    }

    
    /**
     * Shows the user a menu to load a file
     */
    public void loadOption(){
        File file = null;
        load = new JFileChooser();
        load.setFileSelectionMode(JFileChooser.FILES_ONLY);
        showFileName("Se abri� el archivo ", file);
    }

    /**
     * Shows the name of the selected file.
     * @param m 
     * @param file 
     */
    private void showFileName(String m, File file){
        int result = load.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            file = load.getSelectedFile();
        }
        if (file != null) {
            JOptionPane.showMessageDialog(null, m + file.getName(), "Atencion", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /**
     * Asks if the user wants to close the JFrame
     *  */
    public void confirmExit(){
        int answer = JOptionPane.showConfirmDialog(null, "�Desea cerrar la ventana?", "Cerrar ventana", JOptionPane.YES_NO_OPTION);
            if (answer == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
    }

}
