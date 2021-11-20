package ventanas;

import java.awt.*;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.border.EmptyBorder;

import simulador.*;
import simulador.Semaforo.Color;
import simulador.Senal.Tipo;

import javax.swing.JList;

public class VentanaSimulador extends JFrame {

	private static final long serialVersionUID = 1L;
	private static Rectangle TAM_VENT = new Rectangle(1150, 600);
	
	/*Atributos*/
	protected JButton b1;
	protected JButton b2;
	protected JButton b3;
	protected JButton b4;
	protected JButton b5;
	protected JButton b6;
	private ArrayList<Obstaculo> listaObs = new ArrayList<Obstaculo>();
	private Coche miCoche;

	/*Main*/
	public static void main(String[] args) {
		
		VentanaSimulador ventana= new VentanaSimulador();
		ventana.setVisible(true);
		
		while (ventana.isVisible()) {
			
		}
	}
	
	/**Constructor de ventana*/
	public VentanaSimulador() {
		
		setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );
		setTitle( "Coche Autonomo" );
		setSize(TAM_VENT.width,TAM_VENT.height);
		setLocationRelativeTo(null);
		
		//Crear contenedor
		Container cp = this.getContentPane();
		//Crear panel para la pantalla del simulador de coche
		JPanel simuladorPane= new JPanel();
		simuladorPane.setLayout(null);
		//simuladorPane.setSize(TAM_VENT.width-20,TAM_VENT.height-10);
		
		//escalar im�genes
		Image fondoImg = new ImageIcon(getClass().getResource("../simulador/img/FONDO COCHE (16).jpg")).getImage();
		ImageIcon fondo = new ImageIcon(fondoImg.getScaledInstance(TAM_VENT.width,TAM_VENT.height, Image.SCALE_SMOOTH));
		
		Image cocheImg = new ImageIcon(getClass().getResource("../simulador/img/coche.png")).getImage();
		ImageIcon coche = new ImageIcon(cocheImg.getScaledInstance(100, 150, Image.SCALE_SMOOTH));
		
		//JPanel.add(image, BorderLayout.NORTH);
		JLabel label2 = new JLabel(coche);
		label2.setBounds(280, 200, 515, 513);
		simuladorPane.add(label2);
		//JPanel.add(image, BorderLayout.NORTH);
		
		
		//Crear panel para botones 
		JPanel panelBotonero= new JPanel();
		panelBotonero.setLayout(new BoxLayout(panelBotonero,BoxLayout.Y_AXIS));
		
		//Crear botones de cada obst�culo
		b1=new JButton("         Peat�n         ");
		b2= new JButton("      Otro Coche    ");
		b3= new JButton("        Semaforo      ");
		b4= new JButton("          STOP          ");
		b5= new JButton("           Ceda           ");
		b6= new JButton("Sentido Contrario");
		panelBotonero.add(b1);
		panelBotonero.add(b2);
		panelBotonero.add(b3);
		panelBotonero.add(b4);
		panelBotonero.add(b5);
		panelBotonero.add(b6);
		
		//Doble panel que contiene el panel y la lista anterior
		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,panelBotonero,simuladorPane);
		
		cp.add(splitPane);
		
		Thread time = new Thread(){
            public void run(){
            	simuladorPane.setLayout(null);
        		//JPanel.add(image, BorderLayout.NORTH);
        		JLabel label = new JLabel(fondo);
        		label.setBounds(10, 10, 966, 542);
        		simuladorPane.add(label);
        		int num=15;
        		 while(true){
             		try {
							sleep(400);
						} catch (InterruptedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
                	Image fondoImg2 = new ImageIcon(getClass().getResource("../simulador/img/FONDO COCHE ("+ num +").jpg")).getImage();
            		ImageIcon fondo2 = new ImageIcon(fondoImg2.getScaledInstance(TAM_VENT.width,TAM_VENT.height, Image.SCALE_SMOOTH));
            		simuladorPane.setLayout(null);
            		simuladorPane.remove(label);
            		label.setVisible(false);
            		
            		JLabel picSecond = new JLabel(fondo2);
            		picSecond.setBounds(10, 10, 966, 542);
            		simuladorPane.add(picSecond);
            		picSecond.setVisible(true);
            		simuladorPane.revalidate();
            		simuladorPane.repaint();
            		
            		label = picSecond;
            		            		
            		num = num-1;
            		if (num==0){
            			num = 16;  		
                    }
            		


                }
            }
        };
        time.start();
		
	}
	
	public void cocheReaccion() {
		
		Obstaculo oDetectado = null;
		OtroCoche cocheCerca = null;
		
		for (Obstaculo o: listaObs) {
			miCoche.getSR().detectaObs(o);
			if (o instanceof OtroCoche) {
				cocheCerca = (OtroCoche)o;
			} else {
				oDetectado = o;
			}
			
		}
		
		if (oDetectado instanceof Semaforo) {
			Semaforo semaf = (Semaforo) oDetectado;
			if (semaf.getColor() == Color.VERDE) {
				
			} else if ((semaf.getColor() == Color.NARANJA) || (semaf.getColor() == Color.ROJO)) {
				
			}
		}
		
		if (oDetectado instanceof Peaton) {
			
		}
		
		if (oDetectado instanceof Senal) {
			Senal senal = (Senal) oDetectado;
			if (senal.getTipo() == Tipo.STOP) {

			} else if (senal.getTipo() == Tipo.CEDA) {
				
			} else if (senal.getTipo() == Tipo.SENTIDO_OBLIGATORIO) {
				
			}
		}
		
		if ((cocheCerca.getY() < miCoche.getY()) && (cocheCerca.getX() == miCoche.getX())) {
			
		}
	}
}
