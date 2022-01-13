package simulador;

import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import ventanas.VentanaSimulador;

public class Coche extends ObjetoSimulacion implements Movible {
	
	/*Constantes*/
	
	public static final Rectangle TAMANYO = new Rectangle(100, 150);
	public static final Point COORD_DEFECT = new Point(VentanaSimulador.CARRIL_DCHO, 375); 
	
	/*Atributos*/
	
	protected double velocidad; /*public double velocidad*/
	protected double dir;	/*�ngulo actual del coche*/
	
	/**Constructor por defecto*/
	
	public Coche() {
		
		super(COORD_DEFECT.x, COORD_DEFECT.y, TAMANYO.width, TAMANYO.height);
		this.lbl = crearLabel();
		this.lbl.setBounds(COORD_DEFECT.x, COORD_DEFECT.y, TAMANYO.width, TAMANYO.height);

	}
	
	/*Constructor*/
	
	public Coche(int x, int y) {
		
		super(x, y, TAMANYO.width, TAMANYO.height);
		this.lbl = crearLabel();
		this.lbl.setBounds(x, y, TAMANYO.width, TAMANYO.height);
	
	}
	
	public Coche(int x, int y, int a, int h, double v, double dir) {
		
		super(x, y, a, h);
		this.velocidad = v;
		this.dir= dir;
	}

	/*Getters y setters*/
	
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
	}
	
	
	@Override
	public void setY(int y) {
		this.y = y;
		this.lbl.setLocation(x, y);
	}
	
	//M�todo de interfaz. Consiste en cambiar los parametos del coche

	@Override
	public void mover(int dx, int dy) {
		setX(getX() + dx);
		setY(getY() + dy);
	}
	
	@Override
	public JLabel crearLabel() {
		Image cocheImg = new ImageIcon(getClass().getResource("../simulador/img/coche.png")).getImage();
		ImageIcon cocheIcon = new ImageIcon(cocheImg.getScaledInstance(TAMANYO.width, TAMANYO.height, Image.SCALE_SMOOTH));
		JLabel lbl =new JLabel(cocheIcon);
		
		return lbl;
	}
	
}

