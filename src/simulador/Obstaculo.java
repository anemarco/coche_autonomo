package simulador;

public abstract class Obstaculo extends ObjetoSimulacion {
	
	/*Constructor*/

	public Obstaculo(double x, double y, double a, double h, String img) {
		super(x, y, a, h, img);
	}
	
	/**Método que permita seleccionar un obstáculo mediante un click con el ratón
	 */
	
	public void seleccionar() {
		//MouseEvent
		
	}
	
	/**Método que haga que el obstáculo se coloque en la simulación una vez seleccionado
	 */
	
	public abstract void colocar();
	
}
