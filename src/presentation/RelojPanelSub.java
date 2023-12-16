/**
 * 
 */
package presentation;

import javax.swing.*;

import domain.Gomoku;
import domain.GomokuException;
import domain.Log;
import domain.TimePassed;

import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RelojPanelSub extends JPanel {
    private JLabel tiempoLabel;
    private Timer timer;
    private TimePassed timePassed;
    
    /**
     * Constructor for relojPanelSub
     * @param timer is the timer
     * @param timePassed is the time elapsed
     */
    public RelojPanelSub(Timer timer, TimePassed timePassed) {
        this.timer = timer;
        this.timePassed = timePassed;

        tiempoLabel = new JLabel();
        add(tiempoLabel);

        timer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
					actualizarTiempo();
				} catch (HeadlessException e1) {
					Log.record(e1);
				} catch (GomokuException e1) {
					Log.record(e1);
				}
            }
        });
    }
    
    /**
     * Updates the time
     * @throws GomokuException 
     * @throws HeadlessException 
     */
    private void actualizarTiempo() throws HeadlessException, GomokuException {
        // Decrementar la variable de segundos solo si no está en pausa
        if (timePassed.getTime() > 0) {
            timePassed.sumTime(-1);
            // Crear un objeto Date basado en los segundos transcurridos
            Date tiempo = new Date(timePassed.getTime() * 1000);

            // Formatear el tiempo para mostrar minutos y segundos
            SimpleDateFormat sdf = new SimpleDateFormat("mm:ss");
            String tiempoFormateado = sdf.format(tiempo);

            // Actualizar el texto del JLabel
            tiempoLabel.setText(tiempoFormateado);
            if(timePassed.getTime() == 0) {
            	JOptionPane.showMessageDialog(null, "Se acabó el tiempo", "Información", JOptionPane.INFORMATION_MESSAGE);
            	Gomoku.getGomoku().timeValidation();
            	GomokuGUI.validateWinCondition();
            }
        }
        
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