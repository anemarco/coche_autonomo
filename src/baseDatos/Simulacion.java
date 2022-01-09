package baseDatos;

public class Simulacion {
	
	private String fecha;
	private long duracion;
	private String dni;
	private String estado;
	
	public Simulacion(String fecha, long duracion, String estado, String dni) {
		super();
		this.fecha = fecha;
		this.duracion = duracion;
		this.dni = dni;
		this.estado = estado;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public long getDuracion() {
		return duracion;
	}

	public void setDuracion(long duracion) {
		this.duracion = duracion;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}
	
}
