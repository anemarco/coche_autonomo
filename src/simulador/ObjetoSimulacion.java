package simulador;

public abstract class ObjetoSimulacion {
	
	/*Atributos*/
	
	protected int x, y;
	protected int a, h;	
	
	/**Constructor
	 * @param x Coordenada x del centro visual del objeto
	 * @param y Coordenada y del centro visual del objeto
	 * @param a Anchura del objeto
	 * @param h Altura del objeto
	 */
	
	public ObjetoSimulacion(int x, int y, int a, int h) {
		this.x = x;
		this.y = y;
		this.a = a;
		this.h = h;
	}
	
	/*Getters y setters*/
	
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	public int getA() {
		return a;
	}

	public void setA(int a) {
		this.a = a;
	}


	public int getH() {
		return h;
	}

	public void setH(int h) {
		this.h = h;
	}


}
