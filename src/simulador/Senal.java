package simulador;

public class Senal extends Obstaculo {
	
	private static final String IMG_CEDA = "";
	private static final String IMG_STOP = "";
	
	public static enum Tipo {STOP, CEDA, SENTIDO_OBLIGATORIO}
	
	/*Atributos*/
	
	private Tipo tSenal;
	
	/*Constructor*/

	public Senal(double x, double y, double a, double h,Tipo tSenal) {
		super(x, y, a, h, null);
		this.tSenal = tSenal;
		if (tSenal == Tipo.CEDA) setImg(IMG_CEDA);
		if (tSenal == Tipo.STOP) setImg(IMG_STOP);	
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

