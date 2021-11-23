package simulador;

import javax.swing.JLabel;

public class Senal extends Obstaculo {
	
	public static enum Tipo {STOP, CEDA, SENTIDO_OBLIGATORIO}
	
	/*Atributos*/
	
	private Tipo tSenal;
	
	/*Constructor*/

	public Senal(int x, int y, int a, int h,Tipo tSenal) {
		super(x, y, a, h);
		this.tSenal = tSenal;
		
		//if (tSenal == Tipo.CEDA) setImg(IMG_CEDA);
		//if (tSenal == Tipo.STOP) setImg(IMG_STOP);	
	}
	
	/*Getter*/

	public Tipo getTipo() {
		return tSenal;
	}

	@Override
	public void colocar() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public JLabel crearLabel() {
		return null;
		
	}
	
}

