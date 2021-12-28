package simulador;

import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import ventanas.VentanaSimulador;

public class Animal extends Obstaculo {

	//Constantes
	public static final Rectangle TAMANYO = new Rectangle(80, 80);
	public static final Point COORD_DEFECT = new Point(VentanaSimulador.CARRIL_IZQ, 100); 
	
	//constructor
	
	public Animal() {
		super(COORD_DEFECT.x, COORD_DEFECT.y, TAMANYO.width, TAMANYO.height);
		this.lbl = crearLabel();
		this.lbl.setBounds(COORD_DEFECT.x, COORD_DEFECT.y, TAMANYO.width, TAMANYO.height);
	}
	

	@Override
	public JLabel crearLabel() {
		Image animalImg = new ImageIcon(getClass().getResource("../simulador/img/rebano.png")).getImage();
		ImageIcon animalIcon = new ImageIcon(animalImg.getScaledInstance(TAMANYO.width, TAMANYO.height, Image.SCALE_SMOOTH));
		JLabel lbl = new JLabel(animalIcon);
		return lbl;
	}

	
}
