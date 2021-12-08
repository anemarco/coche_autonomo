package simulador;

import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Senal extends Obstaculo {
	
	public static enum Tipo {STOP, CEDA, SENTIDO_OBLIGATORIO}
	
	public static final Rectangle TAMANYO = new Rectangle(58, 58);
	public static final Point COORD_DEFECT = new Point(620, 30); 
	
	/*Atributos*/
	
	private Tipo tSenal;
	
	/*Constructor*/
	
	public Senal(Tipo tSenal) {
		super(COORD_DEFECT.x, COORD_DEFECT.y, TAMANYO.width, TAMANYO.height);
		this.tSenal = tSenal;
		this.lbl = crearLabel();
		this.lbl.setBounds(COORD_DEFECT.x, COORD_DEFECT.y, TAMANYO.width, TAMANYO.height);
	}

	public Senal(int x, int y, int a, int h, Tipo tSenal) {
		super(x, y, a, h);
		this.tSenal = tSenal;
	}
	
	/*Getter*/

	public Tipo getTipo() {
		return tSenal;
	}

	@Override
	public JLabel crearLabel() {
		Image img = null;
		
		if (tSenal == Tipo.STOP) {
			img = new ImageIcon(getClass().getResource("../simulador/img/stop.png")).getImage();
		} else if (tSenal == Tipo.CEDA) {
			
		} else if (tSenal == Tipo.SENTIDO_OBLIGATORIO) {
			
		}
		
		ImageIcon icon = new ImageIcon(img.getScaledInstance(TAMANYO.width, TAMANYO.height, Image.SCALE_SMOOTH));
		JLabel lbl = new JLabel(icon);	
		return lbl;
		
	}
	
}

