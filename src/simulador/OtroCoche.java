package simulador;

import java.awt.Image;
import java.awt.Rectangle;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class OtroCoche extends Obstaculo {
	
	public static final Rectangle TAMANYO = new Rectangle(75, 150);
	
	public OtroCoche(int x, int y) {
		
		super(x, y, TAMANYO.width, TAMANYO.height);
		this.lbl = crearLabel();
		this.lbl.setBounds(x, y, TAMANYO.width, TAMANYO.height);
	
	}

	public OtroCoche(int x, int y, int a, int h) {
			super(x, y, a, h);
	}
	

	@Override
	public JLabel crearLabel() {
		Image cocheImg = new ImageIcon(getClass().getResource("../simulador/img/otroCoche.png")).getImage();
		ImageIcon cocheIcon = new ImageIcon(cocheImg.getScaledInstance(TAMANYO.width, TAMANYO.height, Image.SCALE_SMOOTH));
		JLabel lbl =new JLabel(cocheIcon);
		
		return lbl;
	}

}
