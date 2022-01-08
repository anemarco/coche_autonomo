package simulador;

import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Semaforo extends Obstaculo {
	
	public static enum Color {VERDE, ROJO}
	
	public static final Rectangle TAMANYO = new Rectangle(55, 82);
	public static final Point COORD_DEFECT = new Point(620, 30); 
	
	/*Atributos*/
	
	private Color color;
	
	/*Constructor*/
	
	public Semaforo() {
		super(COORD_DEFECT.x, COORD_DEFECT.y, TAMANYO.width, TAMANYO.height);
		
		ArrayList<Color> colores = new ArrayList<>();
		colores.add(Color.ROJO);
		colores.add(Color.VERDE);
		Color color = colores.get(new Random().nextInt(colores.size()));
		
		this.color = color;
		this.lbl = crearLabel();
		this.lbl.setBounds(COORD_DEFECT.x, COORD_DEFECT.y, TAMANYO.width, TAMANYO.height);
	}

	public Semaforo(int x, int y, int a, int h, Color color) {
		super(x, y, a, h);
		this.color = color;
	}
	
	/*Getters y setters*/

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}
	

	@Override
	public JLabel crearLabel() {
		Image img = null;
		
		if (color == Color.VERDE) {
			img = new ImageIcon(getClass().getResource("../simulador/img/semafVerde.png")).getImage();
		} else if (color == Color.ROJO) {
			img = new ImageIcon(getClass().getResource("../simulador/img/semafRojo.png")).getImage();
		}
		
		ImageIcon icon = new ImageIcon(img.getScaledInstance(TAMANYO.width, TAMANYO.height, Image.SCALE_SMOOTH));
		JLabel lbl = new JLabel(icon);	
		return lbl;
	}
	
	
}
