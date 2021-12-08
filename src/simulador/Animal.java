package simulador;

import javax.swing.JLabel;

public class Animal extends Obstaculo {

	//constructor
	
	public Animal(int x, int y, int a, int h) {
		super(x, y, a, h);
	}
	

	@Override
	public JLabel crearLabel() {
		return null;
		
	}

	
}
