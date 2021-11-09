package Simulador;

public class Coche extends ObjetoSimulacion implements Movible, Chocable {
	
	/*Constructor*/
	
	public Coche(double x, double y, double a, double h) {
		super(x, y, a, h);
	}

	@Override
	public void dibujar() {	
		
	}

	@Override
	public void mover(double dx, double dy) {
		setX(getX() + dx);
		setY(getY() + dy);
	}


	@Override
	public void girar(Movible.Direccion dir) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void acelerar(double a) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean choca(Obstaculo obst) {
		// TODO Auto-generated method stub
		return false;
	}
}

