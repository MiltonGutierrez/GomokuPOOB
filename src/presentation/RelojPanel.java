package presentation;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RelojPanel extends JPanel {
    private JLabel tiempoLabel;
    private int segundosTranscurridos;
    private Timer timer;
    private boolean enPausa;

    public RelojPanel(Timer timer) {
        this.timer = timer;
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
    	if(timer.isRunning()) {
    		enPausa = false;
    	}
    	else {
    		enPausa = true;
    	}
    	
        if (!enPausa) {
            // Incrementar la variable de segundos solo si no está en pausa
            segundosTranscurridos++;

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
        enPausa = true;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame frame = new JFrame("Reloj");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(200, 100);

                // Crear un Timer con una acción predeterminada (puedes personalizarlo según tus necesidades)
                Timer timer = new Timer(1000, null);

                // Crear el panel proporcionando el Timer como parámetro
                RelojPanel relojPanel = new RelojPanel(timer);

                // Botón para pausar o reanudar el tiempo
                JButton pausarReanudarButton = new JButton("Pausar/Reanudar");
                pausarReanudarButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        relojPanel.pausarOReanudarTiempo();
                    }
                });

                // Botón para reiniciar el panel
                JButton reiniciarButton = new JButton("Reiniciar");
                reiniciarButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        relojPanel.reiniciarPanel();
                    }
                });

                frame.add(relojPanel);
                frame.add(pausarReanudarButton);
                frame.add(reiniciarButton);

                frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
                frame.setVisible(true);

                // Iniciar el temporizador después de que la interfaz esté visible
                timer.start();
            }
        });
    }
}

