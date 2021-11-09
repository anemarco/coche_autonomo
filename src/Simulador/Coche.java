package Simulador;

public class Coche extends ObjetoSimulacion implements Movible, Chocable {
	
	protected final SensorCoche SENSOR = new SensorCoche(0.0, 0.0, 25.0);
	
	/*Constructor*/
	
	public Coche(double x, double y, double a, double h) {
		super(x, y, a, h);
		SENSOR.setX(x);
		SENSOR.setY(y);
	}
	
	/*Se reescriben los setter de ObjetoSimulación, pues al cambiar las coordenadas
	 * de el coche, también cambiarán las de su sensor*/
	
	@Override
	public void setX(double x) {
		this.x = x;
		SENSOR.setX(x);
	}
	
	@Override
	public void setY(double y) {
		this.y = y;
		SENSOR.setY(y);
	}

	@Override
	public void dibujar() {	
		
	}
	
	@Override
	public boolean choca(Obstaculo obst) {
		if ((x-obst.getX()) < (h/2 + obst.getH()/2)) return true;	/*De frente*/
		if ((y-obst.getY()) < (a/2 + obst.getA()/2)) return true;	/*Del lado izquierdo*/
		if ((obst.getY()-y) < (a/2 + obst.getA()/2)) return true;	/*Del lado derecho*/
		return false;
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
}

