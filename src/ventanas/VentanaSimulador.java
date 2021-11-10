package ventanas;

import java.awt.EventQueue;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import simulador.*;
import simulador.Semaforo.Color;
import simulador.Senal.Tipo;

import javax.swing.JList;

import java.awt.BorderLayout;

public class VentanaSimulador extends JFrame {

	private static final long serialVersionUID = 1L;
	
	/*Atributos*/
	
	private ArrayList<Obstaculo> listaObs = new ArrayList<Obstaculo>();
	private Coche miCoche;
	
	private JPanel contentPane;
	

	/*Main*/
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaSimulador frame = new VentanaSimulador();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	

	/**Constructor de ventana*/
	
	public VentanaSimulador() {
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		ImageIcon fondo1 = new ImageIcon(getClass().getResource("../simulador/img/FONDO COCHE (1).jpg"));
		contentPane.setLayout(null);
		
		
		ImageIcon cohce = new ImageIcon(getClass().getResource("../simulador/img/coche.png"));
		contentPane.setLayout(null);
		//JPanel.add(image, BorderLayout.NORTH);
		JLabel label2 = new JLabel(cohce);
		label2.setBounds(177, 189, 33, 53);
		contentPane.add(label2);
		//JPanel.add(image, BorderLayout.NORTH);
		JLabel label = new JLabel(fondo1);
		label.setBounds(58, 10, 313, 253);
		contentPane.add(label);
		
	}
	
	
	/**Método que establezca la lógica del simulador, es decir, como va a reaccionar el 
	 * coche ante los diferentes obstáculos que se le presenten
	 */
	
	public void cocheReaccion() {
		
		Obstaculo oDetectado = null;
		
		for (Obstaculo o: listaObs) {
			//miCoche.getSENSOR().detectaObs(o);
			oDetectado = o;
		}
		
		if (oDetectado instanceof Semaforo) {
			Semaforo semaf = (Semaforo) oDetectado;
			if (semaf.getColor() == Color.VERDE) {
				
			} else if (semaf.getColor() == Color.NARANJA) {
				
			} else if (semaf.getColor() == Color.ROJO) {
				
			}
		}
		
		if (oDetectado instanceof Peaton) {
			
		}
		
		if (oDetectado instanceof Senal) {
			Senal senal = (Senal) oDetectado;
			if (senal.getTipo() == Tipo.STOP) {

			} else if (senal.getTipo() == Tipo.CEDA) {
				
			} else if (senal.getTipo() == Tipo.SENTIDO_OBLIGATORIO) {
				
			}
		}
	}
}
