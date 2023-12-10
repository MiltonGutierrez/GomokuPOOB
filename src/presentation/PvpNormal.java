package presentation;
import java.awt.event.*;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.ArrayList;
import java.awt.*;
import javax.swing.*;

import domain.Gomoku;
import domain.Log;

public class PvpNormal extends JPanel {
    private JPanel gameOptions;
    private JPanel boardGame;

    private static PvpNormal pvpNormal = null;

    /**
     * Creates an instance of Gomoku
     */
    private PvpNormal() {
        this.setOpaque(true);
       
    }

    public static PvpNormal getPvpNormal() {
        if (pvpNormal == null) {
            pvpNormal = new PvpNormal();
            pvpNormal.prepareElementsGameBoardPVP();
            
        }
        return pvpNormal;
    }

    /**
     * Prepares the elements of the board
     *  */
    public void prepareElementsGameBoardPVP() {
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

    private JPanel game;
    private JPanel information;
    private JPanel tokensLeft;
    /**
     * Prepares the elements of the game
     *  */
    public void prepareElementsGame() {
        boardGame.setLayout(new BorderLayout());
        boardGame.setSize(this.getWidth() - gameOptions.getWidth(), this.getHeight());
        boardGame.setBackground(new Color(113, 197, 232,128));
    
        game = createBoardGame();
    
        // Create a panel to hold the game component in the center
        JPanel gameWrapper = new JPanel();
        gameWrapper.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 30));
        gameWrapper.setBackground(new Color(113, 197, 232,128));
        gameWrapper.add(game);

        boardGame.add(gameWrapper, BorderLayout.CENTER);
        
        boardGame.validate();
        boardGame.repaint();
    }


    public JPanel createBoardTokenInformation() {
        tokensLeft = new JPanel();
        tokensLeft.setLayout(new GridBagLayout());
        tokensLeft.setBackground(new Color(166, 220, 242,128));
        tokensLeft.setPreferredSize(new Dimension(200, 200));
        return tokensLeft;
    }


    /*
     * Creates the board of Gomoku
     *  */
    private JPanel createBoardGame(){
        game = new JPanel();
        game.setBackground(Color.WHITE);
        game.setLayout(new GridLayout(GomokuGUI.returnDimension(), GomokuGUI.returnDimension()));
        game.setPreferredSize(new Dimension(400, 400));
        for (int fila = 0; fila < GomokuGUI.returnDimension(); fila++) {
          for (int columna = 0; columna < GomokuGUI.returnDimension(); columna++) {
            JButton boton = createBoton(fila, columna);
            boton.setPreferredSize(new Dimension(30, 30));
            game.add(boton);
          }
        }

        return game;
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
            int buttonPosition = getComponentNumber(Gomoku.getGomoku().getDimension(), position[0], position[1]);
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
        int buttonPosition = getComponentNumber(Gomoku.getGomoku().getDimension(), xPos, yPos);
        buttonPosition--;
        JButton boton = (JButton) game.getComponent(buttonPosition);
        boton.setBackground(Gomoku.getGomoku().getLastColor());
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
     * 
     * @param dimension
     * @param x
     * @param y
     * @return
     */
    public int getComponentNumber(int dimension, int x, int y) {
        return  x * dimension + y + 1;
    }

    public void refreshTokens(){
        boardGame.validate();
        boardGame.repaint();
    }
    public void refreshInformationPanel(){
        gameOptions.removeAll();
        informationPanel.removeAll();
        gameOptions.remove(informationPanel);
        informationPanel = createInformationPanelGameOptions();
        gameOptions.add(optionsPanel, BorderLayout.EAST);
        gameOptions.add(informationPanel, BorderLayout.WEST);
        gameOptions.add(logoPanel, BorderLayout.CENTER);
        Gomoku.getGomoku().startPlayerTimer(Gomoku.getGomoku().getTurn());;
        this.revalidate();
        this.repaint();
        validateWinCondition();
    }
    /**
     * Validates if there is a winner
     */
    public void validateWinCondition(){
        if(Gomoku.getGomoku().getGomokuFinished()){ 
            JOptionPane.showMessageDialog(this, "Juego Terminado\n" + Gomoku.getGomoku().getWinner(), "Información", JOptionPane.INFORMATION_MESSAGE);
        }
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
        save = new JFileChooser();
        save.setFileSelectionMode(JFileChooser.FILES_ONLY);
        save.showSaveDialog(this);
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
    }
    private JPanel optionsPanel;
    /**
     * Prepares the elements of the panel with option buttons
     */
    private JPanel createOptionsPanelGameOptions() {
        optionsPanel = new JPanel(new GridBagLayout());
        optionsPanel.setBackground(new Color(224,62,82,128));
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

    private JPanel informationPanel;
    private JLabel turno;
    private JLabel nombreP1;
    private JLabel nombreP2;
    private JLabel colorP1;
    private JLabel colorP2;
    private JLabel tiempoP1;
    private JLabel tiempoP2;
    private JLabel puntuacionJugador1;
    private JLabel puntuacionJugador2;
    /**
     * Prepares the elements of the panel with labels with important information of the game
     * @return informationPanel
     */
    public JPanel createInformationPanelGameOptions(){
        informationPanel = new JPanel(new GridBagLayout());
        Font arial = new Font("italic", 1, 18);
        turno = new JLabel("Siguiente en jugar: " + Gomoku.getGomoku().getTurn());
        nombreP1 = new JLabel("P1: " + GomokuGUI.returnP1());
        nombreP2 = new JLabel("P2: " + GomokuGUI.returnP2());
        colorP1 = new JLabel("ColorP1: " + Gomoku.getGomoku().getColor(GomokuGUI.returnP1()));
        colorP2 = new JLabel("ColorP2: " + Gomoku.getGomoku().getColor(GomokuGUI.returnP2()));
        tiempoP1 = new JLabel("Tiempo " + GomokuGUI.returnP1() +": " + Gomoku.getGomoku().getPlayerTotalTime(GomokuGUI.returnP1()) + " " + "Segundos.");
        tiempoP2 = new JLabel("Tiempo "+ GomokuGUI.returnP2() +": " + Gomoku.getGomoku().getPlayerTotalTime(GomokuGUI.returnP2()) + " " + "Segundos.");
        puntuacionJugador1 = new JLabel("Score P1: " + "Pendiente");
        puntuacionJugador2 = new JLabel("Score P2: " + "Pendiente");
        turno.setFont(arial);
        nombreP1.setFont(arial);
        nombreP2.setFont(arial);
        colorP1.setForeground(hexToColor(Gomoku.getGomoku().getColor(GomokuGUI.returnP1())));
        colorP2.setForeground(hexToColor(Gomoku.getGomoku().getColor(GomokuGUI.returnP2())));
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
    public static Color hexToColor(String hex) {
        int r = Integer.parseInt(hex.substring(1, 3), 16);
        int g = Integer.parseInt(hex.substring(3, 5), 16);
        int b = Integer.parseInt(hex.substring(5, 7), 16);
        return new Color(r, g, b);
    }
    private ImageIcon logo;
    private JPanel logoPanel;
    /**
     * Prepares the elements of the panel with labels with the logo of the game
     *  */
    public JPanel createLogoPanelGameOptions() {
        JLabel logoLabel = new JLabel();
        logoLabel.setBounds(10, 80, 150, 150);
        logoPanel = new JPanel();
        logoPanel.setBackground(new Color(224,62,82,128));
        logoPanel.add(logoLabel, BorderLayout.CENTER);
        URL url = getClass().getResource("/presentation/resources/logo.png");
        if(url != null){
            logo = new ImageIcon(url);
            logoLabel.setIcon(new ImageIcon(logo.getImage().getScaledInstance(logoLabel.getWidth(), logoLabel.getHeight(), Image.SCALE_SMOOTH)));
        }
        System.out.println("Hola");
        return logoPanel;
    }
    

}
