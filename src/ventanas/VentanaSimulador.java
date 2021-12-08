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
	
	private static final Rectangle TAM_VENT = new Rectangle(1160, 600);	
	private static final Rectangle TAM_FONDO = new Rectangle(966, 542);
	private static final Point COORD_FONDO = new Point(10, 10);

	public static final int CARRIL_DCHO = 492;
	private static final int CARRIL_IZQ = 390;
	private static final int ARCEN_DCHO = 330;
	private static final int ARCEN_IZQ = 552;
	
	/*Atributos*/
	
	static JPanel simuladorPane;
	static VentanaSimulador vent;
	
	private ArrayList<Obstaculo> listaObs = new ArrayList<Obstaculo>();
	private Coche miCoche;

	/*Main*/
	
	public static void main(String[] args) {
		
		VentanaInicio ventInicio = new VentanaInicio();
		ventInicio.setVisible(true);
		
	}
	
	/**Constructor de ventana*/
	
	public VentanaSimulador() {
		
		vent = this;
		
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
		
		JLabel titulo = new JLabel("    OBSTÃ�CULOS ");
		titulo.setFont(new Font("Agency FB", Font.PLAIN, 28));
		panelBotonero.add(titulo);
		
		
		/*Doble panel que contiene el panel y la lista anterior*/
		
		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,panelBotonero,simuladorPane);
		cp.add(splitPane);
		
		//Crear botones de cada obstÃ¡culo y aÃ±adirlos al panel
		JButton bPeaton	= new JButton("            PeatÃ³n          ");
		JButton bCoche = new JButton("        Otro Coche      ");
		JButton bSemaf = new JButton("          SemÃ¡foro       ");
		JButton bStop = new JButton("             STOP           ");
		JButton bCeda = new JButton("             Ceda            ");
		JButton bSentidoCon = new JButton(" Sentido Contrario  ");
		JButton bAnimal = new JButton("            Animal          ");
		  
		panelBotonero.add(bPeaton);
		panelBotonero.add(bCoche );
		panelBotonero.add(bSemaf );
		panelBotonero.add(bStop );
		panelBotonero.add(bCeda );
		panelBotonero.add(bSentidoCon );
		panelBotonero.add(bAnimal );
		
		/*BotÃ³n que cree un obtÃ¡culo peaton*/
		
		bPeaton.addActionListener(new ActionListener() {
		      
			@Override
			public void actionPerformed(ActionEvent e) {
				
				/*Crear un objeto constructor*/
				Peaton peaton = new Peaton();
				simuladorPane.add(peaton.getLbl());
				listaObs.add(peaton);
				
				
				/*Hilo de movimiento del peatÃ³n*/
				Thread moverPeaton= new Thread() {
					
					@Override
        			public void run(){
        				
        				while (peaton.getX()<700) {
        					
        					try {
    							sleep(MS_SLEEP);
    						} catch (InterruptedException e1) {
    							e1.printStackTrace();
    						}
        					
        					peaton.mover(20, 20);
  
        				}
        				simuladorPane.remove(peaton.getLbl());
        				listaObs.remove(peaton);
        	    		peaton.getLbl().setVisible(false);
        			}	
        		};
        		moverPeaton.start();
			}
        });
		
		/*BotÃ³n que cree un obtÃ¡culo coche*/
		
        bCoche.addActionListener(new ActionListener() {
            
			@Override
			public void actionPerformed(ActionEvent e) {
				
        		//Hacer que aparezca la imagen coche.jpg en el simulador al presionar su botï¿½n
				OtroCoche otroCoche = new OtroCoche(CARRIL_DCHO, 10);
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
        
        /*BotÃ³n que cree un obtÃ¡culo semÃ¡foro*/
        
        bSemaf.addActionListener(new ActionListener() {
            
			@Override
			public void actionPerformed(ActionEvent e) {
				
				Semaforo semaf = new Semaforo();
				simuladorPane.add(semaf.getLbl());
				listaObs.add(semaf);
				
        		Thread moverSemaf = new Thread() {
        			public void run(){
        				
        				while (semaf.getY()<500) {
        					try {
    							sleep(MS_SLEEP);
    						} catch (InterruptedException e1) {
    							e1.printStackTrace();
    						}
                			semaf.mover(0, 20);
        				}
        				simuladorPane.remove(semaf.getLbl());
        	    		semaf.getLbl().setVisible(false);
        			}
        				
        		};
				
        		moverSemaf.start();
			}
        });
        
        /*BotÃ³n que cree un obtÃ¡culo seÃ±al tipo stop*/
		
        bStop.addActionListener(new ActionListener() {
            
			@Override
			public void actionPerformed(ActionEvent e) {
				
				Senal stop = new Senal(Tipo.STOP);
				simuladorPane.add(stop.getLbl());
				listaObs.add(stop);
				
				
        		Thread moverStop = new Thread() {
        			
        			public void run(){
        				//int aceleracion = 100;
        				while (stop.getY()<500) {
        					try {
    							sleep(MS_SLEEP);
    							 
                    			if(stop.getY()%50==0 && stop.getY() < 370) {
                    				//movimientoCarretera(true, aceleracion);
                    				//aceleracion = aceleracion+100;               				
                    			}
    							   							
    							//TODO: determinar coordenada a partir del panel del coche.
    							if(stop.getY()==370) {
    								movimientoCarretera(false, 0);
    								System.out.println("El coche se detiene");
    								sleep(3000);
    								movimientoCarretera(true, 0);
    							}
    							
    						} catch (InterruptedException e1) {
    							e1.printStackTrace();
    						}
                			stop.mover(0, 20);


        				}
        				System.out.println("SALE");
        				simuladorPane.remove(stop.getLbl());
        				listaObs.remove(stop);
        	    		stop.getLbl().setVisible(false);
        			}
        				
        		};
				
        		moverStop.start();
			}
        });
               
        movimientoCarretera(true, 0);
	}
        
	
	/*MÃ©todo que contiene el hilo de movimiento del fondo (carretera) */
	
	public void movimientoCarretera(boolean activo, int aceleracion) {
		
		/*Escalar la imagen de fondo*/
		
		Image fondoImg = new ImageIcon(getClass().getResource("../simulador/img/FONDO COCHE (16).jpg")).getImage();
		ImageIcon fondoIcon = new ImageIcon(fondoImg.getScaledInstance(TAM_VENT.width,TAM_VENT.height, Image.SCALE_SMOOTH));
		
		Thread time = new Thread(){
			
			@Override
            public void run(){
            	System.out.println("Aceleración:" + aceleracion);
            	System.out.println("Activo:" + activo);
            	simuladorPane.setLayout(null);
        		//JPanel.add(image, BorderLayout.NORTH);
        		JLabel label = new JLabel(fondoIcon);
        		label.setBounds(COORD_FONDO.x, COORD_FONDO.y, TAM_FONDO.width, TAM_FONDO.height);
        		simuladorPane.add(label);
        		
        		int num=15;
        		
        		 while(activo){
        			 
             		try {
							sleep(MS_SLEEP + aceleracion);
							if(MS_SLEEP + aceleracion!=400) {
								//System.out.println(MS_SLEEP + aceleracion);
							}
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
		
		Thread reacion = new Thread(){
			
			@Override
            public void run(){
				
				while (true) {
					
					for (Obstaculo o: listaObs) {
						
						if (o instanceof OtroCoche) {
							
						}
						
						if (o instanceof Peaton) {
							Peaton peaton = (Peaton)o;
							if (peaton.getX()<ARCEN_DCHO && peaton.getX()>ARCEN_IZQ) {
								//movimientoCarretera(true);
							} else {
								//movimientoCarretera(false);
							}
						}
						
					}
					
				}
				
			}
		};
		
		reacion.start();
	}
		
		
		
		/*Obstaculo oDetectado = null;
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
	}*/
}

