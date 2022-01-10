package ventanas;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import baseDatos.BD;
import baseDatos.ObstaculoBD;
import baseDatos.Simulacion;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Vector;
import java.awt.event.ActionEvent;


public class VentanaFin extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private JButton btnSalir, btnEliminar, btnEliminarTodosLosUsuarios, btnIrAlInicio;
	
	public static VentanaFin ventFin;
	
	private JTable tablaUsuarios;
	private DefaultTableModel modeloTablaUsuarios;
	private JTable tablaObstaculos;
	private DefaultTableModel modeloTablaObstaculos;
	
	public static ArrayList<Simulacion> lSimulaciones;
	
	public static SimpleDateFormat sdf = new SimpleDateFormat( "dd/MM/yyyy HH:mm:ss" );

	/**
	 * Create the frame.
	 */
	public VentanaFin() {
		ventFin = this;
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setBounds(150, 100, 865, 530);
		this.setLocationRelativeTo(null);
		getContentPane().setLayout(null);
		
		lSimulaciones = BD.getSimulacionesDeUnaPersona(VentanaInicio.usuarioActivo.getDni());
		
		/*Paneles*/
		
		JPanel pIzq = new JPanel();
		pIzq.setBounds(10, 49, 475, 377);
		getContentPane().add(pIzq);
		
		JPanel pDer = new JPanel();
		pDer.setBounds(512, 87, 309, 339);
		getContentPane().add(pDer);
		
		JPanel pBotones = new JPanel();
		pBotones.setBounds(10, 437, 811, 43);
		getContentPane().add(pBotones);
		
		JPanel pInfo = new JPanel();
		pInfo.setBounds(10, 11, 811, 27);
		getContentPane().add(pInfo);
		
		JPanel panel = new JPanel();
		panel.setBounds(512, 49, 309, 27);
		getContentPane().add(panel);
		
		
		/*Componentes*/
		
		JLabel nomUsuario = new JLabel(VentanaInicio.usuarioActivo.getNombre());
		pInfo.add(nomUsuario);
		
		JLabel info = new JLabel("Seleccione una simulación para más detalles");
		panel.add(info);
		
		btnIrAlInicio = new JButton("Inicio");
		pBotones.add(btnIrAlInicio);
		
		btnEliminar = new JButton("Eliminar usuario");
		pBotones.add(btnEliminar);
		
		btnEliminarTodosLosUsuarios = new JButton("Eliminar todos los Usuarios");
		pBotones.add(btnEliminarTodosLosUsuarios);
		
		btnSalir = new JButton("Salir");
		pBotones.add(btnSalir);
		
		/*TABLA USUARIOS*/
		
		/**
		 * Creamos una JTable que mostrar� el TreeMap de usuarios que han usado
		 * el simulador con su nombre, dni y la puntuaci�n que han conseguido al 
		 * sortear los diferentes obst�culos.
		 */
		modeloTablaUsuarios = new DefaultTableModel() {
			
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int col) {
				if(col == 0 && col == 1 && col == 2) {
					return false;
				}else {
					return true;
				}
			}
		};
		
		String [] columnas = {"Fecha", "Duración (seg)", "Estado", "Obstáculos"};
		modeloTablaUsuarios.setColumnIdentifiers(columnas);
		
		tablaUsuarios = new JTable(modeloTablaUsuarios);
		JScrollPane scrollTabla = new JScrollPane(tablaUsuarios); 
		pIzq.add(scrollTabla);
		
		for (Simulacion s : lSimulaciones) {
			ArrayList<ObstaculoBD> lObstaculos = BD.getObstaculosDeUnaSimulacion(s.getFecha());
			modeloTablaUsuarios.addRow(new Object[] {s.getFecha(), s.getDuracion(), s.getEstado(),lObstaculos.size()});
		}
		
		tablaUsuarios.setModel(modeloTablaUsuarios);
		
		tablaUsuarios.getColumnModel().getColumn(0).setMinWidth(140);
		tablaUsuarios.getColumnModel().getColumn(0).setMaxWidth(140);
		
		/*Renderizar tabla*/
		
		tablaUsuarios.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
			private static final long serialVersionUID = 1L;
			
			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
				JLabel ret = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
				
				if (column==0 || column==1 || column==2) {
					ret.setHorizontalAlignment(JLabel.LEFT);
				} else if (column==3) {
					ret.setHorizontalAlignment(JLabel.RIGHT);
				}
				
				if (row>=1 && column<=1) {
					if (lSimulaciones.get(row).getEstado().equals("FRACASO")) {
						ret.setForeground(Color.RED);
					} else if (lSimulaciones.get(row).getEstado().equals("EXITO")) {
						ret.setForeground(Color.BLACK);
					}
				}
				return ret;
			}
		});
		
		/*TABLA OBSTACULOS*/
		
		Vector<String> cabeceras = new Vector<String>(Arrays.asList("Hora", "Nombre"));
		modeloTablaObstaculos = new DefaultTableModel(
				new Vector<Vector<Object>>(),
				cabeceras
		);
		
		/*BOTONES*/
		
		btnIrAlInicio.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				ventFin.setVisible(false);
				VentanaInicio ventReg = new VentanaInicio();
				ventReg.setVisible(true);
				
			}
		});
		
		btnEliminarTodosLosUsuarios.addActionListener(new ActionListener() {
					
			@Override
			public void actionPerformed(ActionEvent e) {
				while(tablaUsuarios.getRowCount() > 0) {
					modeloTablaUsuarios.removeRow(0);
				}
			}
		});
		
		
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
