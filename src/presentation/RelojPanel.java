/**
 * 
 */
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

    public RelojPanel() {
        tiempoLabel = new JLabel();
        add(tiempoLabel);

        // Inicializar la variable de segundos
        segundosTranscurridos = 0;

        // Configuración del temporizador para actualizar cada segundo
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarTiempo();
            }
        });
        timer.start();
    }

    private void actualizarTiempo() {
        // Incrementar la variable de segundos
        segundosTranscurridos++;

        // Crear un objeto Date basado en los segundos transcurridos
        Date tiempo = new Date(segundosTranscurridos * 1000);

        // Formatear el tiempo para mostrar minutos y segundos
        SimpleDateFormat sdf = new SimpleDateFormat("mm:ss");
        String tiempoFormateado = sdf.format(tiempo);

        // Actualizar el texto del JLabel
        tiempoLabel.setText(tiempoFormateado);
    }

    // Método para pausar el tiempo
    public void pausarTiempo() {
        if (timer.isRunning()) {
            timer.stop();
        }
    }

    // Método para reiniciar el panel
    public void reiniciarPanel() {
        // Reiniciar la variable de segundos
        segundosTranscurridos = 0;
        // Actualizar el JLabel
        tiempoLabel.setText("00:00");

        // Reiniciar el temporizador
        if (!timer.isRunning()) {
            timer.start();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame frame = new JFrame("Reloj");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(200, 100);
                RelojPanel relojPanel = new RelojPanel();
                frame.setContentPane(relojPanel);

                // Botón para pausar el tiempo
                JButton pausarButton = new JButton("Pausar");
                pausarButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        relojPanel.pausarTiempo();
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

                frame.add(pausarButton);
                frame.add(reiniciarButton);

                frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
                frame.setVisible(true);
            }
        });
    }
}
