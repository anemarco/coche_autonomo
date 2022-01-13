package simulador;

public interface Movible {
	
	//public static enum Direccion {DCHA, IZQ}
		
		/**Método que permite mover el objeto dentro de la ventana gráfica
		 * @param dx Desplazamiento en el eje x
		 * @param dy Desplazamiento en el eje y
		 */
		
		public void mover(int dx, int dy);

}
