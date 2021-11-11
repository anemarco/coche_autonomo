package simulador;

public class Coche extends ObjetoSimulacion implements Movible, Chocable {
	
	private static final  String IMG_COCHE = "/Coche_autonomo/src/simulador/img/coche.png";
	
	protected final Sensor S_RECON = new Sensor(0.0, 0.0, 50.0);	/*Sensor de reconocimiento: Avisa de los obstáculos que hay alrededor*/
	protected final Sensor S_PROX = new Sensor(0.0, 0.0, 15.0);		/*Sensor de proximidad: Asegura que el coche matiene una distancia de seguridad con los demás vehículos*/
	
	public double velocidad;
	
	/*Constructor*/
	
	public Coche(double x, double y, double a, double h, double v) {
		super(x, y, a, h, IMG_COCHE);
		this.velocidad = v;
		S_RECON.setX(x);
		S_RECON.setY(y);
		S_PROX.setX(x);
		S_PROX.setY(y+h/2); 
	}
	
	/*Getters y setters*/
	
	public Sensor getSR() {
		return S_RECON;
	}
	
	public Sensor getSP() {
		return S_PROX;
	}
	
	/*Se reescriben los setter de ObjetoSimulación, pues al cambiar las coordenadas
	 * de el coche, también cambiarán las de su sensor*/
	
	@Override
	public void setX(double x) {
		this.x = x;
		S_RECON.setX(x);
		S_PROX.setX(x);
	}
	
	@Override
	public void setY(double y) {
		this.y = y;
		S_RECON.setY(y);
		S_PROX.setY(y);
	}
	
	@Override
	public boolean choca(Obstaculo obst) {
		if ((x-obst.getX()) < (h/2 + obst.getH()/2)) return true;	/*De frente*/
		if ((y-obst.getY()) < (a/2 + obst.getA()/2)) return true;	/*Del lado izquierdo*/
		if ((obst.getY()-y) < (a/2 + obst.getA()/2)) return true;	/*Del lado derecho*/
		return false;
	}

	@Override
	public void mover(double dx, double dy) {
		setX(getX() + dx);
		setY(getY() + dy);
	}


	@Override
	public void girar(Movible.Direccion dir) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void acelerar(double a) {
		velocidad = velocidad + a;
		
	}
}

