package Ventanas;

import Simulador.Obstaculo;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JList;

public class VentanaSimulador extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private ArrayList<Obstaculo> listaObs = new ArrayList<Obstaculo>();
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
		
		JList list = new JList();
		list.setBounds(153, 147, -52, -86);
		contentPane.add(list);
		
		JList list_1 = new JList();
		list_1.setBounds(10, 23, 91, 131);
		contentPane.add(list_1);
		
	
		
		
	}
	
	
}
