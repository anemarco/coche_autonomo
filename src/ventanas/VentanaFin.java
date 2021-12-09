package ventanas;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class VentanaFin extends JFrame {

	private JPanel contentPane, panelNorte, panelSur, panelCentral;
	private JButton btnSalir, btnEliminar;
	
	private JTable tablaUsuarios;
	private DefaultTableModel modeloTablaUsuarios;

	/**
	 * Create the frame.
	 */
	public VentanaFin() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		setLocationRelativeTo(null);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		panelNorte = new JPanel();
		contentPane.add(panelNorte, BorderLayout.NORTH);
		
		panelSur = new JPanel();
		contentPane.add(panelSur, BorderLayout.SOUTH);
		
		btnEliminar = new JButton("Eliminar usuario");
		panelSur.add(btnEliminar);
		
		btnSalir = new JButton("Salir");
		panelSur.add(btnSalir);
		
		panelCentral = new JPanel();
		contentPane.add(panelCentral, BorderLayout.CENTER);
		
		
		/*JTABLE*/
		/**
		 * Creamos una JTable que mostrar� el TreeMap de usuarios que han usado
		 * el simulador con su nombre, dni y la puntuaci�n que han conseguido al 
		 * sortear los diferentes obst�culos.
		 */
		modeloTablaUsuarios = new DefaultTableModel() {
			public boolean isCellEditable(int row, int col) {
				if(col == 0 && col == 1 && col == 2) {
					return false;
				}else {
					return true;
				}
			}
		};
		
		String [] columnas = {"NOMBRE", "DNI", "PUNTUACI�N"};
		modeloTablaUsuarios.setColumnIdentifiers(columnas);
		
		tablaUsuarios = new JTable(modeloTablaUsuarios);
		JScrollPane scrollTabla = new JScrollPane(tablaUsuarios); //Falta a�adir al panel
		/**
		 * Boton que sale de la pantalla
		 */
		btnSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
	}
	

}
