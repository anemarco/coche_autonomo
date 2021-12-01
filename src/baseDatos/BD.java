package baseDatos;

import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.TreeMap;

public class BD {
	/**
	 * Método que crea la conexión con la base de datos
	 * @param nombreBD (he puesto nombreBD como variable para que el código se pueda utilizar con cualquier archivo de sqliteman)
	 * @return devuelve la conexión
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
	 * Método que hará que se cierre la base de datos
	 * @param con
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
	 * 
	 */
	public static void crearTablas(Connection con) {
		String sent1 = "CREATE TABLE IF NOT EXISTS usuario(nom String, dni String, con String)";
		Statement st= null;
		
		try {
			st = con.createStatement();
			st.executeUpdate(sent1);
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
	 * Insertará el usuario que se solicite
	 * @param con
	 * @param nombre
	 * @param dni
	 * @param contrasenia
	 */
	public static void insertarUsuario(Connection con, String nombre, String dni, String contrasenia) {
		String sentSQL = "INSERT INTO usuario VALUES('"+nombre+"',"+dni+"',"+contrasenia+")";
		Statement stmt;
		try {
			stmt = con.createStatement();
			stmt.executeUpdate(sentSQL);
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	/**
	 * Eliminará el usuario que se necesite
	 * @param con
	 * @param dni
	 */
	public static void eliminarUsuario(Connection con, String dni) {
		String sentSQL = "DELETE FROM usuario WHERE dni="+dni+"'";
		try {
			Statement stmt = con.createStatement();
			stmt.executeUpdate(sentSQL);
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * TreeMap que hará que los usuarios que se registren se guarden en el mismo.
	 * @param con
	 * @return
	 */
	public static TreeMap<String, Usuario> obtenerMapaUsuarios(Connection con){
		TreeMap<String, Usuario> tmUsuario = new TreeMap<>();
		
		String sentSQL = " SELECT * FROM usuario";
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sentSQL);
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

}
