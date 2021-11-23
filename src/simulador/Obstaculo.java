package simulador;

public abstract class Obstaculo extends ObjetoSimulacion {
	
	/*Constructor*/

	public Obstaculo(int x, int y, int a, int h) {
		super(x, y, a, h);
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
