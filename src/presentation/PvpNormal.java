package presentation;
import java.awt.event.*;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.ArrayList;
import java.awt.*;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import domain.TimePassed;
import domain.Gomoku;
import domain.GomokuException;
import domain.Log;

public class PvpNormal extends JPanel{
   
	protected JPanel gameOptions;
    protected JPanel boardGame;
    private static PvpNormal pvpNormal = null;
    protected JButton saveGomoku;
    protected JButton resetGomoku;
    protected JButton finishGomoku;
    protected JPanel game;
    protected JPanel information;
    private JFileChooser save;
    protected JPanel optionsPanel;
    protected JPanel informationPanel;
    protected JLabel turno;
    protected JLabel nombreP1;
    protected JLabel nombreP2;
    protected JLabel colorP1;
    protected JLabel colorP2;
    protected JLabel puntuacionJugador1;
    protected JLabel puntuacionJugador2;
    protected JPanel tiempoP1 = new RelojPanelSum(Gomoku.getGomoku().getTimer(Gomoku.getGomoku().getP1(), "timerT"), Gomoku.getGomoku().getTimeP1("timeT"));
    protected JPanel tiempoP2 = new RelojPanelSum(Gomoku.getGomoku().getTimer(Gomoku.getGomoku().getP2(), "timerT"), Gomoku.getGomoku().getTimeP2("timeT"));
    private ImageIcon logo;
    protected JPanel logoPanel;
    protected JPanel gameWrapper;

    /**
     * Creates an instance of PvpNormal panel
     */
    public PvpNormal() {
        this.setOpaque(true);
    }

    /**
     * Returns the panel for pvpNormal and creates one if null
     * @return the panel for pvpNormal
     * @throws GomokuException
     */
    public static PvpNormal getPvpNormal(){
        if (pvpNormal == null) {
            pvpNormal = new PvpNormal();
            pvpNormal.prepareElementsGameBoardPVP();
        }
        return pvpNormal;
    }

    /**
     * Prepares the elements of the board
     * @throws GomokuException 
     *  */
    public void prepareElementsGameBoardPVP(){
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
    
    /**
     * Prepares the elements of the game options
     * @throws GomokuException 
     *  */
    public void prepareElementsGameOptionsPVP(){
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
     * Prepares the elements of the game
     * 
     */
    public void prepareElementsGame() {
        boardGame.setLayout(new BorderLayout());
        boardGame.setSize(this.getWidth() - gameOptions.getWidth(), this.getHeight());
        boardGame.setBackground(new Color(113, 197, 232,128));
        game = createBoardGame();
        // Create a panel to hold the game component in the center
        gameWrapper = new JPanel();
        gameWrapper.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 30));
        gameWrapper.setBackground(new Color(113, 197, 232,128));
        gameWrapper.add(game);
        boardGame.add(gameWrapper, BorderLayout.CENTER);
        boardGame.validate();
        boardGame.repaint();
    }

    /*
     * Creates the board of Gomoku
     */
    public JPanel createBoardGame(){
        game = new JPanel();
        game.setBackground(Color.WHITE);
        game.setLayout(new GridLayout(Gomoku.getGomoku().getDimension(), Gomoku.getGomoku().getDimension()));
        game.setPreferredSize(new Dimension(400, 400));
        for (int fila = 0; fila < Gomoku.getGomoku().getDimension(); fila++) {
          for (int columna = 0; columna < Gomoku.getGomoku().getDimension(); columna++) {
            setBoton(fila, columna, Gomoku.getGomoku().getBox(fila, columna));
            game.add(Gomoku.getGomoku().getBox(fila, columna));
          }
        }
        return game;
    }
    
    /**
     * Creates the board
     * @param fila is the row to create
     * @param columna is the column to create
     * @param color is the color of the button
     */
    private void setBoton(int fila, int columna, domain.Box boton) {
    	boton.setPreferredSize(new Dimension(30, 30));
        boton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
            	addSound1();
                if(Gomoku.getGomoku().validPlay(fila, columna)){
					Gomoku.getGomoku().play(fila, columna);
                	deleteTokensOnBoard(Gomoku.getGomoku().getLastPositionTokens());
					refreshInformationPanel();
					refreshTime();
					GomokuGUI.validateWinCondition();
                }
                else{
					GomokuGUI.validateWinCondition();
                    JOptionPane.showMessageDialog(null, "Movimiento Invalido", "Informacion", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        }); 
        return ;
    }
    
    /**
     * Deletes the selected tokens
     * @param positions are the positions to delete
     */
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
    
    
    /**
     * Returns the component number
     * @param dimension is the dimension of the board
     * @param x row
     * @param y column
     * @return component number
     */
    public int getComponentNumber(int dimension, int x, int y) {
        return  x * dimension + y + 1;
    }
    
    /**
     * Refresh the time of the board
     */
    public void refreshTime(){
        boardGame.validate();
        boardGame.repaint();
    }
    /**
     * Refresh the information panel
     * @throws GomokuException
     */
    public void refreshInformationPanel(){
        gameOptions.removeAll();
        informationPanel.removeAll();
        gameOptions.remove(informationPanel);
        informationPanel = createInformationPanelGameOptions();
        gameOptions.add(optionsPanel, BorderLayout.EAST);
        gameOptions.add(informationPanel, BorderLayout.WEST);
        gameOptions.add(logoPanel, BorderLayout.CENTER);
        this.revalidate();
        this.repaint();
    }


    /**
     * Prepares the actions of the board game
     *  
     */
    public void prepareActionsBoardGame(){
        
        saveGomoku.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
            	addSound();
                try {
					saveOption();
				} catch (GomokuException e1) {
					Log.record(e1);
				}
            }
        });

        resetGomoku.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
            	addSound();
                resetOption(); 
            }
        });
        finishGomoku.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
            	addSound();
                try {
					finishOption();
				} catch (GomokuException e1) {
					Log.record(e1);
				}
            }
        });
    }
    

    /**
     * Shows the user a menu to save a file.
     * @throws GomokuException 
     */
    public void saveOption() throws GomokuException{
        Gomoku.getGomoku().save(fileToSave());
    }
    
    
    /*
     * Actions for the reset button
     */
    public void resetOption(){
        try {
			Gomoku.getGomoku().resetAll();
			refreshInformationPanel();
			refreshBoard();
		} catch (GomokuException e) {
			Log.record(e);
		}
        this.repaint();
    }

    /*
     * Actions for the finish button
     */
    public void finishOption() throws GomokuException{
    	this.removeAll();
        GomokuGUI.finishButtonNormal();
        pvpNormal = null;
    }
    
    /**
     * Prepares the elements of the panel with option buttons
     */
    protected JPanel createOptionsPanelGameOptions() {
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
    
    /**
     * Prepares the elements of the panel with labels with important information of the game
     * @return informationPanel
     * @throws GomokuException 
     */
    public JPanel createInformationPanelGameOptions(){
        informationPanel = new JPanel(new GridBagLayout());
        Font arial = new Font("italic", 1, 18);
        turno = new JLabel("Siguiente en jugar: " + Gomoku.getGomoku().getTurn());
        nombreP1 = new JLabel("P1: " + Gomoku.getGomoku().getP1());
        nombreP2 = new JLabel("P2: " + Gomoku.getGomoku().getP2());
        try {
        	colorP1 = new JLabel("ColorP1: " + Gomoku.getGomoku().getColor(Gomoku.getGomoku().getP1()));
            colorP2 = new JLabel("ColorP2: " + Gomoku.getGomoku().getColor(Gomoku.getGomoku().getP2()));
            puntuacionJugador1 = new JLabel("Score P1: " + GomokuGUI.getPuntuacion(Gomoku.getGomoku().getP1()));
            puntuacionJugador2 = new JLabel("Score P2: " + GomokuGUI.getPuntuacion(Gomoku.getGomoku().getP2()));
            colorP1.setForeground(hexToColor(Gomoku.getGomoku().getColor(Gomoku.getGomoku().getP1())));
            colorP2.setForeground(hexToColor(Gomoku.getGomoku().getColor(Gomoku.getGomoku().getP2())));
        }
        catch(GomokuException e) {
        	JOptionPane.showMessageDialog(null, e, "Advertencia", JOptionPane.INFORMATION_MESSAGE);
        	Log.record(e);
        }
        
        turno.setFont(arial);
        nombreP1.setFont(arial);
        nombreP2.setFont(arial);
        
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
     * Formats the hexadecimal color to a color rgb
     * @param hex is the color
     * @return Color 
     */
    public static Color hexToColor(String hex) {
        int r = Integer.parseInt(hex.substring(1, 3), 16);
        int g = Integer.parseInt(hex.substring(3, 5), 16);
        int b = Integer.parseInt(hex.substring(5, 7), 16);
        return new Color(r, g, b);
    }
    
    
    /**
     * Prepares the elements of the panel with labels with the logo of the game
     */
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
        return logoPanel;
    }
    
    /**
     * Makes the panel visible
     */
    protected void setVisible() {
    	this.setVisible(true);
    	pvpNormal.repaint();
    }
    
    /**
     * Makes the panel invisible
     */
    protected void setInvisible() {
    	this.setVisible(false);
    	pvpNormal.repaint();
    }
    
    /**
     * Shows the user a menu to save a file.
     */
    public File fileToSave() {
        File file = null;
        save = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Archivos .dat", "dat");
        save.setFileFilter(filter);
        save.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int result = save.showSaveDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            file = save.getSelectedFile();
            if (!file.getName().endsWith(".dat")) {
                String filename = file.getAbsolutePath() + ".dat";
                file = new File(filename);
            }
        }
        return file;
    }
    
    /**
     * Refresh the board when reset
     */
    public void refreshBoard() {
    	game.removeAll();
    	boardGame.removeAll();
    	prepareElementsGame();
        this.revalidate();
        this.repaint();
    }
    
    /**
     * Adds a sound for the button
     * @param boton
     */
    public void addSound() {
        try {
            URL soundUrl = getClass().getResource("/presentation/resources/boton.wav");
            if (soundUrl == null) {
                throw new RuntimeException("No se pudo encontrar el archivo de sonido: boton.wav");
            }
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundUrl);
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (Exception e) {
            Log.record(e);
        }
    }
    
    /**
     * Adds a sound for the button
     * @param boton
     */
    public void addSound1() {
        try {
            URL soundUrl = getClass().getResource("/presentation/resources/piedra.wav");
            if (soundUrl == null) {
                throw new RuntimeException("No se pudo encontrar el archivo de sonido: piedra.wav");
            }
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundUrl);
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (Exception e) {
            Log.record(e);
        }
    }
    
    
    
    

}