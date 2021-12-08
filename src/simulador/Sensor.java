package simulador;

public class Sensor {
	
	/*Atributos*/
	
	double x, y;
	double r;
	
	/*Constructor*/
	
	public Sensor(double x, double y, double r) {
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
	
	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
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
	
	public boolean choca(Sensor s) {
		double x2 = s.getX();
		double y2 = s.getY();
		
		double dist = Math.sqrt(Math.pow(x-x2, 2)+Math.pow(y-y2, 2));
		
		if (dist <= r + s.r) return true;
		else return false;
	}
}
