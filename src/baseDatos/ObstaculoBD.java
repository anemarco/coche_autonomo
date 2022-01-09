package baseDatos;

public class ObstaculoBD {
	
	private String hora;
	private String nombre;
	private String fecha;
	
	/**
	 * Clase que representa la tabla obstaculo de la base de datos
	 * @param hora Tiempo de creación
	 * @param nombre Nombre del objeto
	 * @param fecha Fecha en la que se ha ejecutado la simulación a la que pertenece el objeto
	 */
	
	public ObstaculoBD(String hora, String nombre, String fecha) {
		super();
		this.hora = hora;
		this.nombre = nombre;
		this.fecha = fecha;
	}

	public String getHora() {
		return hora;
	}

	public void setHora(String hora) {
		this.hora = hora;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

}
