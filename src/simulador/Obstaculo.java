package simulador;

public abstract class Obstaculo extends ObjetoSimulacion {
	
	/*Constructor*/

	public Obstaculo(int x, int y, int a, int h) {
		super(x, y, a, h);
	}

	//M�todo de interfaz. Consiste en cambiar los parametos de los obst�culos
	public void mover(int dx, int dy) {
		setX(getX() + dx);
		setY(getY() + dy);
	}
}
