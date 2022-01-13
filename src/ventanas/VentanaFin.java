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
import java.util.Arrays;
import java.util.List;
import java.util.Vector;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;


public class VentanaFin extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private JButton btnSalir, btnIrAlInicio, bExportar, bNuevaSimulacion;
	
	public static VentanaFin ventFin;
	
	private static JTable tSimulaciones, tObstaculos;
	private static DefaultTableModel mSimulaciones, mObstaculos;
	public static List<Simulacion> lSimulaciones;
	public static List<ObstaculoBD> lObstaculos;
	
	public static SimpleDateFormat sdf = new SimpleDateFormat( "dd/MM/yyyy HH:mm:ss" );
	
	public static JPanel panelCapa, pIzq, pDer, pBotones, pInfo, panelTituloObs, panel;
	public static JScrollPane scrollPane;
	public static JLabel info, instrucciones, nomUsuario;
	
	public static JComboBox<String> comboBox;

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
		
		/**
		 * Crear paneles
		 */
		
		pIzq = new JPanel();
		pIzq.setBounds(10, 56, 475, 420);
		getContentPane().add(pIzq);
		
		panelCapa = new JPanel();
		panelCapa.setBounds(512, 49, 250, 400);
		getContentPane().add(panelCapa);
		
		pBotones = new JPanel();
		pBotones.setBounds(10, 487, 752, 43);
		getContentPane().add(pBotones);
		
		pInfo = new JPanel();
		pInfo.setBounds(145, 18, 504, 27);
		getContentPane().add(pInfo);
		
		panelTituloObs = new JPanel();
		panelTituloObs.setBounds(512, 49, 250, 27);
		getContentPane().add(panelTituloObs);
		
		pDer = new JPanel();
		pDer.setBounds(495, 76, 280, 398);
		getContentPane().add(pDer);
		
		/**
		 * Componentes básicos
		 */
		
		instrucciones = new JLabel("<html><br><br><br><br><br><br><br><br><br><br>"
				+ "Para obtener infromación más detallada"
				+ "<br>haga Clik sobre una de las simulaciones </html>");

		panelCapa.add(instrucciones);
		
		nomUsuario = new JLabel(VentanaInicio.usuarioActivo.getNombre());
		pInfo.add(nomUsuario);
		
		info = new JLabel();
		info.setVisible(false);
		panelTituloObs.add(info);
		
		btnIrAlInicio = new JButton("Inicio");
		pBotones.add(btnIrAlInicio);
		
		bNuevaSimulacion = new JButton("Nueva Simulación");
		pBotones.add(bNuevaSimulacion);
		
		bExportar = new JButton("Exportar datos");
		pBotones.add(bExportar);
		
		btnSalir = new JButton("Salir");
		pBotones.add(btnSalir);
		
		/*EVENTOS*/
		
		/**
		 * Permite a un usuario volver a ejecutar una simulación sin volvera tener que registarse 
		 * en la ventana de inidio
		 */
		
		bNuevaSimulacion.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				ventFin.setVisible(false);
				VentanaSimulador ventSim = new VentanaSimulador();
				ventSim.setVisible(true);
			}
		});
		
		/**
		 * Permite a un usuario exportar todos los datos de sus simulaciones y sus obstáculos
		 * mediante un fichero de texto
		 */
		
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
		
		btnSalir.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		
		btnIrAlInicio.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				ventFin.setVisible(false);
				VentanaInicio ventReg = new VentanaInicio();
				ventReg.setVisible(true);
				
			}
		});
		
		
		/**
		 * Crear tabla de simulaciones
		 */
		
		mSimulaciones = new DefaultTableModel() {
			
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int col) {
				return false;
			}
		};
		
		String [] columnas = {"Fecha", "Duración (seg)", "Estado", "Obstáculos"};
		mSimulaciones.setColumnIdentifiers(columnas);
		
		tSimulaciones = new JTable(mSimulaciones);
		JScrollPane scrollTabla = new JScrollPane(tSimulaciones); 
		scrollTabla.setPreferredSize(new java.awt.Dimension(460, 410));
		pIzq.add(scrollTabla);
		
		for (Simulacion s : lSimulaciones) {
			lObstaculos = BD.getObstaculosDeUnaSimulacion(s.getFecha());
			mSimulaciones.addRow(new Object[] {s.getFecha(), s.getDuracion(), s.getEstado(),lObstaculos.size()});
		}
		
		tSimulaciones.setModel(mSimulaciones);
		
		tSimulaciones.getColumnModel().getColumn(0).setMinWidth(140);
		tSimulaciones.getColumnModel().getColumn(0).setMaxWidth(140);
		
		renderizarSimulaciones();
		
		/**
		 * Crear tabla usuarios
		 */
		
		tObstaculos = new JTable();
		scrollPane = new JScrollPane(tObstaculos);
		scrollPane.setPreferredSize(new java.awt.Dimension(240, 375));
		scrollPane.setVisible(false);
		pDer.add(scrollPane, BorderLayout.WEST);
		
		Vector<String> cabeceras = new Vector<String>(Arrays.asList("Nombre", "Hora"));
		mObstaculos = new DefaultTableModel(
				new Vector<Vector<Object>>(),
				cabeceras
		);
		
		System.out.println(lObstaculos);
		
		/**
		 * Añadir un evento de ratón a la tabla simulación. Al pulsar una simulación se creará una tabla
		 * adicional a la derecha de la ventana con los obstáculos creados en dicha simulación
		 */
		
		tSimulaciones.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseClicked(MouseEvent e) {
				panelCapa.setVisible(false);
				instrucciones.setVisible(false);
				info.setVisible(true);
				scrollPane.setVisible(true);
				
				String fecha = (String) tSimulaciones.getValueAt(tSimulaciones.getSelectedRow(), 0);
				cargarTablaObstaculos(fecha);
				info.setText(fecha);
			}
		});
		
		tObstaculos.setModel(mObstaculos);
		
		JPanel panel = new JPanel();
		panel.setBounds(10, 11, 125, 34);
		getContentPane().add(panel);
		
		comboBox = new JComboBox<String>();
		comboBox.setFont(new Font("ARIAL", Font.PLAIN, 12));
		comboBox.addItem("Todo");
		comboBox.addItem("Exitos");
		comboBox.addItem("Fracasos");
		panel.add(comboBox);
		
		
		tObstaculos.getColumnModel().getColumn(0).setMinWidth(140);
		tObstaculos.getColumnModel().getColumn(0).setMaxWidth(140);
		tObstaculos.getColumnModel().getColumn(1).setMinWidth(100);
		tObstaculos.getColumnModel().getColumn(1).setMaxWidth(100);
		
		
		comboBox.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				cargarTablaSimulaciones();
			}
		});
	}
	
	/**
	 * Actualiza la tabla de simulaciones cuando se realizen cambios en ella debido al comboBox
	 * @param fecha
	 */
	
	public static void cargarTablaSimulaciones() {
		String sel = comboBox.getSelectedItem().toString();
		
		if(sel!=null) {
			if(sel.equals("Exitos")) {
				lSimulaciones = BD.getSimulacionesDeUnaPersonaConEstado(VentanaInicio.usuarioActivo.getDni(), "EXITO");
			}else if(sel.equals("Fracasos")){
				lSimulaciones = BD.getSimulacionesDeUnaPersonaConEstado(VentanaInicio.usuarioActivo.getDni(), "FRACASO");
			}else {
				lSimulaciones = BD.getSimulacionesDeUnaPersonaConEstado(VentanaInicio.usuarioActivo.getDni(), "TODAS");
			}
			
			while(mSimulaciones.getRowCount()>0)  mSimulaciones.removeRow(0);
			
			for(Simulacion s: lSimulaciones) {
				lObstaculos = BD.getObstaculosDeUnaSimulacion(s.getFecha());
				mSimulaciones.addRow(new Object[] {s.getFecha(), s.getDuracion(), s.getEstado(),lObstaculos.size()});
			}
		}
		
		renderizarSimulaciones();
	}
	
	/**
	 * Actualiza la tabla de obstáculos en función de la simulación que esté seleccionada
	 * @param fecha Fecha de la simulación seleccionada
	 */
	
	public static void cargarTablaObstaculos(String fecha) {
		while(tObstaculos.getRowCount() > 0) {
			mObstaculos.removeRow(0);
		}
		
		lObstaculos = BD.getObstaculosDeUnaSimulacion(fecha);
		
		for (ObstaculoBD o : lObstaculos) {
			mObstaculos.addRow(new Object[] {o.getNombre(), o.getHora()});
		}
		
		tObstaculos.setModel(mObstaculos);
	}
	
	/**
	 * Renderizar tabla de simulaciones
	 */
	
	public static void renderizarSimulaciones() {
		
		tSimulaciones.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
			private static final long serialVersionUID = 1L;
			
			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
				JLabel ret = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
				
				if (column==0 || column==1 || column==2) {
					ret.setHorizontalAlignment(JLabel.LEFT);
				} else if (column==3) {
					ret.setHorizontalAlignment(JLabel.RIGHT);
				}
				
				if (row>=0 && column<=0) {
					if (lSimulaciones.get(row).getEstado().equals("FRACASO")) {
						ret.setForeground(Color.RED);
					} else if (lSimulaciones.get(row).getEstado().equals("EXITO")) {
						ret.setForeground(Color.BLACK);
					}
				}
				return ret;
			}
		});
	}
}
