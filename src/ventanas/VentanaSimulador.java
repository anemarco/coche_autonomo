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

	private static final long serialVersionUID = 1L;
	
	/*Constantes*/
	
	private static Rectangle TAM_VENT = new Rectangle(1150, 600);	
	
	private final int x_obs =100;
	private final int y_obs=-150;
	
	/*Atributos*/
	
	JPanel simuladorPane;
	
	private ArrayList<Obstaculo> listaObs = new ArrayList<Obstaculo>();
	private Coche miCoche;

	/*Main*/
	public static void main(String[] args) {
		
		VentanaSimulador ventana= new VentanaSimulador();
		ventana.setVisible(true);
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
		
		/*Escalar imágenes*/

		Image cocheImg = new ImageIcon(getClass().getResource("../simulador/img/coche.png")).getImage();
		ImageIcon cocheIcon = new ImageIcon(cocheImg.getScaledInstance(100, 150, Image.SCALE_SMOOTH));
		
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
				
				/*Hilo de movimiento del peatón*/
				Thread moverPeaton= new Thread() {
					
					@Override
        			public void run(){
        				
        				while (peaton.getX()<700) {
        					
        					try {
    							sleep(400);
    						} catch (InterruptedException e1) {
    							e1.printStackTrace();
    						}
        					
        					peaton.mover(15, 4);
  
        				}
        				simuladorPane.remove(peaton.getLbl());
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
        		JLabel label4 = new JLabel(cocheIcon);
        		simuladorPane.add(label4);
        		//Hacer que se mueva la imagen (objetivo:adelantarlo)
        		Thread moverCoche= new Thread() {
        			public void run(){
        				int y=y_obs-50;
        				while (y<370) {
        					try {
    							sleep(400);
    						} catch (InterruptedException e1) {
    							// TODO Auto-generated catch block
    							e1.printStackTrace();
    						}
                			y+=20;
                			label4.setBounds(x_obs+180,y,515,513);
        				}
        				simuladorPane.remove(label4);
        	    		label4.setVisible(false);
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
            public void run(){
            	simuladorPane.setLayout(null);
        		//JPanel.add(image, BorderLayout.NORTH);
        		JLabel label = new JLabel(fondoIcon);
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
			Peaton peat= (Peaton) oDetectado;
			
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

