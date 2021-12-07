package ventanas;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import simulador.*;
import simulador.Semaforo.Color;
import simulador.Senal.Tipo;

public class VentanaSimulador extends JFrame {

	/*Constantes*/

	
	private static final long serialVersionUID = 1L;
	private static final int MS_SLEEP = 400;
	
	private static final Rectangle TAM_VENT = new Rectangle(1150, 600);	
	private static final Rectangle TAM_FONDO = new Rectangle(966, 542);
	private static final Point COORD_FONDO = new Point(10, 10);

	private static final int CARRIL_DCHO = 492;
	private static final int CARRIL_IZQ = 390;
	
	/*Atributos*/
	
	JPanel simuladorPane;
	
	private ArrayList<Obstaculo> listaObs = new ArrayList<Obstaculo>();
	private Coche miCoche;

	/*Main*/
	
	public static void main(String[] args) {
		
		VentanaSimulador vent = new VentanaSimulador();
		vent.setVisible(true);
		
		while (vent.isVisible()) {
			vent.cocheReaccion();
		}
	}
	
	/**Constructor de ventana*/
	
	public VentanaSimulador() {
		
		this.setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );
		this.setTitle( "Coche Autonomo" );
		this.setSize(TAM_VENT.width,TAM_VENT.height);
		this.setLocationRelativeTo(null);
		
		/*Contenedor y panel*/
		Container cp = this.getContentPane();
		simuladorPane= new JPanel();
		simuladorPane.setLayout(null);
		
		/*Crear el objeto coche*/
		miCoche = new Coche();
		simuladorPane.add(miCoche.getLbl());
		
		//Crear panel para botones 
		JPanel panelBotonero= new JPanel();
		panelBotonero.setLayout(new BoxLayout(panelBotonero,BoxLayout.Y_AXIS));
		
		//Crear botones de cada obstáculo y añadirlos al panel
		JButton bPeaton = new JButton("       Peatón       ");
		JButton bCoche = new JButton("    Otro Coche  ");
		JButton bSemaf = new JButton("     Semáforo    ");
		JButton bStop = new JButton("       STOP         ");
		JButton bCeda = new JButton("       Ceda         ");
		JButton bSentidoCon = new JButton("Sentido Contrario");
		JButton bAnimal = new JButton("      Animal       ");
		
		panelBotonero.add(bPeaton);
		panelBotonero.add(bCoche );
		panelBotonero.add(bSemaf );
		panelBotonero.add(bStop );
		panelBotonero.add(bCeda );
		panelBotonero.add(bSentidoCon );
		panelBotonero.add(bAnimal );
		
		/*Doble panel que contiene el panel y la lista anterior*/
		
		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,panelBotonero,simuladorPane);
		cp.add(splitPane);
		
		/*Botón que cree un obtáculo peaton*/
		
		bPeaton.addActionListener(new ActionListener() {
		      
			@Override
			public void actionPerformed(ActionEvent e) {
				
				/*Crear un objeto constructor*/
				Peaton peaton = new Peaton();
				simuladorPane.add(peaton.getLbl());
				listaObs.add(peaton);
				
				
				/*Hilo de movimiento del peatón*/
				Thread moverPeaton= new Thread() {
					
					@Override
        			public void run(){
        				
        				while (peaton.getX()<700) {
        					
        					try {
    							sleep(MS_SLEEP);
    						} catch (InterruptedException e1) {
    							e1.printStackTrace();
    						}
        					
        					peaton.mover(15, 4);
  
        				}
        				simuladorPane.remove(peaton.getLbl());
        				listaObs.remove(peaton);
        	    		peaton.getLbl().setVisible(false);
        			}	
        		};
        		moverPeaton.start();
			}
        });
		
		
        //Action listener para el botón otroCoche: 
		
        bCoche.addActionListener(new ActionListener() {
            
			@Override
			public void actionPerformed(ActionEvent e) {
				
        		//Hacer que aparezca la imagen coche.jpg en el simulador al presionar su bot�n
				Coche otroCoche = new Coche(CARRIL_DCHO, 10);
				simuladorPane.add(otroCoche.getLbl());
				
				//Hacer que se mueva la imagen (objetivo:adelantarlo)
        		Thread moverCoche= new Thread() {
        			public void run(){
        				
        				while (otroCoche.getY()<375) {
        					try {
    							sleep(MS_SLEEP);
    						} catch (InterruptedException e1) {
    							e1.printStackTrace();
    						}
                			otroCoche.mover(0, 20);
        				}
        				simuladorPane.remove(otroCoche.getLbl());
        	    		otroCoche.getLbl().setVisible(false);
        			}
        				
        		};
				
        		moverCoche.start();
			}
        });
        
        movimientoCarretera();
	}
	
	/*Método que contiene el hilo de movimiento del fondo (carretera) */
	
	public void movimientoCarretera() {
		
		/*Escalar la imagen de fondo*/
		
		Image fondoImg = new ImageIcon(getClass().getResource("../simulador/img/FONDO COCHE (16).jpg")).getImage();
		ImageIcon fondoIcon = new ImageIcon(fondoImg.getScaledInstance(TAM_VENT.width,TAM_VENT.height, Image.SCALE_SMOOTH));
	
		Thread time = new Thread(){
			
			@Override
            public void run(){
            	
            	simuladorPane.setLayout(null);
        		//JPanel.add(image, BorderLayout.NORTH);
        		JLabel label = new JLabel(fondoIcon);
        		label.setBounds(COORD_FONDO.x, COORD_FONDO.y, TAM_FONDO.width, TAM_FONDO.height);
        		simuladorPane.add(label);
        		
        		int num=15;
        		
        		 while(true){
        			 
             		try {
							sleep(MS_SLEEP);
						} catch (InterruptedException e1) {
							e1.printStackTrace();
						}
             		
                	Image fondoImg2 = new ImageIcon(getClass().getResource("../simulador/img/FONDO COCHE ("+ num +").jpg")).getImage();
            		ImageIcon fondo2 = new ImageIcon(fondoImg2.getScaledInstance(TAM_VENT.width,TAM_VENT.height, Image.SCALE_SMOOTH));
            		simuladorPane.setLayout(null);
            		simuladorPane.remove(label);
            		label.setVisible(false);
            		
            		JLabel picSecond = new JLabel(fondo2);
            		picSecond.setBounds(COORD_FONDO.x, COORD_FONDO.y, TAM_FONDO.width, TAM_FONDO.height);
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

