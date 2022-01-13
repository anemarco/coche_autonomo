package simulador;

public abstract class Obstaculo extends ObjetoSimulacion {
	
	/*Constructor*/

	public Obstaculo(int x, int y, int a, int h) {
		super(x, y, a, h);
	}

	//Método de interfaz. Consiste en cambiar los parametos de los obstáculos
	public void mover(int dx, int dy) {
		setX(getX() + dx);
		setY(getY() + dy);
	}
}
