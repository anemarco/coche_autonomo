package Simulador;

public class SensorCoche {
	
	/*Atributos*/
	
	double x, y;
	double r;
	
	/*Constructor*/
	
	public SensorCoche(double x, double y, double r) {
		this.x = x;
		this.y = y;
		this.r = r;
	}
	
	/*Setters*/

	public void setX(double x) {
		this.x = x;
	}

	public void setY(double y) {
		this.y = y;
	}
	
	/**Método que indica si un obstáculo es precibido por los sensores del coche
	 * @param obs Obstáculo que se quiere comprobar
	 * @return Obstáculo que es detectado
	 */
	
	public Obstaculo detectaObs(Obstaculo obs) {
		double dist = Math.sqrt(Math.pow(x-obs.getX(), 2)+Math.pow(y-obs.getY(), 2));
		if (dist < r) return obs;
		else return null;
	}
}
