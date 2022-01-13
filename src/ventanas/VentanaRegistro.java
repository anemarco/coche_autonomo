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
import java.util.TreeMap;
import java.util.Vector;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import baseDatos.*;

/**
 * Ventana que gestiona todo lo referente a los usuarios de la base de datos
 */

public class VentanaRegistro extends JFrame {

	private static final long serialVersionUID = 1L;
	private JTextField tfNombre, tfApellido, tfContrasenya, tfDni;
	private JLabel lblNombre, lblApellido, lblDni, lblContrasenya, lblUsuarios, lblNewLabel, lblNewLabel_1;
	private JPanel panelTabla;
	private JButton btnRegistrar, btnInicio;
	public static VentanaRegistro ventReg;
	private static JTable table;
	public static DefaultTableModel mTable;
	
	public static TreeMap<String, Usuario> lUsuarios;
	
	public VentanaRegistro() {
		
		ventReg = this;
		
		this.setTitle("REGISTRO");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(727, 548);
		this.setLocationRelativeTo(null);
		getContentPane().setLayout(null);
		
		/**
		 * Crar panel de la tabla
		 */
		
		panelTabla = new JPanel();
		panelTabla.setBounds(226, 40, 475, 439);
		getContentPane().add(panelTabla);
		
		/**
		 * Crare compnentes básicos: labels, textfields y botones
		 */
		
		lblNombre = new JLabel("Nombre: ");
		lblNombre.setBounds(20, 93, 85, 14);
		getContentPane().add(lblNombre);
		
		lblApellido = new JLabel("Apellido: ");
		lblApellido.setBounds(20, 118, 65, 14);
		getContentPane().add(lblApellido);
		
		lblDni = new JLabel("DNI:");
		lblDni.setBounds(20, 142, 46, 14);
		getContentPane().add(lblDni);
		
		lblContrasenya = new JLabel("Contrasenya: ");
		lblContrasenya.setBounds(20, 167, 85, 14);
		getContentPane().add(lblContrasenya);
		
		lblNewLabel = new JLabel("Introduzca sus datos");
		lblNewLabel.setBounds(50, 53, 142, 14);
		getContentPane().add(lblNewLabel);
		
		lblUsuarios = new JLabel("Usuarios");
		lblUsuarios.setBounds(432, 15, 115, 14);
		getContentPane().add(lblUsuarios);
		
		lblNewLabel_1 = new JLabel("Alt + Clik para eliminar a un usuario");
		lblNewLabel_1.setBounds(480, 476, 240, 14);
		getContentPane().add(lblNewLabel_1);
		
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
		
		btnRegistrar = new JButton("REGISTRAR");
		btnRegistrar.setBounds(50, 209, 105, 23);
		getContentPane().add(btnRegistrar);
		
		btnInicio = new JButton("INICIO");
		btnInicio.setBounds(20, 474, 105, 23);
		getContentPane().add(btnInicio);
		
		/**
		 * Anñadir eventos a los botones
		 */
		
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
					VentanaInicio.tmUsuarios.put(u.getDni(), u);
					BD.insertarUsuario(d, n, a, c);
					String newRow[] = {d,n,a,c};
					mTable.addRow(newRow);
					JOptionPane.showMessageDialog(null, "Usuario regristrado correctamente", "REGISTRO CORRECTO", JOptionPane.INFORMATION_MESSAGE);
					vaciarCampos();
				}else {
					JOptionPane.showMessageDialog(null, "El dni no es correcto", "��ERROR!!", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
		
		/*
		 * Crear tabla usuarios
		 */
		
		table = new JTable();
		panelTabla.add(new JScrollPane(table), BorderLayout.CENTER);

		Vector<String> cabeceras = new Vector<String>(Arrays.asList("Dni", "Nombre", "Apellido"));
		mTable = new DefaultTableModel(
				new Vector<Vector<Object>>(),
				cabeceras
		);
		
		updateUI();
		
		/*
		 * Añadir un evento de ratón a la tabal usuarios. Al hacer click sobre una fila de la
		 * tabla se iniciará un proceso para borrar el usuario que se encuentra en dicha fila de la bd
		 */

		table.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseClicked(MouseEvent e) {
				
				Usuario selectUsuario = null;
				if (e.isAltDown()) {
					String selectedDni = (String) table.getValueAt(table.getSelectedRow(), 0);
					for (Usuario u : VentanaInicio.lUsuarios.values()) {
						if (u.getDni().equals(selectedDni)) 
							selectUsuario = u;
					}
				}
				
				VentanaConfirmacion ventConf = new VentanaConfirmacion(selectUsuario);
				ventConf.setVisible(true);
			}
			
		});
	}
	
	/**
	 * Método que actualiza la tabla de usuarios cada vez que se realiza un cambio en la base de datos
	 */
	
	public static void updateUI() {
		lUsuarios = BD.getMapaUsuarios();
		
		for(int i = mTable.getRowCount() - 1; i >= 0; i--) {
			mTable.removeRow(i);
		}
		
		for (Usuario u : lUsuarios.values()) {
			mTable.addRow(new Object[] {u.getDni(), u.getNombre(), u.getApellido()});
		}
		
		table.setModel(mTable);
	}
	
	/**
	 * Método que vacía los campos de todos los textFields
	 */
	
	private void vaciarCampos() {
		tfNombre.setText("");
		tfApellido.setText("");
		tfDni.setText("");
		tfContrasenya.setText("");
	}
}

/**
 * Ventana adicional que se despliega al seleccionar un usuario en la tabla.
 * Pide una confirmación por contraeña para el usuario antes de borrarlo de la base de datos
 * @author iness
 *
 */

class VentanaConfirmacion extends JFrame {

	private static final long serialVersionUID = 1L;
	
	public VentanaConfirmacion vent;

	public VentanaConfirmacion(Usuario u) {
		vent = this;
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(280, 190);
		this.setLocationRelativeTo(null);
		getContentPane().setLayout(null);
		
		JLabel instruccion = new JLabel("Usuario a eliminar: " + u.getNombre() + " " + u.getApellido());
		instruccion.setBounds(40, 22, 200, 30);
		getContentPane().add(instruccion);
		
		JTextField tfContra = new JTextField("Introduzaza la cotraseña");
		tfContra.setBounds(60, 72, 150, 20);
		getContentPane().add(tfContra);
		tfContra.setColumns(10);
		
		JButton eliminar = new JButton("ELIMINAR");
		eliminar.setBounds(92, 100, 100, 25);
		getContentPane().add(eliminar);
		
		eliminar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (tfContra.getText().equals(u.getContrasenia())) {
					BD.eliminarUsuario(u.getDni());
					vent.setVisible(false);
					VentanaRegistro.updateUI();
					JOptionPane.showMessageDialog(null, "Usuario elimienado correctamente");
				} else {
					JOptionPane.showMessageDialog(null, "Contraseña incorrecta. No se ha podido eliminar el usuario");
					vent.setVisible(false);
				}
			}
		});
	}
}
