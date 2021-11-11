package ventanas;

import java.awt.*;
import java.awt.EventQueue;
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
		
		//Crear contenedor
		Container cp = this.getContentPane();
		//Crear panel para la pantalla del simulador de coche
		JPanel simuladorPane= new JPanel();
		simuladorPane.setLayout(null);
		//Crear lista de los obstáculos
		String[] obstaculos = {"Peatón", "Otro Coche","Semáforo","STOP", "Ceda", "Sentido Obligatorio"};
		JList lista= new JList(obstaculos);
		
		//Doble panel que contiene el panel y la lista anterior
		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,lista,simuladorPane);
		
		
		cp.add(splitPane);
		setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );
		setTitle( "Coche Autonomo" );
		setSize(750,450);
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
