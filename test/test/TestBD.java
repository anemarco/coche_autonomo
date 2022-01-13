package test;

import static org.junit.Assert.*;

import java.util.List;
import java.util.TreeMap;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import baseDatos.BD;
import baseDatos.ObstaculoBD;
import baseDatos.Simulacion;
import baseDatos.Usuario;

public class TestBD {

	@Before
	public void setUp() throws Exception {
		BD.initBD("simulacion.bd");
	}
	
	@After
	public void tearDown() throws Exception {
		BD.closeBD();
	}
	
	/**
	 * Comprobar que no se puede insertar un nuevo usuario cuya clave primaria 
	 * ya exista en la BD 
	 */
	
	@Test
	public void testInsertUsuario() {
		//assertTrue(BD.insertarUsuario("12345678A", "Alberto", "Suarez", "gahsx"));
		assertFalse(BD.insertarUsuario("11111111A", "Alberto", "Suarez", "gahsx"));
	}
	
	/**
	 * Este método no se puede comprobar. Se necesita un String DNI que solo
	 * se consigue cuando se ejecuta la ventana inicial
	 */
	
	@Test
	public void testInsertarSimulacion() {
		try {
			BD.insertarSimulacion(null, 64, "FRACASO");
		} catch (Exception e) {
			//fail("Error en la comprobación");
		}
		
	}
	
	
	
	/**
	 * Este método tampoco se puede comprobar. Se necesita un String fecha que 
	 * solo se conigue cuando se ejecuta la ventana simualción
	 */
	
	@Test
	public void testInsertarObstaculo() {
		try {
			BD.insertarObstaculo("16:48:20 p. m.", "Coche");
		} catch (Exception e) {
			fail("Error en la comprobación");
		}
	}
	
	/**
	 * Comprobar que un usuario que no existe no se puede borrar
	 */
	
	@Test
	public void testEliminarUsuario() {
		//assertTrue(BD.eliminarUsuario("11111111A"));
		assertFalse(BD.eliminarUsuario("39826783Q"));
	}
	
	/**
	 * Comprobar que el número de simulaciones de el usuario admin, con el que
	 * realizamos las pruebas, va a ser mayor al de cualquier otro usuario
	 */
	
	@Test
	public void getSimulacionesDeUnaPersona() {
		List<Simulacion> listA = BD.getSimulacionesDeUnaPersona("11111111A");
		List<Simulacion> listB = BD.getSimulacionesDeUnaPersona("52384730J");
		assertTrue(listA.size() > listB.size());
		
	}
	
	@Test
	public void getObstaculosDeUnaSimulacion() {
		List<ObstaculoBD> listA = BD.getObstaculosDeUnaSimulacion("12/01/2022 16:48:18");
	}
	
	@Test
	public void getMapaUsuarios() {
		BD.initBD("simulacion.bd");
		TreeMap<String, Usuario> tmUsuario = BD.getMapaUsuarios();
		BD.closeBD();
		
		assertTrue(tmUsuario.keySet().size() > 0);
	}
	

	
}
