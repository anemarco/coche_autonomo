package baseDatos;

import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.TreeMap;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class BD {
	
	private static SimpleDateFormat sdf = new SimpleDateFormat( "dd/MM/yyyy HH:mm:ss" ); 
	private static SimpleDateFormat sdf2 = new SimpleDateFormat( "dd/MM/yyyy" ); 
	
	static Connection con;
	
	/**
	 * M�todo que crea la conexion con la base de datos
	 * @param nombreBD nombre del archivo de sqliteman de la base de datos
	 * @return devuelve la conexion
	 */

	
	public static void  initBD(String nombreBD) {
		try {
			Class.forName("org.sqlite.JDBC");
			con = DriverManager.getConnection("jdbc:sqlite:"+nombreBD);
		}catch (ClassNotFoundException e){
			e.printStackTrace();
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}
	/**
	 * Metodo que hara que se cierre la base de datos
	 * @param con parametro que establece la conexion con la base de datos
	 */
	public static void closeBD() {
		if(con!=null) {
			try {
				con.close();
			}catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Metodo que crea las tablas de la base de datos y algunos datos
	 */
	public static void crearTablas() {
		
		String sent1 = "CREATE TABLE IF NOT EXISTS usuario(dni String, nombre String, apellido String, contrasenia String);";
		String sent2 = "CREATE TABLE IF NOT EXISTS simulacion(cod String, fecha bigint, hora String, duracion float);";
		String sent3 = "CREATE TABLE IF NOT EXISTS obstaculo(cod String, nombre String);";
		String sent4 = "CREATE TABLE IF NOT EXISTS obstaculoUsado(cod_obs String, cod_sim String);";
		
		String sent5 = "INSERT INTO usuario VALUES ('87623453Y', 'Pepe', 'Rodrigez', 'gdx75');";
		String sent6 = "INSERT INTO usuario VALUES ('79172639G', 'Alba', 'Perez', '12345');";
		
		Statement st= null;
		
		try {
			st = con.createStatement();
			
			st.executeUpdate(sent1);
			st.executeUpdate(sent2);
			st.executeUpdate(sent3);
			st.executeUpdate(sent4);
			st.executeUpdate(sent5);
			st.executeUpdate(sent6);
			
			guardarLog(sent1);
			
		} catch (SQLException e) {
			e.printStackTrace();
			
		} finally {
			if(st!=null) {
				try {
					st.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * Método que lee todos los usuarios de la base de datos, los convierte en objetos 
	 * y los guarda en una lista
	 * @return	Lista de usuarios de la BD
	 */
	 public static ArrayList<Usuario>  getUsuarios() {
		 try (Statement st = con.createStatement()) {
			 ArrayList<Usuario> lUsuarios = new ArrayList<>();
			 String sent = "SELECT * FROM usuario;";
			 ResultSet rs = st.executeQuery(sent);
			 
			 while (rs.next()) {
				 String dni = rs.getString("dni");
				 String nombre = rs.getString("nombre");
				 String apellido = rs.getString("apellido");
				 String contrasenia = rs.getString("contrasenia");
				 
				 lUsuarios.add(new Usuario(dni, nombre, apellido, contrasenia));
			 }
			 
			 return lUsuarios;
		 } catch (SQLException e) {
			 e.printStackTrace();
			 return null;
		 }
	 }
	 
	 /**
	  * Método que lee todas las simulaciones de la base de datos, las convierte a objetos
	  * y las guarda en una lista
	  * @param con
	  * @return Lista de todas las simulaciones de la BD
	  */
	 
	 public static ArrayList<Simulacion> getSimulaciones() {
		 try (Statement st = con.createStatement()) {
			 ArrayList<Simulacion> lSimulaciones = new ArrayList<>();
			 String sent = "SELECT * FROM simulacion;";
			 ResultSet rs = st.executeQuery(sent);
			 
			 while (rs.next()) {
				 String cod = rs.getString("cod");
				 
			 }
			 
			 return lSimulaciones;
		 } catch (SQLException e) {
			 e.printStackTrace();
			 return null;
		 }
	 }
	
	
	/**
	 * Insertara el usuario que se solicite
	 * @param con parametro que establece la conexion con la base de datos
	 * @param nombre nombre del usuario que se desea insertar
	 * @param dni dni del usuario que se desea insertar
	 * @param contrasenia contrasenia del usuario que se desea insertar
	 */
	public static void insertarUsuario(String dni, String nombre, String apellido, String contrasenia) {
		String sentSQL = "INSERT INTO usuario VALUES('"+dni+"','"+nombre+"','"+apellido+"',"+contrasenia+");";
		Statement stmt;
		try {
			stmt = con.createStatement();
			stmt.executeUpdate(sentSQL);
			guardarLog(sentSQL);
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	/**
	 * Eliminara el usuario que se necesite
	 * @param con parametro que establece la conexion con la base de datos
	 * @param dni dni del usuario que se desea eliminar
	 */
	public static void eliminarUsuario(String dni) {
		String sentSQL = "DELETE FROM usuario WHERE dni="+dni+"';";
		try {
			Statement stmt = con.createStatement();
			stmt.executeUpdate(sentSQL);
			guardarLog(sentSQL);
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * TreeMap que har� que los usuarios que se registren se guarden en el mismo.
	 * @param con parametro que crea la conexion con la base de datos
	 * @return devuelve el treemap de usuarios
	 */
	public static TreeMap<String, Usuario> obtenerMapaUsuarios(){
		TreeMap<String, Usuario> tmUsuario = new TreeMap<>();
		
		String sentSQL = " SELECT * FROM usuario;";
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sentSQL);
			guardarLog(sentSQL);
			while(rs.next()) {
				String nombre = rs.getString("nombre");
				String dni = rs.getString("dni");
				String contrasenia = rs.getString("contrasenia");
				//Usuario u = new Usuario(nombre, dni, contrasenia);
				//tmUsuario.put(dni, u);
			}
			rs.close();
			stmt.close();
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return tmUsuario;
	}
	
	
	/*Guardar logs en fichero*/
	
	public static void guardarLog (String mensaje) {
		
		Logger logger = Logger.getLogger("BD");
		FileHandler fh;
		
		try {
			fh = new FileHandler("info.log");
			logger.addHandler(fh);
			fh.setFormatter(new SimpleFormatter());
			logger.info(mensaje);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}
}
