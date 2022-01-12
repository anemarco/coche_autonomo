package baseDatos;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import simulador.Obstaculo;
import ventanas.VentanaInicio;
import ventanas.VentanaSimulador;

public class BD {
	
	/*
	 * CREATE TABLE usuario (
	 * 		dni CHAR(9) PRIMARY KEY,
	 * 		nombre VARCHAR(15),
	 * 		apellido VARCHAR(15),
	 * 		contrasenia VARCHAR(15)
	 * );
	 * 
	 * CREATE TABLE simulacion (
	 * 		fecha STRING PRIMARY KEY,
	 * 		duracion FLOAT,
	 * 		estado STRING,
	 * 		dni CHAR(9),
	 * 		FOREIGN KEY(dni) REFERENCES usuario(DNI) ON DELETE CASCADE
	 * );
	 * 
	 * CREATE TABLE obstaculo (
	 * 		hora STRING PRIMARY KEY,
	 * 		nombre VARCHAR(15),
	 * 		fecha STRING,
	 * 		FOREIGN KEY(fecha) REFERENCES simulacion(fecha) ON DELETE CASCADE
	 * );
	 * 
	 */
	
	static Connection con;
	
	/**
	 * Metodo que crea la conexion con la base de datos
	 * @param nombreBD nombre del archivo de sqliteman de la base de datos
	 * @return devuelve la conexion
	 */

	
	public static void  initBD(String nombreBD) {
		try {
			Class.forName("org.sqlite.JDBC");
			con = DriverManager.getConnection("jdbc:sqlite:"+nombreBD);
			generarLog("Conexión abierta con " + nombreBD);
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
				generarLog("Conexion cerrada");
			}catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	/*NO BORRAR POR AHORA */
	
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
			 generarLog(sent);
			 
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
	 
	 /** TreeMap que guarda el dni y la contraseña de los usuarios. 
	  * @return Devuelve un treeMap con el dni como clave.
	  */
	 
	public static TreeMap<String, Usuario> getMapaUsuarios(){
		TreeMap<String, Usuario> tmUsuario = new TreeMap<>();
			
		String sent = " SELECT * FROM usuario";
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sent);
			while(rs.next()) {
				String dni = rs.getString("dni");
				String nom = rs.getString("nombre");
				String ape = rs.getString("apellido");
				String c = rs.getString("contrasenia");
				Usuario u = new Usuario(dni, nom, ape, c);
				System.out.println(u);
				tmUsuario.put(dni, u);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
			return tmUsuario;
		}
	
	 
	 /**
	  * Método que lee todas las simulaciones ejecutadas por un usuario de la base de datos
	  * @param con
	  * @return Lista de las simulaciones
	  */
	 
	 public static List<Simulacion> getSimulacionesDeUnaPersona(String dni) {
		 try (Statement st = con.createStatement()) {
			 List<Simulacion> lSimulaciones = new ArrayList<>();
			 String sent = "SELECT * FROM simulacion WHERE dni ='"+dni+"';";
			 ResultSet rs = st.executeQuery(sent);
			 generarLog(sent);
			 
			 while (rs.next()) {
				 String fecha = rs.getString("fecha");
				 long duracion = rs.getLong("duracion");
				 String estado = rs.getString("estado");
				 lSimulaciones.add(new Simulacion(fecha, duracion, estado, dni));
			 }
			 return lSimulaciones;
		 } catch (SQLException e) {
			 e.printStackTrace();
			 return null;
		 }
	 }
	 
	 /**
	  * Método que lee todas los obstáculos creados en una simulación de la base de datos
	  * @param fecha
	  * @return
	  */
	 
	 public static List<ObstaculoBD> getObstaculosDeUnaSimulacion(String fecha) {
		 try (Statement st = con.createStatement()) {
			 List<ObstaculoBD> lObstaculos = new ArrayList<>();
			 String sent = "SELECT * FROM obstaculo WHERE fecha ='"+fecha+"';";
			 ResultSet rs = st.executeQuery(sent);
			 generarLog(sent);
			 
			 while (rs.next()) {
				 String hora = rs.getString("hora");
				 String nombre = rs.getString("nombre");
				 lObstaculos.add(new ObstaculoBD(hora, nombre, fecha));
			 }
			 return lObstaculos;
		 } catch (SQLException e) {
			 e.printStackTrace();
			 return null;
		 }
	 }
	 
	 /**
	  * Insertar un obstaculo en la base de datos
	  * @param hora
	  * @param nombre
	  * @param fecha
	  */
	 
	 public static void insertarObstaculo(String hora, String nombre, String fecha) {
		 String sent = "INSERT INTO obstaculo VALUES('"+hora+"','"+nombre+"','"+VentanaSimulador.fecha+"');";
		 
		 try {
				Statement stmt = con.createStatement();
				stmt.executeUpdate(sent);
				generarLog(sent);
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	 }
	 
	 /**
	  * Insertar un nueva lod datos de una simulación 
	  * @param fecha y hora Fecha de ejecución
	  * @param duracion Duración de la simulación
	  * @param lObstaculos Lista de óbstáculos ejecutados en la simulación
	  */
	 
	 public static void insertarSimulacion(String fecha, double duracion, String estado, ArrayList<Obstaculo> lObstaculos) {
		 String sent = "INSERT INTO simulacion VALUES('"+fecha+"',"+duracion+",'"+estado+"','"+ VentanaInicio.usuarioActivo.getDni() +"');";
		 
		 try {
				Statement stmt = con.createStatement();
				stmt.executeUpdate(sent);
				generarLog(sent);
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
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
		String sent = "INSERT INTO usuario VALUES('"+dni+"','"+nombre+"','"+apellido+"',"+contrasenia+");";
		
		try {
			Statement stmt = con.createStatement();
			stmt.executeUpdate(sent);
			generarLog(sent);
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
		String sent = "DELETE FROM usuario WHERE dni="+dni+"';";
		try {
			Statement stmt = con.createStatement();
			stmt.executeUpdate(sent);
			generarLog(sent);
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
		
		String sent = " SELECT * FROM usuario";
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sent);
			while(rs.next()) {
				String dni = rs.getString("dni");
				String nom = rs.getString("nombre");
				String ape = rs.getString("apellido");
				String c = rs.getString("contrasenia");
				Usuario u = new Usuario(dni, nom, ape, c);
				tmUsuario.put(dni, u);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return tmUsuario;
		
		/*public static TreeMap<String, Persona> obtenerMapaPersonas(Connection con){
		TreeMap<String, Persona> tmPersonas = new TreeMap<>();
		
		String sentSQL = "SELECT * FROM alumno";
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sentSQL);
			while(rs.next()) { //Mientras no hayamos llegado al final del conjunto de resultados
				String dni = rs.getString("dni");
				String nom = rs.getString("nombre");
				int edad = rs.getInt("edad");
				Persona p = new Persona(dni,nom,edad);
				tmPersonas.put(dni, p);
			}
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return tmPersonas;
	}*/
		
	}
	
	
	/**
	 * Método que genera loggers y los guarda en un fichero
	 * @param info Información a guardar
	 */
	
	public static void generarLog (String info) {
		Logger logger = Logger.getLogger("BD");
		FileHandler fh;
		
		try {
			fh = new FileHandler("infoBD.log", true);
			logger.addHandler(fh);
			fh.setFormatter(new SimpleFormatter());
			logger.info(info);
			fh.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Método que imprime todos los datos referentes a un usuario en un fichero
	 * @param file fichero de texto
	 * @param dni Dni del usuario
	 */
	
	public static void guardarDatos(File file, String dni) {
		List<Simulacion> lSimulaciones = getSimulacionesDeUnaPersona(dni);
		List<ObstaculoBD> lObstaculos;
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(file));
			for (Simulacion s : lSimulaciones) {
				bw.write("\nFecha de ejecución: " + s.getFecha());
				bw.write("\nDuración: " + s.getDuracion() + " s");
				bw.write("\nEstado: " + s.getEstado());
				
				lObstaculos = getObstaculosDeUnaSimulacion(s.getFecha());
				for (ObstaculoBD o : lObstaculos) {
					bw.write("\n\t[Tipo: " + o.getNombre() + "\tTiempo de ejecución: " + o.getHora() +"]");
				}
			}
			
			bw.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}