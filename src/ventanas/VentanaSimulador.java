package ventanas;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

import baseDatos.BD;
import simulador.*;
import simulador.Semaforo.Color;
import simulador.Senal.Tipo;

public class VentanaSimulador extends JFrame {

	/*Constantes*/
	
	public static enum Estado {EXITO, FRACASO};
	
	private static final long serialVersionUID = 1L;
	private static final int MS_SLEEP = 400;
	
	private static final Rectangle TAM_VENT = new Rectangle(1160, 600);	
	private static final Rectangle TAM_FONDO = new Rectangle(966, 542);
	private static final Point COORD_FONDO = new Point(10, 10);

	public static final int CARRIL_DCHO = 492;
	public static final int CARRIL_IZQ = 390;
	
	private static Logger logger = Logger.getLogger("Simulador");
	
	/*Atributos*/
	
	static JPanel simuladorPane;
	static VentanaSimulador vent;
	
	private static  ArrayList<Obstaculo> listaObs = new ArrayList<Obstaculo>();
	public Coche miCoche;
	public Thread movimientoCarr;

	public static String fecha;
	static long tiempoInicial;
	static long tiempoFinal;
	static long tiempoActual;


	/*Main*/
	
	public static void main(String[] args) {
		
		VentanaInicio ventInicio = new VentanaInicio();
		ventInicio.setVisible(true);	
	}
	
	/**Constructor de ventana*/
	
	public VentanaSimulador() {
		
		logger.log( Level.INFO, "Inicio de la simulación" );
		vent = this;
		fecha = VentanaFin.sdf.format(new Date());
		System.out.println(fecha);
		tiempoInicial = System.currentTimeMillis();
		System.out.println(tiempoInicial);
		
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
		
		JPanel panel = new JPanel();
	    panel.setLayout(new BorderLayout());

		/*Doble panel que contiene el panel y la lista anterior*/
		
		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,panel ,simuladorPane);
		cp.add(splitPane);
		
		JPanel panelBotonero = new JPanel();
		panelBotonero .setLayout(new BoxLayout(panelBotonero ,BoxLayout.PAGE_AXIS));
		panel.add(panelBotonero, BorderLayout.CENTER);
	        
	     JLabel titulo = new JLabel(" OBSTACULOS  ");
	     panel.add(titulo, BorderLayout.NORTH);
	     titulo.setFont(new Font("Agency FB", Font.PLAIN, 28));
	        
	     //Al pusar el boton salir de la simulación y que aparezca la VentanaFin
	     JButton salir = new JButton("FINALIZAR");
	     panel.add(salir, BorderLayout.SOUTH);
	     //salir.setBackground(java.awt.Color.RED);
	     //salir.setForeground(java.awt.Color.WHITE);
		
		//Crear botones de cada obstÃ¡culo y aÃ±adirlos al panel
		
	    JButton bPeaton	= new JButton("  Peatón  ");
		bPeaton.setMaximumSize(new Dimension(Integer.MAX_VALUE, bPeaton.getMinimumSize().height));
		JButton bCoche = new JButton("  Otro Coche  ");
		bCoche.setMaximumSize(new Dimension(Integer.MAX_VALUE, bCoche.getMinimumSize().height));
		JButton bSemaf = new JButton("  Semáforo  ");
		bSemaf.setMaximumSize(new Dimension(Integer.MAX_VALUE, bSemaf.getMinimumSize().height));
		JButton bStop = new JButton("  STOP  ");
		bStop.setMaximumSize(new Dimension(Integer.MAX_VALUE, bStop.getMinimumSize().height));
		/*JButton bCeda = new JButton("             Ceda            ");
		JButton bSentidoCon = new JButton(" Sentido Contrario ");		Para el futuro*/
		JButton bAnimal = new JButton("  Animal  ");
		bAnimal.setMaximumSize(new Dimension(Integer.MAX_VALUE, bAnimal.getMinimumSize().height));
		  
		panelBotonero .add(bPeaton);
		panelBotonero .add(bCoche );
		panelBotonero .add(bSemaf );
		panelBotonero .add(bStop );
		//panelBotonero.add(bCeda );
		//panelBotonero.add(bSentidoCon );
		panelBotonero .add(bAnimal );
		
		/*BotÃ³n que cree un obtÃ¡culo peaton*/
		
		bPeaton.addActionListener(new ActionListener() {
		      
			@Override
			public void actionPerformed(ActionEvent e) {
				
				/*Crear un objeto constructor*/
				Peaton peaton = new Peaton();
				simuladorPane.add(peaton.getLbl());
				listaObs.add(peaton);
				guardarObjetoBD(peaton);
				logger.log( Level.INFO, "Objeto Peaton añadido" );
				
				/*Hilo de movimiento del peatÃ³n*/
				Thread moverPeaton= new Thread() {
					
					@SuppressWarnings("removal")
					@Override
        			public void run(){
        				
        				while (peaton.getX()<700) {
        					
        					try {
    							sleep(MS_SLEEP);
    							if(peaton.getX()<560 && peaton.getX()>350) {    				
    								//Reacción del coche
    								movimientoCarr.suspend();
    								peaton.mover(20, 0);
    							}
    							else if(peaton.getX()==570) {
    								movimientoCarr.resume();
    								peaton.mover(20, 20);
    							}
    							else {
    								peaton.mover(20, 20);
    							}
    								
    								
    						} catch (InterruptedException e1) {
    							e1.printStackTrace();
    						}
        					
        					
        				}
        				
        				//eliminar peatón de la pantalla y de la listaObs
        				simuladorPane.remove(peaton.getLbl());
        	    		peaton.getLbl().setVisible(false);
        	    		listaObs.remove(peaton);
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
				guardarObjetoBD(otroCoche);
				logger.log( Level.INFO, "Objeto OtroCoche añadido");
				//llamar al metodo cocheReaccion para que el miCoche actue en base a la situación
				cocheReaccion(otroCoche, miCoche);
				//Desabilitar el boton OtroCoche para que no haya colapso de imagenes
				bCoche.setEnabled(false);
				//Hacer que se mueva la imagen (objetivo:adelantarlo)
        		Thread moverCoche= new Thread() {
        			public void run(){
        				
        				while (otroCoche.getY()<480) {
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
				//Hacer aparecer un semaforo por pantalla
				Semaforo semaf = new Semaforo();
				simuladorPane.add(semaf.getLbl());
				guardarObjetoBD(semaf);
				logger.log( Level.INFO, "Objeto Semáforo añadido");
				//Movimiento del semáforo
        		Thread moverSemaf = new Thread() {
        			@SuppressWarnings("removal")
					public void run(){
        				
        				while (semaf.getY()<500) {
        					try {
    							sleep(MS_SLEEP);
    							//Si es rojo el coche debe parar
    							if (semaf.getColor().toString()==Color.ROJO.toString() && semaf.getY()==370) {
    								
									System.out.println("El coche se detiene");   
									logger.log( Level.INFO, "Semáforo está en rojo");   							
									movimientoCarr.suspend();
									sleep(2000);
									//Llamar al metodo cambiarSemaforo para que se ponga verde
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
        				//eliminar semáforo de la pantalla y de la listaObs
        				simuladorPane.remove(semaf.getLbl());
        	    		semaf.getLbl().setVisible(false);
        	    		listaObs.remove(semaf);
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
				//Hacer aparecer un STOP en pantalla
				Senal stop = new Senal(Tipo.STOP);
				simuladorPane.add(stop.getLbl());
				listaObs.add(stop);
				guardarObjetoBD(stop);
				logger.log( Level.INFO, "Objeto Señal tipo Stop añadido");
				
				//Movimiento del STOP
        		Thread moverStop = new Thread() {
        			
        			@SuppressWarnings("removal")
					public void run(){
        				while (stop.getY()<500) {
        					try {
    							sleep(MS_SLEEP);    							     						
    							if(stop.getY()==370) {    				
    								//Reacción del coche
    								System.out.println("El coche se detiene");   
    								movimientoCarr.suspend();
    								sleep(3000);
    								movimientoCarr.resume();
    								
    							}
    							
    						} catch (InterruptedException e1) {
    							e1.printStackTrace();
    						}
                			stop.mover(0, 20);

        				}
        				//eliminar STOP de la pantalla y de la listaObs
        				simuladorPane.remove(stop.getLbl());
        	    		stop.getLbl().setVisible(false);
        	    		listaObs.remove(stop);
        	    		logger.log( Level.INFO, "Objeto Señal tipo Stop eliminado");
        			}
        				
        			
        		};
				
        		moverStop.start();
			}
        });
        /*BotÃ³n que cree un obstaculo Animal*/
        bAnimal.addActionListener(new ActionListener() {
        	@Override
			public void actionPerformed(ActionEvent e) {
				//Hacer aparecer un rebaño por pantalla
				Animal animal = new Animal();
				simuladorPane.add(animal.getLbl());
				listaObs.add(animal);
				guardarObjetoBD(animal);
				logger.log( Level.INFO, "Objeto Animal añadido");
				//movimiento del animal y reacción del coche
				cocheReaccion(animal,miCoche);
        		Thread moverAnimal = new Thread() {
        			
        			public void run(){
        				//int aceleracion = 100;
        				while (animal.getY()<500) {
        					try {
    							sleep(MS_SLEEP);
    							
    						} catch (InterruptedException e1) {
    							e1.printStackTrace();
    						}
                			animal.mover(0, 20);


        				}
        				//eliminar semáforo de la pantalla y de la listaObs
        				System.out.println("SALE");
        				simuladorPane.remove(animal.getLbl());
        				listaObs.remove(animal);
        				logger.log( Level.INFO, "Objeto Animal eliminado");
        	    		animal.getLbl().setVisible(false);
        	    		
        			}
        				
        			
        		};
				
        		moverAnimal.start();
			}
        });
        
        salir.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				finSimulacion(Estado.EXITO);
			}
		});
        
        movimientoCarr = movimientoCarretera(true);
        movimientoCarr.start();
	}
      
	
	/**
	 * Método que contiene el hilo de movimiento del condo (carretera)
	 * @param activo
	 * @return
	 */
		
	public Thread movimientoCarretera(boolean activo){
		return new Thread(){
			//Crear la imagen del ultimo fondo
			Image fondoImg = new ImageIcon(getClass().getResource("../simulador/img/FONDO COCHE (16).jpg")).getImage();
			ImageIcon fondoIcon = new ImageIcon(fondoImg.getScaledInstance(TAM_VENT.width,TAM_VENT.height, Image.SCALE_SMOOTH));
			
			@Override
            public void run(){            
				//añadir la imagen a la pantalla
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
             		//poner la imagen del numero correspondiente en pantalla
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
            		//si el numero llega a 0 volver a empezar (sensación obtica de movimiento continuo)
            		if (num==0){
            			num = 16;  		
                    }
       			 }
            }
        };
	}
	
	/** 
	 * La reacción que debe tener el coche contemplando todas las situaciones posibles 
	 * @param o Obstaclulo
	 * @param miCoche Coche autónomo
	 */
	
	public static void cocheReaccion(Obstaculo o, Coche miCoche) {
		listaObs.remove(o);
		//Para saber si hay obstaculos con peligro de colisión en la circulación 
		if (listaObs.isEmpty()|| o==null || (listaObs.get(0) instanceof Animal && o instanceof Animal) ||(o instanceof Animal && (listaObs.get(0) instanceof Peaton||listaObs.get(0) instanceof Senal ||listaObs.get(0) instanceof Semaforo))) {
			//Reacción ante un otroCoche
			if (o instanceof OtroCoche) {
				if(miCoche.getX()==CARRIL_DCHO) {
					listaObs.add(o);
					//movimiento de miCoche
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
	        				//RECURSIVIDAD para volver a su carril
	        				cocheReaccion(null,miCoche);
	        			}
					};
        		movimientoOC.start();
			}
				logger.log( Level.INFO, "OtroCoche superado con éxito" );
			}		
			//Reacción ante un animal o en general para volver a su carril
			if (o instanceof Animal || o==null) {
				listaObs.add(o);
				if(miCoche.getX()==CARRIL_IZQ) {
					//movimiento de miCoche
					Thread movimientoA= new Thread() {
	        			public void run(){
	        				try {
								Thread.sleep(700);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
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
			finSimulacion(Estado.FRACASO);
		}
	}
	
	
	/**
	 * Crea un semáformo y aplica recursividad hasta que el semáforo creado tenga un JLabel VERDE
	 */

	public void cambiarSemaforo() {
		Semaforo semafVerde= new Semaforo();
		System.out.println("Color del semáforo:"+semafVerde.getColor().toString());
		//Si el semaforo creado es rojo aplicar recursividad
		if(semafVerde.getColor()==Semaforo.Color.ROJO) {
			cambiarSemaforo();
			
		}else {
			//Hacer aparecer el semáforo verde en pantalla
			semafVerde.setY(370);
			VentanaSimulador.simuladorPane.add(semafVerde.getLbl());
			semafVerde.getLbl().setVisible(true);
			//Movimiento del semáforo
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
	
	/**
	 * Método que da por finalizada la simulación o bien por una colisión o por decisión del usuario mediante bontón salir
	 * @param e Determina si la simulación ha sido exitosa (No ha habido colisiones) o no.
	 */
	
	public static void finSimulacion(Estado e) {
		tiempoFinal = System.currentTimeMillis();
		
		if (e == Estado.EXITO) {
			BD.insertarSimulacion(fecha, (tiempoFinal-tiempoInicial)/1000.00, "EXITO", listaObs);
			
		} else if (e == Estado.FRACASO) {
			System.out.println("Colisión entre:"+listaObs);
			logger.log( Level.SEVERE, "COLISIÓN");
			JOptionPane.showMessageDialog(null, "Se producirá una colisión. Debe mejorar el sistema", "ALERTA", JOptionPane.WARNING_MESSAGE);
			
			BD.insertarSimulacion(fecha, (tiempoFinal-tiempoInicial)/1000.00, "FRACASO", listaObs);
		}
		
		vent.setVisible(false);
		VentanaFin fin = new VentanaFin();
		fin.setVisible(true);
		
	}
	
	/**
	 * Asigna un nombre a cada obstaculo y llama al método insertarObstaculo de la BD
	 * @param o Obstáculo
	 */
	
	public static void guardarObjetoBD(Obstaculo o) {
		String nombre = null;
		
		if (o instanceof Peaton) nombre = "Peaton";
		else if (o instanceof OtroCoche) nombre = "Coche";
		else if (o instanceof Semaforo) nombre = "Semaforo";
		else if (o instanceof Senal) nombre = "Stop";
		else if (o instanceof Animal) nombre = "Animal";
		
		BD.insertarObstaculo(hora(), nombre, fecha);
	}
	
	/**
	 * Formatear hora
	 * @return
	 */
	
	public static String hora(){
        return new SimpleDateFormat("HH:mm:ss a").format(new Date());
    }
}
