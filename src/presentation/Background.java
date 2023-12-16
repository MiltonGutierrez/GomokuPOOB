package presentation;

import java.awt.*;
import java.net.URL;

import javax.imageio.*;
import javax.swing.*;

import domain.Log;

public class Background extends JPanel {
	/**
	 * Constructor for the background panel
	 * @param height is the height to apply
	 * @param width is the width to apply
	 */
    public Background(int height, int width){
        this.setOpaque(true);
        this.setLayout(new BorderLayout());
        this.setSize(height, width);
    }
    
    /**
     * Is the image of the panel
     * @param is the image
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        Image image = null;
        // Carga la imagen
        URL url = getClass().getResource("/presentation/resources/fondoPrincipal.png");
        if (url != null) {
            try{
                image = ImageIO.read(url);
            }catch(Exception e){
                Log.record(e);
            }
            // Dibuja la imagen en el fondo
            g2d.drawImage(image, 0, 0, getWidth(), getHeight(), null);
        }
        g2d.dispose();
    }


}
