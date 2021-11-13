package simulador;

public class Coche extends ObjetoSimulacion implements Movible, Chocable {
	
	private static final  String IMG_COCHE = "/Coche_autonomo/src/simulador/img/coche.png";
	
	protected double velocidad; /*public double velocidad*/
	protected final Sensor S_RECON = new Sensor(0.0, 0.0, 50.0);	/*Sensor de reconocimiento: Avisa de los obst√°culos que hay alrededor*/
	protected final Sensor S_PROX = new Sensor(0.0, 0.0, 15.0);	/*Sensor de proximidad: Asegura que el coche matiene una distancia de seguridad con los dem√°s veh√≠culos*/
	protected double dir;	/*¡ngulo actual del coche*/
	
	
	/*Constructor*/
	
	public Coche(double x, double y, double a, double h, double v, double dir) {
		super(x, y, a, h, IMG_COCHE);
		this.velocidad = v;
		S_RECON.setX(x);
		S_RECON.setY(y);
		S_PROX.setX(x);
		S_PROX.setY(y+h/2); 
		this.dir= dir;
	}

	/*Getters y setters*/
	
	public Sensor getSR() {
		return S_RECON;
	}
	
	public Sensor getSP() {
		return S_PROX;
	}
	public double getVelocidad() {
		return velocidad;
	}

	public void setVelocidad(double velocidad) {
		this.velocidad = velocidad;
	}

	public double getDir() {
		return dir;
	}

	public void setDir(double dir) {
		this.dir = dir;
	}
	
	/*Se reescriben los setter de ObjetoSimulaci√≥n, pues al cambiar las coordenadas
	 * de el coche, tambi√©n cambiar√°n las de su sensor*/
	
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
	


	/*MÈtodo que indica si el coche se ha chocado con un obst·culo
	 * Recibe un objeto de la clase obst·culo
	 * Si la distancia de las posiciÛn (x e y) del coche y obst·culo son menores que las proporciones de sus imagenes devuelve TRUE
	 */
	@Override
	public boolean choca(Obstaculo obst) {
		if ((this.x-obst.getX() < this.h/2 + obst.getH()/2) && (Math.abs(y-obst.getY()) < Math.abs(a/2 + obst.getA()/2))) return true;	/*De frente y de cualquiera de los lados*/
		return false;
	}

	@Override
	public void mover(double dx, double dy) {
		setX(getX() + dx);
		setY(getY() + dy);
	}

	/*MÈtodo que indica los grados que el choche debe tomar para maniobrar o esquibar un obst·culo
	 * Desde la ventana se le enviar· la cantidad de grados que debe moverse por maniobra u objeto
	 * AsÌ reaccionara de manera autÛnoma
	 */
	


	@Override
	public void acelerar(double a) {
		velocidad = velocidad + a;
		
	}
	@Override
	public void girar(double grados) {
		// TODO Auto-generated method stub
		
	}
	
}

