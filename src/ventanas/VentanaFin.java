package ventanas;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import baseDatos.BD;
import baseDatos.Simulacion;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.awt.event.ActionEvent;


public class VentanaFin extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private JPanel contentPane, panelNorte, panelSur, panelCentral;
	private JButton btnSalir, btnEliminar, btnEliminarTodosLosUsuarios, btnIrAlInicio;
	
	public static VentanaFin ventFin;
	
	private JTable tablaUsuarios;
	private DefaultTableModel modeloTablaUsuarios;
	
	public static ArrayList<Simulacion> lSimulaciones;
	
	public static SimpleDateFormat sdf = new SimpleDateFormat( "dd/MM/yyyy HH:mm:ss" );

	/**
	 * Create the frame.
	 */
	public VentanaFin() {
		ventFin = this;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(150, 100, 753, 500);
		setLocationRelativeTo(null);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		lSimulaciones = BD.getSimulacionesDeUnaPersona(VentanaInicio.usuarioActivo.getDni());
		
		panelNorte = new JPanel();
		contentPane.add(panelNorte, BorderLayout.NORTH);
		
		JLabel nomUsuario = new JLabel(VentanaInicio.usuarioActivo.getNombre());
		panelNorte.add(nomUsuario);
		
		panelSur = new JPanel();
		
		contentPane.add(panelSur, BorderLayout.SOUTH);
		btnIrAlInicio = new JButton("Inicio");
		panelSur.add(btnIrAlInicio);
		
		btnEliminar = new JButton("Eliminar usuario");
		panelSur.add(btnEliminar);
		
		
		btnEliminarTodosLosUsuarios = new JButton("Eliminar todos los Usuarios");
		panelSur.add(btnEliminarTodosLosUsuarios);
		
		btnSalir = new JButton("Salir");
		panelSur.add(btnSalir);
		
		panelCentral = new JPanel();
		contentPane.add(panelCentral, BorderLayout.EAST);
		
		JPanel panelInfo= new JPanel();
		contentPane.add(panelInfo,BoxLayout.Y_AXIS);
		
		JLabel lblNewLabel = new JLabel("Resumen de la simulación");
		panelInfo.add(lblNewLabel);
		
		
		/*JTABLE*/
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
		
		String [] columnas = {"Fecha", "Duración", "Estado", "Obstáculos"};
		modeloTablaUsuarios.setColumnIdentifiers(columnas);
		
		tablaUsuarios = new JTable(modeloTablaUsuarios);
		JScrollPane scrollTabla = new JScrollPane(tablaUsuarios); //Falta a�adir al panel
		panelCentral.add(scrollTabla);
		
		for (Simulacion s : lSimulaciones) {
			modeloTablaUsuarios.addRow(new Object[] {s.getFecha(), s.getDuracion(), s.getEstado(),1});
		}
		
		tablaUsuarios.setModel(modeloTablaUsuarios);
		
		tablaUsuarios.getColumnModel().getColumn(0).setMinWidth(130);
		tablaUsuarios.getColumnModel().getColumn(0).setMaxWidth(130);
		
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
