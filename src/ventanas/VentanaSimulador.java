package ventanas;

import java.awt.*;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.border.EmptyBorder;

import simulador.*;
import simulador.Semaforo.Color;
import simulador.Senal.Tipo;

import javax.swing.JList;

public class VentanaSimulador extends JFrame {

	private static final long serialVersionUID = 1L;
	private static Rectangle TAM_VENT = new Rectangle(1000, 600);
	
	/*Atributos*/
	protected JList listaObst;
	protected JComboBox senalesCombo;
	private ArrayList<Obstaculo> listaObs = new ArrayList<Obstaculo>();
	private Coche miCoche;

	/*Main*/
	public static void main(String[] args) {
		VentanaSimulador ventana= new VentanaSimulador();
		ventana.setVisible(true);
	}
	
	/**Constructor de ventana*/
	public VentanaSimulador() {
		
		setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );
		setTitle( "Coche Autonomo" );
		setSize(TAM_VENT.width,TAM_VENT.height);
		setLocationRelativeTo(null);
		
		//Crear contenedor
		Container cp = this.getContentPane();
		cp.setLayout(null);
		//Crear panel para la pantalla del simulador de coche
		JPanel simuladorPane= new JPanel();
		simuladorPane.setLayout(null);
		
		Image fondoImg = new ImageIcon(getClass().getResource("../simulador/img/FONDO COCHE (1).jpg")).getImage();
		ImageIcon fondo = new ImageIcon(fondoImg.getScaledInstance(TAM_VENT.width,TAM_VENT.height, Image.SCALE_SMOOTH));
		
		Image cocheImg = new ImageIcon(getClass().getResource("../simulador/img/coche.png")).getImage();
		ImageIcon coche = new ImageIcon(cocheImg.getScaledInstance(100, 150, Image.SCALE_SMOOTH));
		
		//JPanel.add(image, BorderLayout.NORTH);
		JLabel label2 = new JLabel(coche);
		label2.setBounds(279, 39, 515, 513);
		cp.add(label2);
		
		//Crear lista de los obst�culos
		String[] obstaculos = {"Peat�n", "Otro Coche","Sem�foro","STOP", "Ceda", "Sentido Obligatorio"};
		JList lista= new JList(obstaculos);
		
		//Doble panel que contiene el panel y la lista anterior
		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,lista,simuladorPane);
		
		cp.add(splitPane);
		//JPanel.add(image, BorderLayout.NORTH);
		JLabel label = new JLabel(fondo);
		label.setBounds(10, 10, 966, 542);
		cp.add(label);
		
	}
	public void cocheReaccion() {
		
		Obstaculo oDetectado = null;
		OtroCoche cocheCerca = null;
		
		for (Obstaculo o: listaObs) {
			miCoche.getSR().detectaObs(o);
			if (o instanceof OtroCoche) {
				cocheCerca = (OtroCoche)o;
			} else {
				oDetectado = o;
			}
			
		}
		
		if (oDetectado instanceof Semaforo) {
			Semaforo semaf = (Semaforo) oDetectado;
			if (semaf.getColor() == Color.VERDE) {
				
			} else if ((semaf.getColor() == Color.NARANJA) || (semaf.getColor() == Color.ROJO)) {
				
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
		
		if ((cocheCerca.getY() < miCoche.getY()) && (cocheCerca.getX() == miCoche.getX())) {
			
		}
	}
}
