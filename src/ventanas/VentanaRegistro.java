package ventanas;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.Vector;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
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
	
	public VentanaRegistro() {
		
		ventReg = this;
		
		this.setTitle("REGISTRO");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(727, 529);
		this.setLocationRelativeTo(null);
		getContentPane().setLayout(null);
		
		/*Panel de la tabla*/
		
		JPanel panelTabla = new JPanel();
		panelTabla.setBounds(226, 40, 475, 439);
		getContentPane().add(panelTabla);
		
		table = new JTable();
		panelTabla.add(new JScrollPane(table), BorderLayout.CENTER);
		
		/*Crear Tabla*/
		
		Vector<String> cabeceras = new Vector<String>(Arrays.asList("Dni", "Nombre", "Apellido"));
		mTable = new DefaultTableModel(
				new Vector<Vector<Object>>(),
				cabeceras
		);
		
		for (Usuario u : VentanaInicio.lUsuarios) {
			mTable.addRow(new Object[] {u.getDni(), u.getNombre(), u.getApellido()});
		}
		
		table.setModel(mTable);
		
		
		addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.isAltDown()) {
					
					int selectRow = table.rowAtPoint(e.getPoint());
					if (selectRow >= 0) {
						
					}
				}
			} 
			
		});
		
		/*Crar JLabels*/
		
		JLabel lblNombre = new JLabel("Nombre: ");
		lblNombre.setBounds(20, 93, 85, 14);
		getContentPane().add(lblNombre);
		
		JLabel lblApellido = new JLabel("Apellido: ");
		lblApellido.setBounds(20, 118, 65, 14);
		getContentPane().add(lblApellido);
		
		JLabel lblDni = new JLabel("DNI:");
		lblDni.setBounds(20, 142, 46, 14);
		getContentPane().add(lblDni);
		
		JLabel lblContrasenya = new JLabel("Contrasenya: ");
		lblContrasenya.setBounds(20, 167, 85, 14);
		getContentPane().add(lblContrasenya);
		
		/*Crara JTextFields*/
		
		tfNombre = new JTextField();
		tfNombre.setBounds(103, 90, 86, 20);
		getContentPane().add(tfNombre);
		tfNombre.setColumns(10);
		
		tfApellido = new JTextField();
		tfApellido.setBounds(103, 115, 86, 20);
		getContentPane().add(tfApellido);
		tfApellido.setColumns(10);
		
		tfDni = new JTextField();
		tfDni.setBounds(103, 139, 86, 20);
		getContentPane().add(tfDni);
		tfDni.setColumns(10);
		
		tfContrasenya = new JPasswordField();
		tfContrasenya.setBounds(105, 164, 86, 20);
		getContentPane().add(tfContrasenya);
		tfContrasenya.setColumns(10);
		
		/*Crear Botones*/
		
		JButton btnRegistrar = new JButton("REGISTRAR");
		btnRegistrar.setBounds(50, 209, 105, 23);
		getContentPane().add(btnRegistrar);
		
		JButton btnInicio = new JButton("INICIO");
		btnInicio.setBounds(20, 456, 105, 23);
		getContentPane().add(btnInicio);
		
		JLabel lblNewLabel = new JLabel("Introduzca sus datos");
		lblNewLabel.setBounds(50, 53, 142, 14);
		getContentPane().add(lblNewLabel);
		
		JLabel lblUsuarios = new JLabel("Usuarios");
		lblUsuarios.setBounds(432, 15, 115, 14);
		getContentPane().add(lblUsuarios);
		
		btnInicio.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				ventReg.setVisible(false);
				VentanaInicio ventIni = new VentanaInicio();
				ventIni.setVisible(true);
				
			}
		});
		
		btnRegistrar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String erdni = "[0-9]{8}[A-Z]";
				String d = tfDni.getText();
				boolean correctoDni = Pattern.matches(erdni, d);
				if(correctoDni) {
					String n = tfNombre.getText();
					String a = tfApellido.getText();
					String c = tfContrasenya.getText();
					Usuario u = new Usuario(d, n, a, c);
					VentanaInicio.tmUsuarios .put(u.getDni(), u);
					JOptionPane.showMessageDialog(null, "Usuario regristrado correctamente", "REGISTRO CORRECTO", JOptionPane.INFORMATION_MESSAGE);
					vaciarCampos();
				}else {
					JOptionPane.showMessageDialog(null, "El dni no es correcto", "��ERROR!!", JOptionPane.ERROR_MESSAGE);
				}
				
			}
		});
	}
	
	/**
	 * Vaciaria los campos cuando el usuario pulse un boton
	 */
	private void vaciarCampos() {
		tfNombre.setText("");
		tfApellido.setText("");
		tfDni.setText("");
		tfContrasenya.setText("");
	}
}

/**
 * Ventana de confirmación que pedirá una contraseña cuando se desee 
 * borrar un usuario de la BD
 * @author iness
 */

class VentanaConfirmacion extends JFrame {

	private static final long serialVersionUID = 1L;
	
	public VentanaConfirmacion() {
		this.setTitle("VENTANA DE CONFIRMACIÓN");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(100, 60);
		this.setLocationRelativeTo(null);
		getContentPane().setLayout(null);
		
		JLabel info = new JLabel("Para borrar el usuario X ha de introducir su contraseña");
		getContentPane().add(info);
		
	}
	
}
