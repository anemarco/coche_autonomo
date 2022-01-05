package baseDatos;

import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.TreeMap;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class BD {
	
	/**
	 * M�todo que crea la conexion con la base de datos
	 * @param nombreBD nombre del archivo de sqliteman de la base de datos
	 * @return devuelve la conexion
	 */
	
	public static Connection initBD(String nombreBD) {
		Connection con = null;
		try {
			Class.forName("org.sqlite.JDBC");
			con = DriverManager.getConnection("jdbc:sqlite:"+nombreBD);
		}catch (ClassNotFoundException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return con;
	}
	/**
	 * Metodo que hara que se cierre la base de datos
	 * @param con parametro que establece la conexion con la base de datos
	 */
	public static void closeBD(Connection con) {
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
	 * Metodo que crea la tabla usuario en la base de datos
	 */
	public static void crearTablas(Connection con) {
		String sent1 = "CREATE TABLE IF NOT EXISTS usuario(nombre String, dni String, contrasenia String)";
		Statement st= null;
		
		try {
			st = con.createStatement();
			st.executeUpdate(sent1);
			guardarLog(sent1);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if(st!=null) {
				try {
					st.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * Insertara el usuario que se solicite
	 * @param con parametro que establece la conexion con la base de datos
	 * @param nombre nombre del usuario que se desea insertar
	 * @param dni dni del usuario que se desea insertar
	 * @param contrasenia contrasenia del usuario que se desea insertar
	 */
	public static void insertarUsuario(Connection con, String nombre, String dni, String contrasenia) {
		String sentSQL = "INSERT INTO usuario VALUES('"+nombre+"',"+dni+"',"+contrasenia+")";
		Statement stmt;
		try {
			stmt = con.createStatement();
			stmt.executeUpdate(sentSQL);
			guardarLog(sentSQL);
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	/**
	 * Eliminara el usuario que se necesite
	 * @param con parametro que establece la conexion con la base de datos
	 * @param dni dni del usuario que se desea eliminar
	 */
	public static void eliminarUsuario(Connection con, String dni) {
		String sentSQL = "DELETE FROM usuario WHERE dni="+dni+"'";
		try {
			Statement stmt = con.createStatement();
			stmt.executeUpdate(sentSQL);
			guardarLog(sentSQL);
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * TreeMap que har� que los usuarios que se registren se guarden en el mismo.
	 * @param con parametro que crea la conexion con la base de datos
	 * @return devuelve el treemap de usuarios
	 */
	public static TreeMap<String, Usuario> obtenerMapaUsuarios(Connection con){
		TreeMap<String, Usuario> tmUsuario = new TreeMap<>();
		
		String sentSQL = " SELECT * FROM usuario";
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sentSQL);
			guardarLog(sentSQL);
			while(rs.next()) {
				String nombre = rs.getString("nombre");
				String dni = rs.getString("dni");
				String contrasenia = rs.getString("contrasenia");
				Usuario u = new Usuario(nombre, dni, contrasenia);
				tmUsuario.put(dni, u);
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
