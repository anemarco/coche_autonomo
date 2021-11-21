package simulador;

public class Peaton extends Obstaculo implements Movible{
	
	private static final  String IMG_PEATON = "";
	
	/*Constructor*/
	
	public Peaton(double x, double y, double a, double h) {
		super(x, y, a, h, IMG_PEATON);
	}
	
	/**Método que mover el objeto dentro de la ventana gráfica. En este caso,
	 * el objeto peatón solo se moverá en el eje x (Atravesando la carretera)
	 * @param dx Desplazamiento en el eje x
	 */
	

	@Override
	public void colocar() {
		// TODO Auto-generated method stub
	}

	@Override
	public void mover(double dx, double dy) {
		setX(getX() + dx);
		setY(getY() + dy);
		
	}

	@Override
	public void girar(double grados) {
		// no se necesita
		
	}

	@Override
	public void acelerar(double a) {
		// no se necesita
		
	}

}
