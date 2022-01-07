package baseDatos;

public class Simulacion {
	
	private String cod;
	private long fecha;
	private String hora;
	private float duracion;
	
	public Simulacion(String cod, long fecha, String hora, float duracion) {
		super();
		this.cod = cod;
		this.fecha = fecha;
		this.hora = hora;
		this.duracion = duracion;
	}

	public String getCod() {
		return cod;
	}

	public void setCod(String cod) {
		this.cod = cod;
	}

	public long getFecha() {
		return fecha;
	}

	public void setFecha(long fecha) {
		this.fecha = fecha;
	}

	public String getHora() {
		return hora;
	}

	public void setHora(String hora) {
		this.hora = hora;
	}

	public float getDuracion() {
		return duracion;
	}

	public void setDuracion(float duracion) {
		this.duracion = duracion;
	}
}
