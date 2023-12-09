package presentation;

import domain.*;
import java.util.*;
import javax.swing.*;
import javax.swing.Box;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.awt.*;
import java.awt.event.*;

/**
 * Class of GomokuGUI
 * @author milton.gutierrez-oscar.lesmes
 */
public class GomokuGUI extends JFrame{
    private int dimension = 15;
    private JPanel mainMenu;
    private JMenuBar menuBar;
    private JMenu menu;
    private JMenuItem newOption;
    private JMenuItem changeSizeOption;
    private JMenuItem loadOption;
    private JMenuItem exitOption;
    private JFileChooser load;
    private String p1Name;
    private String p2Name;
    private JPanel informationPanel;
    private JLabel turno;
    private JLabel puntuacionJugador1;
    private JLabel puntuacionJugador2;
    private JLabel nombreP1;
    private JLabel nombreP2;
    private JLabel tiempoP1;
    private JLabel tiempoP2;
    private JLabel colorP1;
    private JLabel colorP2;
    private ImageIcon logo;
    private JPanel logoPanel;
    private JPanel optionsPanel;
    
    
    public GomokuGUI(){
        super("Gomoku");
        prepareElements();
        prepareActions();
    }

    /**
     * Prepares the elements of the presentation, centers the frame and sets the specified size
     **/
    public void prepareElements(){
        int heightScreen = (java.awt.Toolkit.getDefaultToolkit().getScreenSize().height) / 2 + 300;
        int widthScreen = (java.awt.Toolkit.getDefaultToolkit().getScreenSize().width) / 2;
        setSize(widthScreen, heightScreen );
        setLocationRelativeTo(null);
        prepareElementsMenu();
    }

    /**
     * Prepares the actions creating the listeners
     */
     public void prepareActions(){
        exit();
        prepareMenuActions();
    }


    /**
     * Prepares the elements of the menu  
     * */
    public void prepareElementsMenu(){
        mainMenu = new JPanel();
        menuBar = new JMenuBar();
        menu = new JMenu("Opciones");
        newOption = new JMenuItem("Jugar");
        changeSizeOption = new JMenuItem("Cambiar tamaño");
        loadOption = new JMenuItem("Cargar partida");
        exitOption = new JMenuItem ("Salir");
        menu.add(newOption);
        menu.add(changeSizeOption);
        menu.add(loadOption);
        menu.add(exitOption);
        menuBar.add(menu);
        mainMenu.add(menuBar,BorderLayout.CENTER);
        getContentPane().add(mainMenu);
    }

    /**
     * Selects the rivals in the game
     * @throws InvocationTargetException
     * @throws GomokuException
     */
    public void askForOpponent() throws InvocationTargetException, GomokuException{
        JRadioButton personaVPersona = new JRadioButton("Humano VS Humano");
        JRadioButton personaVMaquina = new JRadioButton("Humano VS Maquina");
        ButtonGroup sizeGroup = new ButtonGroup();
        sizeGroup.add(personaVPersona);
        sizeGroup.add(personaVMaquina);
        Object[] options = {personaVPersona, personaVMaquina};
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
        if (personaVPersona.isSelected()){
            Gomoku.getGomoku().setOpponent("pvp");
            askForNames();
            selectGameMode();
            Gomoku.getGomoku().createRivals();
            changeColor();
            prepareElementsGameBoardPVP();
            Gomoku.getGomoku().startGame();

        } else if (personaVMaquina.isSelected()){
            Gomoku.getGomoku().setOpponent("pve");
            askForName();
            askForTypeOfMachine();
            Gomoku.getGomoku().createRivals();
            selectGameMode();
            //prepareElementsGameBoard();
        }
    }


    private JPanel gameOptions;
    private JPanel boardGame;
    /**
     * Prepares the elements of the board
     *  */
    public void prepareElementsGameBoardPVP() {
        mainMenu.setVisible(false);
        gameOptions = new JPanel();
        boardGame = new JPanel();
        this.setLayout(new BorderLayout());
        this.add(gameOptions, BorderLayout.NORTH);
        this.add(boardGame, BorderLayout.CENTER);
        prepareElementsGameOptionsPVP();
        prepareElementsGame();
        gameOptions.setVisible(true);
        boardGame.setVisible(true);
        prepareActionsBoardGame();
    }

    private JPanel game;
    private JPanel information;
    private JPanel tokensLeft;
    /**
     * Prepares the elements of the game
     *  */
    public void prepareElementsGame() {
        boardGame.setLayout(new BorderLayout());
        boardGame.setSize(this.getWidth() - gameOptions.getWidth(), this.getHeight());
        boardGame.setBackground(new Color(113, 197, 232));
    
        game = createBoardGame();
        information = createBoardInformation();
        tokensLeft = createBoardTokenInformation();
    
        // Create a panel to hold the game component in the center
        JPanel gameWrapper = new JPanel();
        gameWrapper.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 30));
        gameWrapper.setBackground(new Color(113, 197, 232));
        gameWrapper.add(game);

        tokenInformation = prepareTokenInformation();
        boardGame.add(gameWrapper, BorderLayout.CENTER);
        boardGame.add(tokenInformation, BorderLayout.EAST);
        
        boardGame.validate();
        boardGame.repaint();
    }
    private JPanel tokenInformation;

    public JPanel prepareTokenInformation(){
        tokenInformation = new JPanel();
        tokenInformation.setLayout(new BoxLayout(tokenInformation, BoxLayout.Y_AXIS)); // Use BoxLayout with Y_AXIS to arrange components vertically
        tokenInformation.setBackground(new Color(113, 197, 232));
            tokenInformation.setPreferredSize(new Dimension(400, 400));
        tokenInformation.add(information);
        tokenInformation.add(Box.createVerticalStrut(10)); // Add vertical spacing between components
        tokenInformation.add(tokensLeft);
        return tokenInformation;
    }

    public void refreshTokens(){
        tokenInformation.removeAll();
        boardGame.remove(tokenInformation);
        information = createBoardInformation();
        tokensLeft = createBoardTokenInformation();
        tokenInformation = prepareTokenInformation();
        
        boardGame.add(tokenInformation, BorderLayout.EAST);
        boardGame.validate();
        boardGame.repaint();
    }

    private JPanel timeLeft;
    private JLabel fichasDisponibles;
    private JButton selectNormal;
    private JButton selectHeavy;
    private JButton selectTemporal;


    public JPanel createBoardInformation() {
        information = new JPanel();
        information.setLayout(new GridBagLayout());
        information.setBackground(new Color(166, 220, 242));
        return information;
    }
    public JPanel createBoardTokenInformation() {
        tokensLeft = new JPanel();
        tokensLeft.setLayout(new GridBagLayout());
        tokensLeft.setBackground(new Color(166, 220, 242));
        tokensLeft.setPreferredSize(new Dimension(200, 200));
        
        return tokensLeft;
    }


    /*
     * Creates the board of Gomoku
     *  */
    private JPanel createBoardGame(){
        game = new JPanel();
        game.setBackground(Color.WHITE);
        game.setLayout(new GridLayout(dimension, dimension));
        game.setPreferredSize(new Dimension(400, 400));
        for (int fila = 0; fila < dimension; fila++) {
          for (int columna = 0; columna < dimension; columna++) {
            JButton boton = createBoton(fila, columna);
            boton.setPreferredSize(new Dimension(30, 30));
            game.add(boton);
          }
        }

        return game;
    }




    /**
     * 
     * @param dimension
     * @param x
     * @param y
     * @return
     */
    public int getComponentNumber(int dimension, int x, int y) {
        return  x * dimension + y + 1;
    }



    public void refreshInformationPanel(){
        gameOptions.removeAll();
        informationPanel.removeAll();
        gameOptions.remove(informationPanel);
        informationPanel = createInformationPanelGameOptions();
        gameOptions.add(optionsPanel, BorderLayout.EAST);
        gameOptions.add(informationPanel, BorderLayout.WEST);
        gameOptions.add(logoPanel, BorderLayout.CENTER);
        boardGame.remove(tokenInformation);
        tokenInformation = prepareTokenInformation();
        Gomoku.getGomoku().startPlayerTimer(Gomoku.getGomoku().getTurn());
        this.revalidate();
        this.repaint();
        validateWinCondition();
    }
    /**
     * 
     */
    public void validateWinCondition(){
        if(Gomoku.getGomoku().getGomokuFinished()){ 
            JOptionPane.showMessageDialog(this, "Juego Terminado\n" + Gomoku.getGomoku().getWinner(), "Información", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private JButton saveGomoku;
    private JButton resetGomoku;
    private JButton finishGomoku;
    /**
     * Prepares the elements of the game options
     *  */
    public void prepareElementsGameOptionsPVP() {
        gameOptions.setSize(this.getWidth() / 3, this.getHeight());
        gameOptions.setBackground(new Color(224,62,82));
        gameOptions.setLayout(new BorderLayout());
        saveGomoku = new JButton("Guardar");
        resetGomoku = new JButton("Reiniciar");
        finishGomoku = new JButton("Finalizar");
        JPanel optionsPanel = createOptionsPanelGameOptions();
        JPanel informationPanel = createInformationPanelGameOptions();
        JPanel logoPanel = createLogoPanelGameOptions();
        gameOptions.add(optionsPanel, BorderLayout.EAST);
        gameOptions.add(informationPanel, BorderLayout.WEST);
        gameOptions.add(logoPanel, BorderLayout.CENTER);
    }

    

    /**
     * Prepares the elements of the panel with labels with the logo of the game
     *  */
    public JPanel createLogoPanelGameOptions() {
        JLabel logoLabel = new JLabel();
        logoLabel.setBounds(10, 80, 150, 150);
        logoPanel = new JPanel();
        logoPanel.setBackground(new Color(224,62,82));
        logoPanel.add(logoLabel, BorderLayout.CENTER);
        URL url = getClass().getResource("/presentation/resources/logo.png");
        if(url != null){
            logo = new ImageIcon(url);
            logoLabel.setIcon(new ImageIcon(logo.getImage().getScaledInstance(logoLabel.getWidth(), logoLabel.getHeight(), Image.SCALE_SMOOTH)));
        }
        return logoPanel;
    }


    
    /**
     * Prepares the elements of the panel with option buttons
     */
    private JPanel createOptionsPanelGameOptions() {
        optionsPanel = new JPanel(new GridBagLayout());
        optionsPanel.setBackground(new Color(224,62,82));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(0, 0, 0, 50);
        optionsPanel.add(saveGomoku, gbc);
        gbc.gridy = 1;
        optionsPanel.add(Box.createRigidArea(new Dimension(0, 10)), gbc);
        gbc.gridy = 2;
        optionsPanel.add(resetGomoku, gbc);
        gbc.gridy = 3;
        optionsPanel.add(Box.createRigidArea(new Dimension(0, 10)), gbc);
        gbc.gridy = 4;
        optionsPanel.add(finishGomoku, gbc);
        return optionsPanel;
    }


    public static Color hexToColor(String hex) {
        int r = Integer.parseInt(hex.substring(1, 3), 16);
        int g = Integer.parseInt(hex.substring(3, 5), 16);
        int b = Integer.parseInt(hex.substring(5, 7), 16);
        return new Color(r, g, b);
    }

    /**
     * Prepares the elements of the panel with labels with important information of the game
     * @return informationPanel
     */
    public JPanel createInformationPanelGameOptions(){
        informationPanel = new JPanel(new GridBagLayout());
        Font arial = new Font("italic", 1, 18);
        turno = new JLabel("Siguiente en jugar: " + Gomoku.getGomoku().getTurn());
        nombreP1 = new JLabel("P1: " + p1Name);
        nombreP2 = new JLabel("P2: " + p2Name);
        colorP1 = new JLabel("ColorP1: " + Gomoku.getGomoku().getColor(p1Name));
        colorP2 = new JLabel("ColorP2: " + Gomoku.getGomoku().getColor(p2Name));
        tiempoP1 = new JLabel("Tiempo " + p1Name +": " + Gomoku.getGomoku().getPlayerTotalTime(p1Name) + " " + "Segundos.");
        tiempoP2 = new JLabel("Tiempo "+ p2Name +": " + Gomoku.getGomoku().getPlayerTotalTime(p2Name) + " " + "Segundos.");
        puntuacionJugador1 = new JLabel("Score P1: " + "Pendiente");
        puntuacionJugador2 = new JLabel("Score P2: " + "Pendiente");
        turno.setFont(arial);
        nombreP1.setFont(arial);
        nombreP2.setFont(arial);
        colorP1.setForeground(hexToColor(Gomoku.getGomoku().getColor(p1Name)));
        colorP2.setForeground(hexToColor(Gomoku.getGomoku().getColor(p2Name)));
        tiempoP1.setFont(arial);
        tiempoP2.setFont(arial);
        puntuacionJugador1.setFont(arial);
        puntuacionJugador2.setFont(arial);

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
        gbc.gridy = 4;
        informationPanel.add(puntuacionJugador1, gbc);
        gbc.gridy = 5;
        informationPanel.add(tiempoP1, gbc);
        gbc.gridy = 6;
        informationPanel.add(nombreP2, gbc);
        gbc.gridy = 7;
        informationPanel.add(colorP2, gbc);
        gbc.gridy = 8;
        informationPanel.add(puntuacionJugador2, gbc);
        gbc.gridy = 9;
        informationPanel.add(tiempoP2, gbc);
        
        return informationPanel;
    }

    /**
     * Prepares the actions of the board game
     *  
     * */
    public void prepareActionsBoardGame(){
        
        saveGomoku.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                saveOption();
            }
        });

        resetGomoku.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                resetOption(); 
            }
        });
        finishGomoku.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                finishOption();
            }
        });
    }

    private JFileChooser save;
    /**
     * Shows the user a menu to save a file.
     * */
    public void saveOption(){
        
        File file = null;
        save = new JFileChooser();
        save.setFileSelectionMode(JFileChooser.FILES_ONLY);
        save.showSaveDialog(this);
        showFileName("Se guardo el archivo ", file);
        
    }

    /*
     * Actions for the reset button
     */
    public void resetOption(){
        this.remove(boardGame);
        this.remove(gameOptions);
        //board.setTurno();
        //board.restoreGame();
        //prepareElementsBoard();
        //refresh();
    }

    /*
     * Actions for the finish button
     */
    public void finishOption(){
        this.remove(boardGame);
        this.remove(gameOptions);
        mainMenu.setVisible(true);
    }

    public void changeColor() throws GomokuException{
        HashMap<String, Player> players = Gomoku.getGomoku().getPlayers();
        Set<String> playersNames = players.keySet();
        HashSet<Color> selectedColors = new HashSet<>();
        if (Gomoku.getGomoku().getOpponent().equals("pvp")) {
            for (String name : playersNames) {
                Player player = players.get(name);
                Color selectedColor = JColorChooser.showDialog(null, "Elige el color para tus piedras, " + name, null);
                while(selectedColor == null) {
                    JOptionPane.showMessageDialog(this, "No puedes seleccionar un color nulo", "Alerta", JOptionPane.ERROR_MESSAGE);
                    selectedColor = JColorChooser.showDialog(null, "Elige el color para tus piedras, " + name, null);
                }
                while (selectedColors.contains(selectedColor)) {
                    JOptionPane.showMessageDialog(this, "No puedes seleccionar un color ya en uso", "Alerta", JOptionPane.WARNING_MESSAGE);
                    selectedColor = JColorChooser.showDialog(null, "Elige el color para tus piedras, " + name, null);
    
                    while(selectedColor == null) {
                        JOptionPane.showMessageDialog(this, "No puedes seleccionar un color nulo", "Alerta", JOptionPane.ERROR_MESSAGE);
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
     * Selects the gameMode of Gomoku
     */
    public void selectGameMode(){
        JRadioButton normal = new JRadioButton("Normal");
        JRadioButton quickTime = new JRadioButton("QuickTime");
        JRadioButton piedrasLimitadas = new JRadioButton("Piedras Ilimitadas");
        ButtonGroup sizeGroup = new ButtonGroup();
        sizeGroup.add(normal);
        sizeGroup.add(quickTime);
        sizeGroup.add(piedrasLimitadas);
        Object[] options = {normal, quickTime, piedrasLimitadas};
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
        if (normal.isSelected()) {
            Gomoku.getGomoku().setGameMode("normal");
        } else if (quickTime.isSelected()) {
            Gomoku.getGomoku().setGameMode("quicktime");
            askForTime();
        } else if (piedrasLimitadas.isSelected()) {
            Gomoku.getGomoku().setGameMode("piedrasLimitadas");
            
        }
    }

    /**
     * Asks for the time limit of the game
     */
    public void askForTime(){
        Gomoku.getGomoku().setTime(Integer.parseInt(JOptionPane.showInputDialog("Ingrese el tiempo limite (En minutos)")));
    }

    /**
     * Prepares the actions of the mainMenu
     *  */
    public void prepareMenuActions(){
        newOption.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                try {
                    newOption();
                } catch (InvocationTargetException e1) {
                    e1.printStackTrace();
                } catch (GomokuException e1) {
                    e1.printStackTrace();
                }
            }
        });

        //hecha
        exitOption.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                confirmExit();
            }
        });
        
        //hecha
        changeSizeOption.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                changeSizeOption();
            }
        });

        loadOption.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                loadOption();
            }
        });
    }
    
    public void newOption() throws InvocationTargetException, GomokuException{
        Gomoku.getGomoku();
        Gomoku.getGomoku().setDimension(dimension);
        askForOpponent();
    }



    /**
     * Changes the size of the Gomoku's board
     */
    public void changeSizeOption(){
        dimension = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el numero de filas"));
        if(dimension < 10){
            dimension = 10;
        }
    }


    /**
     * Shows the user a menu to load a file
     */
    public void loadOption(){
        File file = null;
        load = new JFileChooser();
        load.setFileSelectionMode(JFileChooser.FILES_ONLY);
        showFileName("Se abrió el archivo ", file);
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

    public JPanel getGame(){
        return game;
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
    
    //NEW METHODS
    private void updateTokensOnBoard(){
        ArrayList<int[]> listOfPositionsOfTokens = Gomoku.getGomoku().getLastPositionTokens();
        if(listOfPositionsOfTokens.size() > 0){
            putTokenOnBoard(listOfPositionsOfTokens.get(0)[0],listOfPositionsOfTokens.get(0)[1]);
            listOfPositionsOfTokens.remove(0);
        }
        deleteTokensOnBoard(listOfPositionsOfTokens);   
    }
  
    private void deleteTokensOnBoard(ArrayList<int[]> positions){
        for(int[] position: positions){
            int buttonPosition = getComponentNumber(dimension, position[0], position[1]);
            buttonPosition--;
            JButton boton = (JButton) game.getComponent(buttonPosition);
            boton.setBackground(null);
            boton.setText(null);
            game.remove(buttonPosition);
            game.add(boton, buttonPosition);
            game.repaint();
        }
    }
    
    private void putTokenOnBoard(int xPos, int yPos){
        int buttonPosition = getComponentNumber(dimension, xPos, yPos);
        buttonPosition--;
        JButton boton = (JButton) game.getComponent(buttonPosition);
        boton.setBackground(Gomoku.getGomoku().getToken(xPos, yPos).getColor());
        setButtonBackground(boton, xPos, yPos);
        game.remove(buttonPosition);
        game.add(boton, buttonPosition);
        game.repaint();
    }

    


    private JButton setButtonBackground(JButton boton, int xPos, int yPos){
        if(Gomoku.getGomoku().getToken(xPos, yPos).getIdentifier() == 'H'){
            boton.setText("H");
        }
        else if(Gomoku.getGomoku().getToken(xPos, yPos).getIdentifier() == 'T'){
            boton.setText("T");
        }
        return boton;
    }
    

    /**
     * Creates the board
     * @param fila
     * @param columna
     * @param color
     */
    private JButton createBoton(int fila, int columna){
        JButton boton = new JButton();
        boton.setPreferredSize(new Dimension(40, 40));
        boton.setBackground(Color.WHITE);
        boton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        boton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                if(Gomoku.getGomoku().validPlay(fila, columna)){
                	Gomoku.getGomoku().play(fila, columna);
                    updateTokensOnBoard();
                    refreshInformationPanel();
                    refreshTokens();
                }
                else{
                    JOptionPane.showMessageDialog(null, "Movimiento Invalido", "Información", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        }); 
        return boton;
    }

}