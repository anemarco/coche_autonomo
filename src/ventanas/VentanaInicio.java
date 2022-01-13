package ventanas;

import java.awt.Font;
import javax.swing.JFrame;
import javax.swing.JPasswordField;

import baseDatos.BD;
import baseDatos.Usuario;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.TreeMap;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class VentanaInicio extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private JTextField textNombre, textDni;
	private JPasswordField textContrasenya;
	public static TreeMap<String, Usuario> tmUsuarios = new TreeMap<>();
	public JLabel titulo, lblDni, lblContrasenya;
	public JButton btnSalir, btnIniciarSesion, btnRegistrar;
	
	public static VentanaInicio ventInic;
	
	public static TreeMap<String, Usuario> lUsuarios;
	public static Usuario usuarioActivo;
	

	/**
	 * Create the frame.
	 */
	
	public VentanaInicio() {
		
		ventInic = this;
		
		this.setTitle("INICIO DE SESIÓN");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(600, 500);
		this.setLocationRelativeTo(null);
		getContentPane().setLayout(null);
		
		/**
		 * Abrir conexión con la base de datos
		 */
		
		BD.initBD("simulacion.bd");
		lUsuarios = BD.getMapaUsuarios();

		/**
		 * Crear los componentes
		 */
		
		JLabel titulo = new JLabel("SIMULADOR DE COCHE AUTÓNOMO");
		titulo.setFont(new Font("Agency FB", Font.PLAIN, 30));
		titulo.setBounds(148, 82, 363, 33);
		getContentPane().add(titulo);
		
		JButton btnSalir = new JButton("SALIR");
		btnSalir.setBounds(10, 427, 104, 23);
		getContentPane().add(btnSalir);
		
		JButton btnIniciarSesion = new JButton("INICIAR SESION");
		btnIniciarSesion.setBounds(227, 324, 140, 23);
		getContentPane().add(btnIniciarSesion);
		
		JButton btnRegistrar = new JButton("REGISTRAR");
		btnRegistrar.setBounds(464, 427, 110, 23);
		getContentPane().add(btnRegistrar);
		
		JLabel lblDni = new JLabel("Introduce tu DNI:");
		lblDni.setBounds(129, 178, 118, 28);
		getContentPane().add(lblDni);
		
		textDni = new JTextField();
		textDni.setBounds(311, 182, 118, 20);
		getContentPane().add(textDni);
		textDni.setColumns(10);
		
		JLabel lblContrasenya = new JLabel("Introduce tu contrasenya:");
		lblContrasenya.setBounds(129, 236, 158, 20);
		getContentPane().add(lblContrasenya);
		
		JTextField textContrasenya = new JPasswordField();
		textContrasenya.setBounds(311, 236, 118, 20);
		getContentPane().add(textContrasenya);
		textContrasenya.setColumns(10); 
		
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
				String dni = textDni.getText();
				String cont = textContrasenya.getText();
				if(!dni.equals("") && !cont.equals("")) {
					BD.initBD("simulacion.bd");
					TreeMap<String, Usuario> tm = BD.getMapaUsuarios();
					if(tm.get(dni) == null) {
						JOptionPane.showMessageDialog(null, "No estas registrado, tienes que registrarte primero");
					}else {
						Usuario usu = tm.get(dni);
						if(!usu.getContrasenia().equals(cont)) {
							JOptionPane.showMessageDialog(null, "Contrasena incorrecta", "��ERROR!!", JOptionPane.ERROR_MESSAGE);
						}else {
							usuarioActivo = usu;
							JOptionPane.showMessageDialog(null, "Bienvenido");
							iniciarSimulador();
						}
					}
				}
				
			}
			
		});
		
	
		
		/**
		 * Boton que registra un usuario si no esta ya registrado y lo guardara en la base de datos
		 */
		
		btnRegistrar.addActionListener(new ActionListener() {
					
			@Override
			public void actionPerformed(ActionEvent e) {
				ventInic.setVisible(false);
				VentanaRegistro ventReg = new VentanaRegistro();
				ventReg.setVisible(true);
			}
		});
		
		
	}
	
	/**
	 * Vaciaria los campos cuando el usuario pulse un boton
	 */
	
	@SuppressWarnings("unused")
	private void vaciarCampos() {
		textNombre.setText("");
		textDni.setText("");
		textContrasenya.setText("");
	}
	
	/**
	 * Método que crea una ventana simulador
	 */
	
	private void iniciarSimulador() {
		ventInic.setVisible(false);
		
		Thread hilo = new Thread() {
			
			@Override
			public void run() {
				VentanaSimulador ventSim = new VentanaSimulador();
				ventSim.setVisible(true);
			}	
		};
		
		hilo.start();	
	}
}
