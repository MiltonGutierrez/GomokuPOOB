package presentation;
import javax.swing.*;

import domain.TimePassed;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RelojPanelSum extends JPanel {
    private JLabel tiempoLabel;
    private Timer timer;
    private TimePassed timePassed;
    
    /**
     * Is the constructor for RelojPanelSum
     * @param timer is the timer
     * @param timePassed is the time elapsed
     */
    public RelojPanelSum(Timer timer, TimePassed timePassed) {
        this.timer = timer;
        this.timePassed = timePassed;

        tiempoLabel = new JLabel();
        add(tiempoLabel);

        timer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarTiempo();
            }
        });
    }
    
    /**
     * Updates the time
     */
    private void actualizarTiempo() {
        // Incrementar la variable de segundos
    	timePassed.sumTime(1);
        // Crear un objeto Date basado en los segundos transcurridos
        Date tiempo = new Date(timePassed.getTime() * 1000);

        // Formatear el tiempo para mostrar minutos y segundos
        SimpleDateFormat sdf = new SimpleDateFormat("mm:ss");
        String tiempoFormateado = sdf.format(tiempo);
        
        // Actualizar el texto del JLabel
        tiempoLabel.setText(tiempoFormateado);
    }
    
    /**
     * Resets the panel
     */
    public void reiniciarPanel() {
        // Reiniciar la variable de segundos
        timePassed.setTime(0);

        // Actualizar el JLabel
        tiempoLabel.setText("00:00");

        // Reiniciar el temporizador
        if (!timer.isRunning()) {
            timer.start();
        }
    }
}

