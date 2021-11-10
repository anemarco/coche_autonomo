package simulador;

public abstract class ObjetoSimulacion {
	
	/*Atributos*/
	
	protected double x, y;
	protected double a, h;	
	protected String img;
	
	/**Constructor
	 * @param x Coordenada x del centro visual del objeto
	 * @param y Coordenada y del centro visual del objeto
	 * @param a Anchura del objeto
	 * @param h Altura del objeto
	 */
	
	public ObjetoSimulacion(double x, double y, double a, double h, String img) {
		this.x = x;
		this.y = y;
		this.a = a;
		this.h = h;
		this.img = img;
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

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}
}
