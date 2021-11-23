package ventanas;

import java.awt.BorderLayout;
import java.awt.EventQueue;

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
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaInicio frame = new VentanaInicio();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public VentanaInicio() {
		
		//Connection con = BD.initBD("iniciosesion.db");
		//tmUsuarios = BD.obtenerMapaUsuarios(con);
		//BD.closeBD(con);
		
		setTitle("INICIO DE SESI�N");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		panelNorte = new JPanel();
		contentPane.add(panelNorte, BorderLayout.NORTH);
		
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
		 * Bot�n que saldr� de la pantalla de inicio
		 */
		
		btnSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				System.exit(0);
			}
		});
		/**
		 * Bot�n que har� que el usuario inicie sesi�n una vez ya registrado y nos llevar� a la pantalla de simulaci�n
		 */
		btnIniciarSesion.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				String n = textNombre.getText();
				String dni = textDni.getText();
				if(tmUsuarios.get(dni) == null) {
					JOptionPane.showMessageDialog(null, "No est�s registrado!", "��ERROR!!", JOptionPane.ERROR_MESSAGE);
				}else {
					JOptionPane.showMessageDialog(null, "Bienvenido!","ACCESO CORRECTO", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		
		/**
		 * Bot�n que registrar� un usuario si no est� ya registrado y lo guardar� en la base de datos
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
	 * Vaciar� los campos cuando el usuario pulse un bot�n
	 */
	private void vaciarCampos() {
		textNombre.setText("");
		textDni.setText("");
	}
	
}
