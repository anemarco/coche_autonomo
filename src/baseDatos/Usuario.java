package baseDatos;

public class Usuario {
	
	private String nombre;
	private String dni;
	private String contrasenia;
	private String apellido;
	
	public Usuario() {
		super();
	}

	public Usuario(String dni, String nombre, String apellido, String contrasenia) {
		super();
		this.nombre = nombre;
		this.dni = dni;
		this.contrasenia = contrasenia;
		this.apellido = apellido;
	}

	public String getContrasenia() {
		return contrasenia;
	}

	public void setContrasenia(String contrasenia) {
		this.contrasenia = contrasenia;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	@Override
	public String toString() {
		return "Usuario [nombre=" + nombre + ", dni=" + dni + ", contrase�a=" + contrasenia + "]";
	}
	

}
