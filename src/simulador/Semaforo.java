package simulador;

import javax.swing.JLabel;

public class Semaforo extends Obstaculo {
	
	public static enum Color {VERDE, NARANJA, ROJO}
	
	/*Atributos*/
	
	private Color color;
	
	/*Constructor*/

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
	public void colocar() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public JLabel crearLabel() {
		return null;
		
	}
	
	
}
