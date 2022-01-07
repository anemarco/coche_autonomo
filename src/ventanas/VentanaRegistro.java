package ventanas;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import baseDatos.*;


public class VentanaRegistro extends JFrame {

	private static final long serialVersionUID = 1L;
	private JTextField tfNombre;
	private JTextField tfApellido;
	private JTextField tfContrasenya;
	private JTextField tfDni;
	
	public static VentanaRegistro ventReg;
	private JTable table;
	private DefaultTableModel mTable;
	
	private ArrayList<Usuario> lUsuarios;
	
	public VentanaRegistro() {
		
		ventReg = this;
		
		this.setTitle("REGISTRO");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(700, 500);
		this.setLocationRelativeTo(null);
		getContentPane().setLayout(null);
		
		/*Crar JLabels*/
		
		JLabel lblNombre = new JLabel("Nombre: ");
		lblNombre.setBounds(27, 44, 85, 14);
		getContentPane().add(lblNombre);
		
		JLabel lblApellido = new JLabel("Apellido: ");
		lblApellido.setBounds(27, 69, 65, 14);
		getContentPane().add(lblApellido);
		
		JLabel lblDni = new JLabel("DNI:");
		lblDni.setBounds(27, 94, 46, 14);
		getContentPane().add(lblDni);
		
		JLabel lblContrasenya = new JLabel("Contraseña: ");
		lblContrasenya.setBounds(27, 132, 85, 14);
		getContentPane().add(lblContrasenya);
		
		/*Crara JTextFields*/
		
		tfNombre = new JTextField();
		tfNombre.setBounds(103, 35, 86, 20);
		getContentPane().add(tfNombre);
		tfNombre.setColumns(10);
		
		tfApellido = new JTextField();
		tfApellido.setBounds(102, 66, 86, 20);
		getContentPane().add(tfApellido);
		tfApellido.setColumns(10);
		
		tfDni = new JTextField();
		tfDni.setBounds(103, 97, 86, 20);
		getContentPane().add(tfDni);
		tfDni.setColumns(10);
		
		tfContrasenya = new JTextField();
		tfContrasenya.setBounds(103, 129, 86, 20);
		getContentPane().add(tfContrasenya);
		tfContrasenya.setColumns(10);
		
		/*Crear Botones*/
		
		JButton btnRegistrar = new JButton("REGISTRAR");
		btnRegistrar.setBounds(59, 181, 105, 23);
		getContentPane().add(btnRegistrar);
		
		JButton btnInicio = new JButton("INICIO");
		btnInicio.setBounds(10, 427, 105, 23);
		getContentPane().add(btnInicio);
		
		btnInicio.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				ventReg.setVisible(false);
				//VentanaInicio ventInicio = new VentanaInicio();
				//ventInicio.setVisible(true);
			}
		});
		
		/*Panel de la tabla*/
		
		JPanel panelTabla = new JPanel();
		panelTabla.setBounds(199, 11, 475, 439);
		getContentPane().add(panelTabla);
		
		table = new JTable();
		panelTabla.add(new JScrollPane(table), BorderLayout.CENTER);
		
		/*Crear las cabeceras de la tabla*/
		
		Vector<String> cabeceras = new Vector<String>(Arrays.asList("Nombre", "Apellido", "Dni"));
		mTable = new DefaultTableModel(
				new Vector<Vector<Object>>(),
				cabeceras
		);
		
		table.setModel(mTable);
		
	}
}
