package Simulador;

public class Senal extends Obstaculo {
	
	public static enum Tipo {STOP, CEDA, ENTRADA_PROHIBIDA}
	
	/*Atributos*/
	
	private Tipo tSenal;
	
	/*Constructor*/

	public Senal(double x, double y, double a, double h, Tipo tSenal) {
		super(x, y, a, h);
		this.tSenal = tSenal;
	}
	
	/*Getter*/

	public Tipo getTipo() {
		return tSenal;
	}

	@Override
	public void colocar() {
		// TODO Auto-generated method stub
		
	}
	
}

