package Simulador;

public class Senal extends Obstaculo {
	
	public static enum TipoSenales {STOP, CEDA, ENTRADA_PROHIBIDA}
	
	/*Atributos*/
	
	private TipoSenales tSenal;
	
	/*Constructor*/

	public Senal(double x, double y, double a, double h, TipoSenales tSenal) {
		super(x, y, a, h);
		this.tSenal = tSenal;
	}
	
	/*Getter*/

	public TipoSenales gettSenal() {
		return tSenal;
	}

	@Override
	public void colocar() {
		// TODO Auto-generated method stub
		
	}
	
}

