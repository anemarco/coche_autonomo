package ventanas;

import java.awt.Font;
import javax.swing.JFrame;
import javax.swing.JPasswordField;

import baseDatos.BD;
import baseDatos.Usuario;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.TreeMap;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class VentanaInicio extends JFrame {

	private static final long serialVersionUID = 1L;
	

	private JTextField textNombre, textDni;
	private JPasswordField textContrasenya;
	public static TreeMap<String, Usuario> tmUsuarios;
	
	public static VentanaInicio ventInic;
	
	private ArrayList<Usuario> lUsuarios;
	

	/**
	 * Create the frame.
	 */
	public VentanaInicio() {
		
		ventInic = this;
		
		/*Conectar con la base de datos*/
		
		addWindowListener(new WindowAdapter() {
			
			@Override
			public void windowOpened(WindowEvent e) {
				BD.initBD("simulacion.bd");
				BD.crearTablas();
				lUsuarios = BD.getUsuarios();
			}
		});
		
	
		
		/*Connection con = BD.initBD("iniciosesion.db");
		BD.crearTablas(con);
		tmUsuarios = BD.obtenerMapaUsuarios(con);
		BD.closeBD(con);*/
		
		this.setTitle("INICIO DE SESIÃ“N");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(600, 500);
		this.setLocationRelativeTo(null);
		getContentPane().setLayout(null);

		
		JLabel titulo = new JLabel("SIMULADOR DE COCHE AUTÃ“NOMO");
		titulo.setFont(new Font("Agency FB", Font.PLAIN, 30));
		titulo.setBounds(144, 82, 269, 33);
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
	
		
		/*lblNombreUsuario = new JLabel("Introduce tu nombre:");
		panelCentral.add(lblNombreUsuario);
		
		textNombre = new JTextField();
		panelCentral.add(textNombre);
		textNombre.setColumns(10);*/
		
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
		 * */
		
		btnIniciarSesion.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				iniciarSimulador();
				String dni = textDni.getText();
				String c = textContrasenya.getText();
				if(!dni.equals("") && !c.equals("")) {
					/*Connection con  = BD.initBD("iniciosesion.db");
					int resul = BD.obtenerMapaUsuarios(dni, c);
					if(resul == 0) {
						JOptionPane.showMessageDialog(null, "No estás registrado, tienes que registrarte primero");
					}else if(resul == 1) {
						JOptionPane.showMessageDialog(null, "Contrasenya incorrecta");
					}else {
						JOptionPane.showMessageDialog(null, "Bienvenido");
					}*/
				}
				
				/*btnIniciarSesion.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						String nom = textNombre.getText();
						String c = textContrasenia.getText();
						if(!nom.equals("") && !c.equals("")) {
							Connection con = BD.initBD("newton.db");
							int resul = BD.obtenerUsuario(con, nom, c);
							if(resul == 0) {
								JOptionPane.showMessageDialog(null, "No estás registrado");
								btnRegistro.setEnabled(true);
							}else if(resul==1) {
								JOptionPane.showMessageDialog(null, "La contraseña no es correcta");
							}else {
								JOptionPane.showMessageDialog(null, "Ongi etorri!");
								activarBotones();
							}
						}
						textNombre.setText("");
						textContrasenia.setText("");
					}
				});*/
				
				//---------------------------------------------------------------------------------
				/*
				for (Usuario u : lUsuarios) {
					if (u.getDni() == textDni.getText() || textDni.getText() == "admin") {
						if (u.getContrasenia() == textContrasenya.getText() || textContrasenya.getText() == "admin") {
							iniciarSimulador();
						} else {
							JOptionPane.showMessageDialog(null, "ContraseÃ±a incorrecta","ERROR", JOptionPane.ERROR_MESSAGE);
						}
						
					} else {
						JOptionPane.showMessageDialog(null, "Usuario no registrado","ERROR", JOptionPane.ERROR_MESSAGE);
					}
				}*/
				
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
	private void vaciarCampos() {
		textNombre.setText("");
		textDni.setText("");
		textContrasenya.setText("");
	}
	
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
