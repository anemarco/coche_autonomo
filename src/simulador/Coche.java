package simulador;

import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Coche extends ObjetoSimulacion implements Movible, Chocable {
	
	/*Constantes*/
	
	public static final Rectangle TAMANYO = new Rectangle(100, 150);
	public static final Point COORD_DEFECT = new Point(492, 375); 
	
	/*Atributos*/
	
	protected double velocidad; /*public double velocidad*/
	protected final Sensor S_RECON = new Sensor(0.0, 0.0, 50.0);	/*Sensor de reconocimiento: Avisa de los obstáculos que hay alrededor*/
	protected final Sensor S_PROX = new Sensor(0.0, 0.0, 15.0);	/*Sensor de proximidad: Asegura que el coche matiene una distancia de seguridad con los demás vehículos*/
	protected double dir;	/*�ngulo actual del coche*/
	
	/**Constructor por defecto*/
	
	public Coche() {
		
		super(COORD_DEFECT.x, COORD_DEFECT.y, TAMANYO.width, TAMANYO.height);
		this.lbl = crearLabel();
		this.lbl.setBounds(COORD_DEFECT.x, COORD_DEFECT.y, TAMANYO.width, TAMANYO.height);
		S_RECON.setX(x);
		S_RECON.setY(y);
		S_PROX.setX(x);
		S_PROX.setY(y+h/2); 
	}
	
	/*Constructor*/
	
	public Coche(int x, int y) {
		
		super(x, y, TAMANYO.width, TAMANYO.height);
		this.lbl = crearLabel();
		this.lbl.setBounds(x, y, TAMANYO.width, TAMANYO.height);
		S_RECON.setX(x);
		S_RECON.setY(y);
		S_PROX.setX(x);
		S_PROX.setY(y+h/2); 
	}
	
	public Coche(int x, int y, int a, int h, double v, double dir) {
		
		super(x, y, a, h);
		this.velocidad = v;
		this.dir= dir;
		S_RECON.setX(x);
		S_RECON.setY(y);
		S_PROX.setX(x);
		S_PROX.setY(y+h/2); 
		
	}

	/*Getters y setters*/
	
	public Sensor getSR() {
		return S_RECON;
	}
	
	public Sensor getSP() {
		return S_PROX;
	}
	public double getVelocidad() {
		return velocidad;
	}

	public void setVelocidad(double velocidad) {
		this.velocidad = velocidad;
	}

	public double getDir() {
		return dir;
	}

	public void setDir(double d) {
		this.dir = d;
	}
	
	/*Se reescriben los setter de ObjetoSimulación, pues al cambiar las coordenadas
	 * de el coche, también cambiarán las de su sensor*/
	
	@Override
	public void setX(int x) {
		this.x = x;
		this.lbl.setLocation(x, y);
		S_RECON.setX(x);
		S_PROX.setX(x);
	}
	
	
	@Override
	public void setY(int y) {
		this.y = y;
		this.lbl.setLocation(x, y);
		S_RECON.setY(y);
		S_PROX.setY(y);
	}
	
	/*M�todo que indica si el coche se ha chocado con un obst�culo
	 * Recibe un objeto de la clase obst�culo
	 * Si la distancia de las posici�n (x e y) del coche y obst�culo son menores que las proporciones de sus imagenes devuelve TRUE
	 */
	@Override
	public boolean choca(Obstaculo obst) {
		if ((this.x-obst.getX() < this.h/2 + obst.getH()/2) && (Math.abs(y-obst.getY()) < Math.abs(a/2 + obst.getA()/2))) return true;	/*De frente y de cualquiera de los lados*/
		return false;
	}

	@Override
	public void mover(int dx, int dy) {
		setX(getX() + dx);
		setY(getY() + dy);
	}

	/*M�todo que indica los grados que el choche debe tomar para maniobrar o esquibar un obst�culo
	 * Desde la ventana se le enviar� la cantidad de grados que debe moverse por maniobra u objeto
	 * As� reaccionara de manera aut�noma
	 */
	
	@Override
	public void acelerar(double a) {
		velocidad = velocidad + a;
		
	}

	@Override
	public void girar(double grados) {
		
		
	}
	
	@Override
	public JLabel crearLabel() {
		Image cocheImg = new ImageIcon(getClass().getResource("../simulador/img/coche.png")).getImage();
		ImageIcon cocheIcon = new ImageIcon(cocheImg.getScaledInstance(TAMANYO.width, TAMANYO.height, Image.SCALE_SMOOTH));
		JLabel lbl =new JLabel(cocheIcon);
		
		return lbl;
	}
	
	/*
	@Override
	public void girar(double grados) {
		this.setDir(this.getDir()+grados);
		this.setX(Math.cos(this.dir));
		this.setY(Math.sin(this.dir));
		
	}
	*/
	
}

