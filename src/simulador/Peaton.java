package simulador;

import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Peaton extends Obstaculo implements Movible {
	
	/*Constantes*/
	
	public static final Rectangle TAMANYO = new Rectangle(40, 60);
	public static final Point COORD_DEFECT = new Point(310, 100); 
	
	public Peaton() {
		super(COORD_DEFECT.x, COORD_DEFECT.y, TAMANYO.width, TAMANYO.height);
		crearLabel();
	}
	
	
	/*Constructor*/
	
	public Peaton(int x, int y, int a, int h) {
		super(x, y, a, h);
		crearLabel();
	}
	
	/**Método que mover el objeto dentro de la ventana gráfica. En este caso,
	 * el objeto peatón solo se moverá en el eje x (Atravesando la carretera)
	 * @param dx Desplazamiento en el eje x
	 */
	

	@Override
	public void colocar() {
		// TODO Auto-generated method stub
	}

	@Override
	public void mover(int dx, int dy) {
		setX(getX() + dx);
		setY(getY() + dy);
		
	}

	@Override
	public void girar(double grados) {
		// no se necesita
		
	}

	@Override
	public void acelerar(double a) {
		// no se necesita
		
	}

	@Override
	public void crearLabel() {
		Image img = new ImageIcon(getClass().getResource("../simulador/img/recortePeaton.png")).getImage();
		ImageIcon icon = new ImageIcon(img.getScaledInstance(TAMANYO.width, TAMANYO.height, Image.SCALE_SMOOTH));
		this.lbl = new JLabel(icon);
		this.lbl.setBounds(COORD_DEFECT.x, COORD_DEFECT.y, TAMANYO.width, TAMANYO.height);
	}

}
