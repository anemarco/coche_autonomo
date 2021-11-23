package simulador;

import javax.swing.JLabel;

public abstract class ObjetoSimulacion {
	
	/*Atributos*/
	
	protected int x, y;
	protected int a, h;	
	protected JLabel lbl;
	
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
		this.lbl.setLocation(x, y);
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
		this.lbl.setLocation(x, y);
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

	public JLabel getLbl() {
		return lbl;
	}

	public void setLbl(JLabel lbl) {
		this.lbl = lbl;
	}
	
	public abstract JLabel crearLabel();

}
