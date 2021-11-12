package baseDatos;

public class Usuario {
	
	private String nombre;
	private String dni;
	private String contrasenia;
	
	public Usuario() {
		super();
	}

	public Usuario(String nombre, String dni, String contrasenia) {
		super();
		this.nombre = nombre;
		this.dni = dni;
		this.contrasenia = contrasenia;
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

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	@Override
	public String toString() {
		return "Usuario [nombre=" + nombre + ", dni=" + dni + ", contraseña=" + contrasenia + "]";
	}
	

}
