package test;

import org.junit.After;
import org.junit.Before;

import baseDatos.BD;

class BDTest {


	@Before
	public void setUp() throws Exception {
		BD.initBD("simulacion.bd");
	}
	
	@After
	public void tearDown() throws Exception {
		BD.closeBD();
	}
	
	public void testInsertarUsuario() {
		
	}
}