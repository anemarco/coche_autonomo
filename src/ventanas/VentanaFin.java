package ventanas;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import baseDatos.BD;
import baseDatos.ObstaculoBD;
import baseDatos.Simulacion;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;


public class VentanaFin extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private JButton btnSalir, btnIrAlInicio;
	
	public static VentanaFin ventFin;
	
	private JTable tablaUsuarios;
	private DefaultTableModel modeloTablaUsuarios;
	private static JTable tablaObstaculos;
	private static DefaultTableModel modeloTablaObstaculos;
	
	public static List<Simulacion> lSimulaciones;
	public static List<ObstaculoBD> lObstaculos;
	
	public static SimpleDateFormat sdf = new SimpleDateFormat( "dd/MM/yyyy HH:mm:ss" );
	
	public static JPanel panelCapa;
	public static JScrollPane scrollPane;
	public static JLabel info, instrucciones;

	/**
	 * Create the frame.
	 */
	public VentanaFin() {
		ventFin = this;
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setBounds(150, 100, 798, 578);
		this.setLocationRelativeTo(null);
		getContentPane().setLayout(null);
		
		lSimulaciones = BD.getSimulacionesDeUnaPersona(VentanaInicio.usuarioActivo.getDni());
		System.out.println(lSimulaciones);
		
		/*Paneles*/
		
		JPanel pIzq = new JPanel();
		pIzq.setBounds(10, 56, 475, 420);
		getContentPane().add(pIzq);
		
		panelCapa = new JPanel();
		panelCapa.setBounds(512, 49, 250, 400);
		getContentPane().add(panelCapa);
		
		JPanel pBotones = new JPanel();
		pBotones.setBounds(10, 487, 752, 43);
		getContentPane().add(pBotones);
		
		JPanel pInfo = new JPanel();
		pInfo.setBounds(145, 18, 504, 27);
		getContentPane().add(pInfo);
		
		JPanel panelTituloObs = new JPanel();
		panelTituloObs.setBounds(512, 49, 250, 27);
		getContentPane().add(panelTituloObs);
		
		JPanel pDer = new JPanel();
		pDer.setBounds(495, 76, 280, 398);
		getContentPane().add(pDer);

		instrucciones = new JLabel("<html><br><br><br><br><br><br><br><br><br><br><br><br><br><br>"
									+ "Para obtener infromación más detallada"
									+ "<br>haga Clik sobre una de las simulaciones </html>");

		panelCapa.add(instrucciones);
		
		/*Componentes*/
		
		JLabel nomUsuario = new JLabel(VentanaInicio.usuarioActivo.getNombre());
		pInfo.add(nomUsuario);
		
		info = new JLabel();
		info.setVisible(false);
		panelTituloObs.add(info);
		
		btnIrAlInicio = new JButton("Inicio");
		pBotones.add(btnIrAlInicio);
		
		JButton bNuevaSimulacion = new JButton("Nueva Simulación");
		pBotones.add(bNuevaSimulacion);
		
		bNuevaSimulacion.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				ventFin.setVisible(false);
				VentanaSimulador ventSim = new VentanaSimulador();
				ventSim.setVisible(true);
			}
		});
		
		JButton bExportar = new JButton("Exportar datos");
		pBotones.add(bExportar);
		
		bExportar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter("Ficheros TXT", "txt");
				fc.setFileFilter(filter);
				
				int status = fc.showOpenDialog(ventFin);
				if (status == JFileChooser.APPROVE_OPTION) {
					BD.guardarDatos(fc.getSelectedFile(), VentanaInicio.usuarioActivo.getDni());
				}
				
				
			}
		});
		
		btnSalir = new JButton("Salir");
		pBotones.add(btnSalir);
		
		btnSalir.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		
		
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
				} else {
					return true;
				}
			}
		};
		
		String [] columnas = {"Fecha", "Duración (seg)", "Estado", "Obstáculos"};
		modeloTablaUsuarios.setColumnIdentifiers(columnas);
		
		tablaUsuarios = new JTable(modeloTablaUsuarios);
		JScrollPane scrollTabla = new JScrollPane(tablaUsuarios); 
		scrollTabla.setPreferredSize(new java.awt.Dimension(460, 410));
		pIzq.add(scrollTabla);
		
		for (Simulacion s : lSimulaciones) {
			lObstaculos = BD.getObstaculosDeUnaSimulacion(s.getFecha());
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
		
		tablaObstaculos = new JTable();
		scrollPane = new JScrollPane(tablaObstaculos);
		scrollPane.setPreferredSize(new java.awt.Dimension(240, 375));
		scrollPane.setVisible(false);
		pDer.add(scrollPane, BorderLayout.WEST);
		
		Vector<String> cabeceras = new Vector<String>(Arrays.asList("Nombre", "Hora"));
		modeloTablaObstaculos = new DefaultTableModel(
				new Vector<Vector<Object>>(),
				cabeceras
		);
		
		System.out.println(lObstaculos);
		
		/*Al hacer la tabla usuarios se cargan los datos en la tabla usuario*/
		
		tablaUsuarios.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseClicked(MouseEvent e) {
				panelCapa.setVisible(false);
				instrucciones.setVisible(false);
				info.setVisible(true);
				scrollPane.setVisible(true);
				
				String fecha = (String) tablaUsuarios.getValueAt(tablaUsuarios.getSelectedRow(), 0);
				cargarTablaObstaculos(fecha);
				info.setText(fecha);
			}
		});
		
		tablaObstaculos.setModel(modeloTablaObstaculos);
		
		JPanel panel = new JPanel();
		panel.setBounds(10, 11, 125, 34);
		getContentPane().add(panel);
		
		JComboBox<String> comboBox = new JComboBox<String>();
		comboBox.setFont(new Font("ARIAL", Font.PLAIN, 12));
		comboBox.addItem("Todo");
		comboBox.addItem("Éxitos");
		comboBox.addItem("Fracasos");
		panel.add(comboBox);
		
		
		tablaObstaculos.getColumnModel().getColumn(0).setMinWidth(140);
		tablaObstaculos.getColumnModel().getColumn(0).setMaxWidth(140);
		tablaObstaculos.getColumnModel().getColumn(1).setMinWidth(100);
		tablaObstaculos.getColumnModel().getColumn(1).setMaxWidth(100);
		

		/*BOTONES*/
		
		btnIrAlInicio.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				ventFin.setVisible(false);
				VentanaInicio ventReg = new VentanaInicio();
				ventReg.setVisible(true);
				
			}
		});
		
		
		/*btnEliminarTodosLosUsuarios.addActionListener(new ActionListener() {
					
			@Override
			public void actionPerformed(ActionEvent e) {
				while(tablaUsuarios.getRowCount() > 0) {
					modeloTablaUsuarios.removeRow(0);
				}
			}
		});*/
		
		
		/**
		 * Boton que sale de la pantalla
		 */
	}
	
	public static void cargarTablaObstaculos(String fecha) {
		
		while(tablaObstaculos.getRowCount() > 0) {
			modeloTablaObstaculos.removeRow(0);
		}
		
		lObstaculos = BD.getObstaculosDeUnaSimulacion(fecha);
		
		for (ObstaculoBD o : lObstaculos) {
			modeloTablaObstaculos.addRow(new Object[] {o.getNombre(), o.getHora()});
		}
		
		tablaObstaculos.setModel(modeloTablaObstaculos);
	}
}
