package ventanas;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;

import baseDatos.BD;
import baseDatos.Usuario;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.util.TreeMap;
import java.util.regex.Pattern;
import java.awt.event.ActionEvent;
import java.awt.GridLayout;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class VentanaInicio extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private JPanel contentPane, panelNorte, panelSur, panelCentral;
	private JButton btnSalir, btnIniciarSesion, btnRegistrar; 
	private JLabel lblNombreUsuario, lblDni, lblContrasenia;
	private JTextField textNombre, textDni;
	private JPasswordField textContrasenia;
	public static TreeMap<String, Usuario> tmUsuarios;
	
	public static VentanaInicio ventInic;

	/**
	 * Create the frame.
	 */
	public VentanaInicio() {
		
		ventInic = this;
		ventInic.setVisible(true);
		
		Connection con = BD.initBD("iniciosesion.db");
		BD.crearTablas(con);
		tmUsuarios = BD.obtenerMapaUsuarios(con);
		BD.closeBD(con);
		
		setTitle("INICIO DE SESIÓN");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(450, 300);
		setLocationRelativeTo(null);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		panelNorte = new JPanel();
		contentPane.add(panelNorte, BorderLayout.NORTH);
		
		JLabel titulo = new JLabel("SIMULADOR DE COCHE AUTÓNOMO");
		titulo.setFont(new Font("Agency FB", Font.PLAIN, 36));
		panelNorte.add(titulo);
		
		panelSur = new JPanel();
		contentPane.add(panelSur, BorderLayout.SOUTH);
		
		btnSalir = new JButton("SALIR");
		panelSur.add(btnSalir);
		
		btnIniciarSesion = new JButton("INICIAR SESION");
		panelSur.add(btnIniciarSesion);
		
		btnRegistrar = new JButton("REGISTRAR");
		panelSur.add(btnRegistrar);
		
		panelCentral = new JPanel();
		contentPane.add(panelCentral, BorderLayout.CENTER);
		panelCentral.setLayout(new GridLayout(0, 2, 0, 0));
		
		lblNombreUsuario = new JLabel("Introduce tu nombre:");
		panelCentral.add(lblNombreUsuario);
		
		textNombre = new JTextField();
		panelCentral.add(textNombre);
		textNombre.setColumns(10);
		
		lblDni = new JLabel("Introduce tu DNI:");
		panelCentral.add(lblDni);
		
		textDni = new JTextField();
		panelCentral.add(textDni);
		textDni.setColumns(10);
		
		lblContrasenia = new JLabel("Introduce tu contrase\u00F1a:");
		panelCentral.add(lblContrasenia);
		
		textContrasenia = new JPasswordField();
		panelCentral.add(textContrasenia);
		textContrasenia.setColumns(10);
		
		/*EVENTOS*/
		/**
		 * Boton que sale de la pantalla de inicio
		 */
		
		btnSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				System.exit(0);
			}
		});
		/**
		 * Boton que haria que el usuario inicie sesion una vez ya registrado y nos llevara a la pantalla de simulacion
		 */
		btnIniciarSesion.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				String n = textNombre.getText();
				String dni = textDni.getText();
				
				/*if(tmUsuarios.get(dni) == null) {
					JOptionPane.showMessageDialog(null, "No estas registrado!", "��ERROR!!", JOptionPane.ERROR_MESSAGE);
				}else {
					JOptionPane.showMessageDialog(null, "Bienvenido!","ACCESO CORRECTO", JOptionPane.INFORMATION_MESSAGE);
				}*/
				
				iniciarSimulador();
			}
		});
		
		/**
		 * Boton que registra un usuario si no esta ya registrado y lo guardara en la base de datos
		 */
		btnRegistrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String erdni = "[0-9]{8}[A-Z]";
				String d = textDni.getText();
				boolean correctoDni = Pattern.matches(erdni, d);
				if(correctoDni) {
					String n = textNombre.getText();
					String dni = textDni.getText();
					String c = textContrasenia.getText();
					if(tmUsuarios.get(dni) == null) {
						Usuario u = new Usuario (n, dni, c);
						tmUsuarios.put(dni, u);
						Connection con = BD.initBD("iniciosesion.db");
						BD.insertarUsuario(con, n, dni, c);
						BD.closeBD(con);
						JOptionPane.showMessageDialog(null, "Persona registrada correctamente","REGISTRO CORRECTO", JOptionPane.INFORMATION_MESSAGE);
						vaciarCampos();
					}else {
						JOptionPane.showMessageDialog(null, "Ya existe un usuario con ese dni", "��ERROR!!", JOptionPane.ERROR_MESSAGE);
					}
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
		textNombre.setText("");
		textDni.setText("");
		textContrasenia.setText("");
	}
	
	private void iniciarSimulador() {
		ventInic.setVisible(false);
		VentanaSimulador ventSim = new VentanaSimulador();
		ventSim.setVisible(true);
	}
	
}
