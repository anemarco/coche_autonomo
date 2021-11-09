package Simulador;

public abstract class ObjetoSimulacion {
	
	/*Atributos*/
	
	protected double x, y;
	protected double a, h;	
	
	/**Constructor
	 * @param x Coordenada x del centro visual del objeto
	 * @param y Coordenada y del centro visual del objeto
	 * @param a Anchura del objeto
	 * @param h Altura del objeto
	 */
	
	public ObjetoSimulacion(double x, double y, double a, double h) {
		this.x = x;
		this.y = y;
		this.a = a;
		this.h = h;
	}
	
	/*Getters y setters*/
	
	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}
	
	public double getA() {
		return a;
	}


	public void setA(double a) {
		this.a = a;
	}


	public double getH() {
		return h;
	}

	public void setH(double h) {
		this.h = h;
	}

	/**Método que permite dibujar el objeto en la ventana gráfica
	 */

	public abstract void dibujar();

}
