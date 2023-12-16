package presentation;

import domain.*;
import java.util.*;
import javax.swing.*;

import java.lang.reflect.InvocationTargetException;
import java.awt.*;
import java.awt.event.*;

/**
 * Class of GomokuGUI
 * @author milton.gutierrez-oscar.lesmes
 */
public class GomokuGUI extends JFrame{
    private int heightScreen;
    private int widthScreen;
    private static JPanel principal;
    
    /**
     * Constructor for GomokuGUI
     */
    public GomokuGUI(){
        super("Gomoku");
        exit();
        prepareElements();
    }
    
    /**
     * Prepares the elements of the presentation, centers the frame and sets the specified size
     **/
    public void prepareElements(){
        heightScreen = (java.awt.Toolkit.getDefaultToolkit().getScreenSize().height) / 2 + 300;
        widthScreen = (java.awt.Toolkit.getDefaultToolkit().getScreenSize().width) / 2;
        setSize(widthScreen, heightScreen);
        setLocationRelativeTo(null);
        preparePrincipalPanel();
        prepareElementsMenu();
    }
    
    /**
     * Prepares the background panel
     */
    public void preparePrincipalPanel(){
        principal = new Background(heightScreen, widthScreen);
        this.add(principal, BorderLayout.CENTER);
    }   
    
    /**
     * Prepares the main menu
     */
    public static void prepareElementsMenu() {
        JPanel optionsPanel = generalPanel(MainMenu.getMainMenu());
        principal.add(optionsPanel, BorderLayout.CENTER);
    }
    
    /**
     * Sets a panel to paste new panels to avoid problems
     * @param panelToPaste is the panel to paste
     * @return panel to add to principal
     */
    public static JPanel generalPanel(JPanel panelToPaste){
        JPanel optionsPanel = new JPanel();
        optionsPanel.setOpaque(false);
        optionsPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        int panelWidth = (int) (principal.getWidth());
        int panelHeight = (int) (principal.getHeight());
        optionsPanel.setPreferredSize(new Dimension(panelWidth, panelHeight));
        optionsPanel.add(panelToPaste, gbc);
        optionsPanel.setVisible(true);
        return optionsPanel;
    }
    
    /**
     * Creates the panel of the new game
     * @throws InvocationTargetException
     */
    public static void newGame() throws InvocationTargetException{
        MainMenu.getMainMenu().setInvisible();
        JPanel panel = generalPanel(MenuOpponents.getMenuOpponents());
        MenuOpponents.getMenuOpponents().setVisible();
        principal.add(panel, BorderLayout.CENTER);
    }  
    
    /**
     * Prepares the gameMode panel
     * @throws InvocationTargetException
     */
    public static void gameModes() throws InvocationTargetException{
    	MenuOpponents.getMenuOpponents().setInvisible();
        principal.remove(MenuOpponents.getMenuOpponents());
        JPanel panel = generalPanel(GameModes.getGameModes());
        GameModes.getGameModes().setVisible();
        principal.add(panel, BorderLayout.CENTER);
    }
    
    /**
     * Activates the panel for the normal game
     * @throws GomokuException
     */
    public static void normalGameSelected() throws GomokuException{
    	MainMenu.getMainMenu().setInvisible();
    	GameModes.getGameModes().setInvisible();
        principal.remove(GameModes.getGameModes());
        JPanel panel = generalPanel(PvpNormal.getPvpNormal());
        PvpNormal.getPvpNormal().setVisible();
        principal.add(panel, BorderLayout.CENTER);
    }

    /**
     * Changes the color of the players
     * @throws GomokuException
     */
    public static void changeColor() throws GomokuException{
        HashMap<String, Player> players = Gomoku.getGomoku().getPlayers();
        Set<String> playersNames = players.keySet();
        HashSet<Color> selectedColors = new HashSet<>();
        if (Gomoku.getGomoku().getOpponent().equals("pvp")) {
            for (String name : playersNames) {
                Color selectedColor = JColorChooser.showDialog(null, "Elige el color para tus piedras, " + name, null);
                while(selectedColor == null) {
                    JOptionPane.showMessageDialog(null, "No puedes seleccionar un color nulo", "Alerta", JOptionPane.ERROR_MESSAGE);
                    selectedColor = JColorChooser.showDialog(null, "Elige el color para tus piedras, " + name, null);
                }
                while (selectedColors.contains(selectedColor)) {
                    JOptionPane.showMessageDialog(null, "No puedes seleccionar un color ya en uso", "Alerta", JOptionPane.WARNING_MESSAGE);
                    selectedColor = JColorChooser.showDialog(null, "Elige el color para tus piedras, " + name, null);
    
                    while(selectedColor == null) {
                        JOptionPane.showMessageDialog(null, "No puedes seleccionar un color nulo", "Alerta", JOptionPane.ERROR_MESSAGE);
                        selectedColor = JColorChooser.showDialog(null, "Elige el color para tus piedras, " + name, null);
                    }
                }
                if (selectedColor != null) {
                    Gomoku.getGomoku().setColor(name, selectedColor);
                    selectedColors.add(selectedColor);
                }
            }
        }else if(Gomoku.getGomoku().getOpponent() == "pve"){
            for(String name : playersNames){
                if(players.get(name) instanceof Human){
                    Player p = players.get(name);
                    Color color = JColorChooser.showDialog(null, "Elige el color para tus piedras, " + name, null);
                    while (color == null || color.equals(Color.WHITE)) {
                        JOptionPane.showMessageDialog(null, "No puedes seleccionar el color blanco ni uno nulo", "Alerta", JOptionPane.ERROR_MESSAGE);
                        color = JColorChooser.showDialog(null, "Elige el color para tus piedras, " + name, null);
                    }
                    p.setColor(color);
                }else{
                    Player p = players.get(name);
                    p.setColor(Color.WHITE);
                }
            }
        }
        
    }
    
    


    /**
     * Ask the name of the players
     */
    public void askForName(){
    	String p1Name;
        do {
            p1Name = JOptionPane.showInputDialog("Inserta el nombre del Jugador 1", "");
            if (p1Name == null || p1Name.isEmpty()) {
                JOptionPane.showMessageDialog(null, "El nombre no puede estar vacío.");
            }
            } while(p1Name == null || p1Name.isEmpty());
        Gomoku.getGomoku().setNameP1(p1Name);
    }
    

    /**
     * Asks for the time limit of the game
     */
    public static void askForTime(){
    	boolean flag = false;
    	int totalTime = 0;
    	while(!flag) {
    		try {
                totalTime = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el tiempo limite (En segundos)"));
                if (totalTime > 10) {
                    flag = true;
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "El tiempo limite debe ser un numero entero.");
            }
    	}
        Gomoku.getGomoku().setTime(totalTime);
    }

    /**
     * Sets the action to exit the JFrame
     */
    public void exit(){
        this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        WindowListener w = new WindowAdapter(){
            @Override
            public void windowClosing(WindowEvent e){
                confirmExit();
                
            }
        };
        this.addWindowListener(w);
    }

    /**
     * Asks if the user wants to close the JFrame
     */
    public void confirmExit(){
        int answer = JOptionPane.showConfirmDialog(null, "¿Desea cerrar la ventana?", "Cerrar ventana", JOptionPane.YES_NO_OPTION);
            if (answer == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
    }
    
    /**
     * Selects the gameMode QuickTime
     * @throws GomokuException 
     */
    public static void quickTimeGameSelected() throws GomokuException{
    	MainMenu.getMainMenu().setInvisible();
    	GameModes.getGameModes().setInvisible();
        principal.remove(GameModes.getGameModes());
        JPanel panel = generalPanel(PvpQuick.getPvpQuick());
        PvpQuick.getPvpQuick().setVisible();
        principal.add(panel, BorderLayout.CENTER);
    }
    
    /**
     * Activates the panel for limited mode
     * @throws GomokuException
     */
    public static void limitedGameSelected() throws GomokuException {
    	MainMenu.getMainMenu().setInvisible();
    	GameModes.getGameModes().setInvisible();
        JPanel panel = generalPanel(PvpLimited.getPvpLimited());
        PvpLimited.getPvpLimited().setVisible();
        principal.add(panel, BorderLayout.CENTER);
    }

    /**
     * Finish the normal game
     * @throws GomokuException
     */
    public static void finishButtonNormal() throws GomokuException {
    	PvpNormal.getPvpNormal().setInvisible();
    	principal.remove(PvpNormal.getPvpNormal());
    	Gomoku.getGomoku().nullAll();
    	MainMenu.getMainMenu().setVisible();
    	
    }
    
    /**
     * Finish the quickgame
     * @throws GomokuException
     */
    public static void finishButtonQuick() throws GomokuException {
    	PvpQuick.getPvpQuick().setInvisible();
    	principal.remove(PvpQuick.getPvpQuick());
    	Gomoku.getGomoku().nullAll();
    	MainMenu.getMainMenu().setVisible();
    }
    
    /**
     * Finish the limited game
     * @throws GomokuException
     */
    public static void finishButtonLimited() throws GomokuException {
    	PvpLimited.getPvpLimited().setInvisible();
    	principal.remove(PvpLimited.getPvpLimited());
    	Gomoku.getGomoku().nullAll();
    	MainMenu.getMainMenu().setVisible();
    }
    
    /**
     * Validates if there is a winner
     * @throws GomokuException 
     * @throws HeadlessException 
     */
    public static void validateWinCondition() throws HeadlessException, GomokuException{
        if(Gomoku.getGomoku().getGomokuFinished()){ 
            JOptionPane.showMessageDialog(null, "Juego Terminado\nGANADOR: " + Gomoku.getGomoku().getWinner(), "Información", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    /**
     * Returns the score of a specific player
     * @param name is the name of the player
     * @return score is the total score of the desired player
     * @throws GomokuException
     */
    public static int getPuntuacion(String name) throws GomokuException {
    	return Gomoku.getGomoku().getPuntuacion(name);
    }
    
    /**
     * Validates the current panel when a game is open
     * @throws GomokuException
     */
    public static void validatePanel() throws GomokuException {
        String gameMode_ = Gomoku.getGomoku().getGameMode();
        System.out.println(gameMode_);
        if ("normal".equals(gameMode_)) {
            System.out.println("Estoy colocando el modo normal");
            normalGameSelected();
        } else if ("quicktime".equals(gameMode_)) {
            System.out.println("Estoy colocando el modo quick");
            quickTimeGameSelected();
        } else if ("limited".equals(gameMode_)) {
            System.out.println("Estoy colocando el modo limited");
            limitedGameSelected();
        }
    }
    
    //////////////////////////////////////////////////////////////////////////
    /**
     * Runs the presentation
     * @param args 
     */
    public static void main(String[] args) {
        GomokuGUI gomokuGUI = new GomokuGUI();
        gomokuGUI.setVisible(true);
    }
    

}