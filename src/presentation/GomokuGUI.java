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
    private static int dimension = 15;
    private static String p1Name;
    private static String p2Name;
    private int heightScreen;
    private int widthScreen;
    private static JPanel principal;
    
    public GomokuGUI(){
        super("Gomoku");
        exit();
        prepareElements();
    }
    
    /**
     * Validates if there is a winner
     */
    public static void validateWinCondition(){
        if(Gomoku.getGomoku().getGomokuFinished()){ 
            JOptionPane.showMessageDialog(null, "Juego Terminado\n" + Gomoku.getGomoku().getWinner(), "Información", JOptionPane.INFORMATION_MESSAGE);
        }
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

    

    public void preparePrincipalPanel(){
        principal = new Background(heightScreen, widthScreen);
        this.add(principal, BorderLayout.CENTER);
    }   
    
    public static void prepareElementsMenu() {
        JPanel optionsPanel = generalPanel(MainMenu.getMainMenu());
        principal.add(optionsPanel, BorderLayout.CENTER);
    }

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

    public static void newGame() throws InvocationTargetException{
        MainMenu.getMainMenu().setInvisible();
        JPanel panel = generalPanel(MenuOpponents.getMenuOpponents());
        MenuOpponents.getMenuOpponents().setVisible();
        principal.add(panel, BorderLayout.CENTER);
    }  

    public static void gameModes() throws InvocationTargetException{
    	MenuOpponents.getMenuOpponents().setInvisible();
        principal.remove(MenuOpponents.getMenuOpponents());
        JPanel panel = generalPanel(GameModes.getGameModes());
        GameModes.getGameModes().setVisible();
        principal.add(panel, BorderLayout.CENTER);
    }

    public static String returnP1(){
        return Gomoku.getP1();
    }

    public static String returnP2(){
        return Gomoku.getP2();
    }

    public static void setNewDimension(int dimension_){
        dimension = dimension_;
    }

    public static int returnDimension(){
        return dimension;
    }

    public static void normalGameSelected(){
    	GameModes.getGameModes().setInvisible();
        principal.remove(GameModes.getGameModes());
        JPanel panel = generalPanel(PvpNormal.getPvpNormal());
        PvpNormal.getPvpNormal().setVisible();
        principal.add(panel, BorderLayout.CENTER);
    }


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
                    Color color = JColorChooser.showDialog(null, "Elige el color para tus piedras, " + name , null);
                    p.setColor(color);
                }else{
                    Player p = players.get(name);
                    p.setColor(Color.WHITE);
                }
            }
        }
        
    }

    public void askForTypeOfMachine() throws InvocationTargetException{
        JRadioButton agressive = new JRadioButton("Agresiva");
        JRadioButton expert = new JRadioButton("Experta");
        JRadioButton fearful = new JRadioButton("fearful");
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
            Gomoku.getGomoku().createMachine("Agressive");
        } else if (expert.isSelected()) {
            Gomoku.getGomoku().createMachine("Expert");
        } else if (fearful.isSelected()) {
            Gomoku.getGomoku().createMachine("Fearful");
        }
    }


    /**
     * Ask the name of the players
     */
    public void askForName(){
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
     * */
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
     *  */
    public void confirmExit(){
        int answer = JOptionPane.showConfirmDialog(null, "¿Desea cerrar la ventana?", "Cerrar ventana", JOptionPane.YES_NO_OPTION);
            if (answer == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
    }
    
    /**
     * Selects the gameMode QuickTime
     */
    public static void quickTimeGameSelected(){
    	GameModes.getGameModes().setInvisible();
        principal.remove(GameModes.getGameModes());
        JPanel panel = generalPanel(PvpQuick.getPvpQuick());
        PvpQuick.getPvpQuick().setVisible();
        principal.add(panel, BorderLayout.CENTER);
    }
    
    
    public static void limitedGameSelected() {
    	GameModes.getGameModes().setInvisible();
        JPanel panel = generalPanel(PvpLimited.getPvpLimited());
        PvpLimited.getPvpLimited().setVisible();
        principal.add(panel, BorderLayout.CENTER);
    }

    public static void finishButtonNormal() {
    	PvpNormal.getPvpNormal().setInvisible();
    	principal.remove(PvpNormal.getPvpNormal());
    	Gomoku.getGomoku().nullAll();
    	MainMenu.getMainMenu().setVisible();
    	
    }
    
    public static void finishButtonQuick() {
    	PvpQuick.getPvpQuick().setInvisible();
    	principal.remove(PvpQuick.getPvpQuick());
    	Gomoku.getGomoku().nullAll();
    	MainMenu.getMainMenu().setVisible();
    }
    
    public static void finishButtonLimited() {
    	PvpLimited.getPvpLimited().setInvisible();
    	principal.remove(PvpLimited.getPvpLimited());
    	Gomoku.getGomoku().nullAll();
    	MainMenu.getMainMenu().setVisible();
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