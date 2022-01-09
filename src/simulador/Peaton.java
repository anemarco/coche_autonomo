package simulador;

import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Peaton extends Obstaculo {
	
	/*Constantes*/
	
	public static final Rectangle TAMANYO = new Rectangle(40, 60);
	public static final Point COORD_DEFECT = new Point(110, 20); 
	
	public Peaton() {
		
		super(COORD_DEFECT.x, COORD_DEFECT.y, TAMANYO.width, TAMANYO.height);
		this.lbl = crearLabel();
		this.lbl.setBounds(COORD_DEFECT.x, COORD_DEFECT.y, TAMANYO.width, TAMANYO.height);
	}
	
	
	/*Constructor*/
	
	public Peaton(int x, int y, int a, int h) {
		super(x, y, a, h);
		this.lbl = crearLabel();
		this.lbl.setBounds(x, y, TAMANYO.width, TAMANYO.height);
	}
	
	/**Método que mover el objeto dentro de la ventana gráfica. En este caso,
	 * el objeto peatón solo se moverá en el eje x (Atravesando la carretera)
	 * @param dx Desplazamiento en el eje x
	 */
	
	@Override
	public JLabel crearLabel() {
		Image img = new ImageIcon(getClass().getResource("../simulador/img/recortePeaton.png")).getImage();
		ImageIcon icon = new ImageIcon(img.getScaledInstance(TAMANYO.width, TAMANYO.height, Image.SCALE_SMOOTH));
		JLabel lbl = new JLabel(icon);
		return lbl;
	}

}
