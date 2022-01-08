package ventanas;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import simulador.*;
import simulador.Semaforo.Color;
import simulador.Senal.Tipo;

import java.awt.Point;
import java.awt.Rectangle;

public class VentanaSimulador extends JFrame {

	/*Constantes*/
	public static enum Color {VERDE, ROJO}
	private static final long serialVersionUID = 1L;
	private static final int MS_SLEEP = 400;
	
	private static final Rectangle TAM_VENT = new Rectangle(1160, 600);	
	private static final Rectangle TAM_FONDO = new Rectangle(966, 542);
	private static final Point COORD_FONDO = new Point(10, 10);

	public static final int CARRIL_DCHO = 492;
	public static final int CARRIL_IZQ = 390;
	private static final int ARCEN_DCHO = 330;
	private static final int ARCEN_IZQ = 552;
	
	
	
	private static Logger logger = Logger.getLogger("Simulador");
	
	/*Atributos*/
	
	static JPanel simuladorPane;
	static VentanaSimulador vent;
	
	private static  ArrayList<Obstaculo> listaObs = new ArrayList<Obstaculo>();
	public Coche miCoche;
	public static OtroCoche otro;
	public Thread movimientoCarr;

	/*Main*/
	
	public static void main(String[] args) {
		
		VentanaInicio ventInicio = new VentanaInicio();
		ventInicio.setVisible(true);
		
	}
	
	/**Constructor de ventana*/
	
	public VentanaSimulador() {
		
		logger.log( Level.INFO, "Inicio de la simulación" );
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
		JButton bSemaf = new JButton("         SemÃ¡foro       ");
		JButton bStop = new JButton("             STOP           ");
		JButton bCeda = new JButton("             Ceda            ");
		JButton bSentidoCon = new JButton(" Sentido Contrario ");
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
				logger.log( Level.INFO, "Objeto Peaton añadido" );
				
				
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
        	    		logger.log( Level.INFO, "Objeto Peaton eliminado");
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
				listaObs.add(otroCoche);
				logger.log( Level.INFO, "Objeto OtroCoche añadido");
				cocheReaccion(otroCoche, miCoche);
				//Desabilitar el boton OtroCoche para que no haya colapso de imagenes
				bCoche.setEnabled(false);
				//Hacer que se mueva la imagen (objetivo:adelantarlo)
        		Thread moverCoche= new Thread() {
        			public void run(){
        				
        				while (otroCoche.getY()<430) {
        					try {
    							sleep(MS_SLEEP);
    						} catch (InterruptedException e1) {
    							e1.printStackTrace();
    						}
                			otroCoche.mover(0, 50);
        				}
        				
        				//eliminar a OtroCoche de la pantalla
        				simuladorPane.remove(otroCoche.getLbl());
        	    		otroCoche.getLbl().setVisible(false);
        	    		listaObs.remove(otroCoche);
        	    		logger.log( Level.INFO, "Objeto OtroCoche eliminado");
        	    		//Abilitar de nuevo el botón
        	    		bCoche.setEnabled(true);
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
				logger.log( Level.INFO, "Objeto Semáforo añadido");
				
        		Thread moverSemaf = new Thread() {
        			public void run(){
        				
        				while (semaf.getY()<500) {
        					try {
    							sleep(MS_SLEEP);
    							if (semaf.getColor().toString()==Color.ROJO.toString() && semaf.getY()==370) {
    								
									System.out.println("El coche se detiene");   
									logger.log( Level.INFO, "Semáforo está en rojo");   							
									movimientoCarr.suspend();
									sleep(2500);
									simuladorPane.remove(semaf.getLbl());
    								cambiarSemaforo();
									logger.log( Level.INFO, "Semáforo se ha cambiado a verde");
    								movimientoCarr.resume();
    								
    							}
    						} catch (InterruptedException e1) {
    							e1.printStackTrace();
    						}
                			semaf.mover(0, 20);
        				}
        				
        				simuladorPane.remove(semaf.getLbl());
        	    		semaf.getLbl().setVisible(false);
        	    		logger.log( Level.INFO, "Objeto Semáforo eliminado");
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
				logger.log( Level.INFO, "Objeto Señal tipo Stop añadido");
				
				
        		Thread moverStop = new Thread() {
        			
        			public void run(){
        				while (stop.getY()<500) {
        					try {
    							sleep(MS_SLEEP);    							     						
    							if(stop.getY()==370) {    								
    								System.out.println("El coche se detiene");   
    								movimientoCarr.suspend();
    								sleep(3000);
    								movimientoCarr.resume();
    								// movimientoCarretera(true);
    								
    							}
    							
    						} catch (InterruptedException e1) {
    							e1.printStackTrace();
    						}
                			stop.mover(0, 20);

        				}

        				simuladorPane.remove(stop.getLbl());
        				listaObs.remove(stop);
        	    		stop.getLbl().setVisible(false);
        	    		logger.log( Level.INFO, "Objeto Señal tipo Stop eliminado");
        			}
        				
        			
        		};
				
        		moverStop.start();
			}
        });
        
        bAnimal.addActionListener(new ActionListener() {
        	@Override
			public void actionPerformed(ActionEvent e) {
				
				Animal oveja = new Animal();
				simuladorPane.add(oveja.getLbl());
				listaObs.add(oveja);
				logger.log( Level.INFO, "Objeto Animal añadido");
				cocheReaccion(oveja,miCoche);
				
        		Thread moverAnimal = new Thread() {
        			
        			public void run(){
        				//int aceleracion = 100;
        				while (oveja.getY()<500) {
        					try {
    							sleep(MS_SLEEP);
    							
    						} catch (InterruptedException e1) {
    							e1.printStackTrace();
    						}
                			oveja.mover(0, 20);


        				}
        				System.out.println("SALE");
        				simuladorPane.remove(oveja.getLbl());
        				listaObs.remove(oveja);
        				logger.log( Level.INFO, "Objeto Animal eliminado");
        	    		oveja.getLbl().setVisible(false);
        	    		
        			}
        				
        			
        		};
				
        		moverAnimal.start();
			}
        });
        JButton salir = new JButton("SALIR");
        salir.setBackground(java.awt.Color.RED);
        salir.setForeground(java.awt.Color.WHITE);
        panelBotonero.add(salir);
        
        salir.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				vent.setVisible(false);
				VentanaFin fin = new VentanaFin();
				fin.setVisible(true);
			}
		});
        
        movimientoCarr = movimientoCarretera(true);
        movimientoCarr.start();
        // movimientoCarretera(true);
	}
        
	
	/*MÃ©todo que contiene el hilo de movimiento del fondo (carretera) */
		
	public Thread movimientoCarretera(boolean activo){
		return new Thread(){
			Image fondoImg = new ImageIcon(getClass().getResource("../simulador/img/FONDO COCHE (16).jpg")).getImage();
			ImageIcon fondoIcon = new ImageIcon(fondoImg.getScaledInstance(TAM_VENT.width,TAM_VENT.height, Image.SCALE_SMOOTH));
			
			@Override
            public void run(){            	
            	simuladorPane.setLayout(null);
        		JLabel label = new JLabel(fondoIcon);
        		label.setBounds(COORD_FONDO.x, COORD_FONDO.y, TAM_FONDO.width, TAM_FONDO.height);
        		simuladorPane.add(label);
        		
        		int num=15;
        		
       			 while(activo) {
             		try {
             			sleep(MS_SLEEP);
					} 
             		catch (InterruptedException e1) {
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
	}
	
	public static void cocheReaccion(Obstaculo o, Coche miCoche) {
		listaObs.remove(o);
		if (listaObs.isEmpty()|| o==null || (listaObs.get(0) instanceof Animal && o instanceof Animal) ||(o instanceof Animal && (listaObs.get(0) instanceof Peaton||listaObs.get(0) instanceof Senal ||listaObs.get(0) instanceof Semaforo))) {
			if (o instanceof OtroCoche) {
				if(miCoche.getX()==CARRIL_DCHO) {
					listaObs.add(o);
					Thread movimientoOC= new Thread() {
	        			public void run(){
	        				while (miCoche.getX()>CARRIL_IZQ) {
	        					try {
	    							sleep(MS_SLEEP);
	    						} catch (InterruptedException e1) {
	    							e1.printStackTrace();
	    						}
	                			miCoche.mover(-20, 0);
	        				}
	        				miCoche.setX(CARRIL_IZQ);
	        				listaObs.add(null);
	        				cocheReaccion(null,miCoche);
	        			}
					};
        		movimientoOC.start();
			}
				logger.log( Level.INFO, "OtroCoche superado con éxito" );
			}		
			if (o instanceof Animal || o==null) {
				listaObs.add(o);
				if(miCoche.getX()==CARRIL_IZQ) {
					Thread movimientoA= new Thread() {
	        			public void run(){
	        				
	        				while (miCoche.getX()<CARRIL_DCHO) {
	        					try {
	    							sleep(MS_SLEEP);
	    						} catch (InterruptedException e1) {
	    							e1.printStackTrace();
	    						}
	                			miCoche.mover(20, 0);
	        				}
	        				miCoche.setX(CARRIL_DCHO);
	        			}
					};
        		movimientoA.start();
				}
				if(o instanceof Animal) {
					logger.log( Level.INFO, "Animal superado con éxito" );
				}else {
					listaObs.remove(o);
					logger.log( Level.INFO, "miCoche vuelve a posición inicial" );
				}
			}
		}else {
				//cerra la simulación por colisión
				logger.log( Level.SEVERE, "COLISIÓN");
				JOptionPane.showMessageDialog(null, "Se producirá una colisión. Debe mejorar el sistema", "ALERTA", JOptionPane.WARNING_MESSAGE);
				vent.setVisible(false);
				VentanaFin fin = new VentanaFin();
				fin.setVisible(true);
		}
	}
	public void cambiarSemaforo() {
		Semaforo semafVerde= new Semaforo();
		System.out.println("Color del semáforo:"+semafVerde.getColor().toString());
		if(semafVerde.getColor()==Semaforo.Color.ROJO) {
			cambiarSemaforo();
			
		}else {
			semafVerde.setY(370);
			VentanaSimulador.simuladorPane.add(semafVerde.getLbl());
			semafVerde.getLbl().setVisible(true);
			Thread moverVerde= new Thread() {
				
				@Override
    			public void run(){
    				
    				while (semafVerde.getY()<500) {
    					
    					try {
							sleep(MS_SLEEP);
						} catch (InterruptedException e1) {
							e1.printStackTrace();
						}
    					semafVerde.mover(0, 20);
    				}
				simuladorPane.remove(semafVerde.getLbl());
				semafVerde.getLbl().setVisible(false);
				}	
			};
			moverVerde.start();
		}
	}
}

