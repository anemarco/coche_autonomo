package simulador;

public class Semaforo extends Obstaculo {
	
	private static final String IMG_SEMAFORO = "";
	
	public static enum Color {VERDE, NARANJA, ROJO}
	
	/*Atributos*/
	
	private Color color;
	
	/*Constructor*/

	public Semaforo(double x, double y, double a, double h, Color color) {
		super(x, y, a, h, IMG_SEMAFORO);
		this.color = color;
	}
	
	/*Getters y setters*/

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	@Override
	public void colocar() {
		// TODO Auto-generated method stub
		
	}
	
	
}