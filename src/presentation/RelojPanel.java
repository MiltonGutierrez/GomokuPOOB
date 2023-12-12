package presentation;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RelojPanel extends JPanel {
    private JLabel tiempoLabel;
    private Integer segundosTranscurridos;
    private Timer timer;
    private boolean enPausa;
    private String gameMode;

    public RelojPanel(Timer timer, String gameMode, Integer time) {
        System.out.println(time);
        tiempoLabel = new JLabel();
        this.gameMode = gameMode;
        add(tiempoLabel);

        // Inicializar la variable de segundos
        segundosTranscurridos = time;

        enPausa = false;

        // Configuración del temporizador para actualizar cada segundo
        timer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarTiempo();
            }
        });
    }

    public RelojPanel(Timer timer, String gameMode) {
        this.timer = timer;
        this.gameMode = gameMode;
        tiempoLabel = new JLabel();
        add(tiempoLabel);

        // Inicializar la variable de segundos
        segundosTranscurridos = 0;
        enPausa = false;

        // Configuración del temporizador para actualizar cada segundo
        timer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarTiempo();
            }
        });
    }

    private void actualizarTiempo() {
        if (!enPausa) {
            if (gameMode.equals("quicktime")) {
                System.out.println("si");
                if (segundosTranscurridos > 0) {
                    segundosTranscurridos--;
                } else {
                    // Si el contador llega a cero, detener el temporizador
                    timer.stop();
                }
            } else {
                segundosTranscurridos++;
            }

            // Crear un objeto Date basado en los segundos transcurridos
            Date tiempo = new Date(segundosTranscurridos * 1000);

            // Formatear el tiempo para mostrar minutos y segundos
            SimpleDateFormat sdf = new SimpleDateFormat("mm:ss");
            String tiempoFormateado = sdf.format(tiempo);

            // Actualizar el texto del JLabel
            tiempoLabel.setText(tiempoFormateado);
        }
    }

    // Método para pausar o reanudar el tiempo
    public void pausarOReanudarTiempo() {
        enPausa = !enPausa;
        if (enPausa) {
            // Si está en pausa, detener el temporizador
            timer.stop();
        } else {
            // Si se reanuda, iniciar el temporizador solo si no está en pausa
            if (!timer.isRunning()) {
                timer.start();
            }
        }
    }

    // Método para reiniciar el panel
    public void reiniciarPanel() {
        // Reiniciar la variable de segundos
        segundosTranscurridos = 0;
        // Actualizar el JLabel
        tiempoLabel.setText("00:00");

        // Reiniciar el temporizador
        if (timer.isRunning()) {
            timer.stop();
        }
        enPausa = false;
        timer.start();
    }
}

