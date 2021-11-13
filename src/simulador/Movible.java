package simulador;

public interface Movible {
	
	//public static enum Direccion {DCHA, IZQ}
		
		/**Método que permite mover el objeto dentro de la ventana gráfica
		 * @param dx Desplazamiento en el eje x
		 * @param dy Desplazamiento en el eje y
		 */
		
		public void mover(double dx, double dy);
		
		/**Método que permite cambiar la orientación del objeto
		 * @param dir Dirección a la que desea girar
		 */
		
		public void girar(double grados);
		
		/**Método que permita variar la velocidad del objeto
		 * @param a Cantidad de aceleración. En caso de que sea negativa estaría fenando
		 */
		
		public void acelerar(double a);

}
