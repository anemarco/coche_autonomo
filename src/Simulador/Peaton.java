package Simulador;

public class Peaton extends Obstaculo {
	
	/*Constructor*/
	
	public Peaton(double x, double y, double a, double h) {
		super(x, y, a, h);
	}
	
	/**Método que mover el objeto dentro de la ventana gráfica. En este caso,
	 * el objeto peatón solo se moverá en el eje x (Atravesando la carretera)
	 * @param dx Desplazamiento en el eje x
	 */
	
	public void mover(double dx) {
		setX(getX() + dx);
	}
	
	

	@Override
	public void colocar() {
		// TODO Auto-generated method stub
	}

}
