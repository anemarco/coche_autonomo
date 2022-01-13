package test;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.Test;

import simulador.Animal;
import simulador.OtroCoche;
import simulador.Semaforo;
import simulador.Coche;
import simulador.Obstaculo;

class TestSimulador {
	
	Coche c1= new Coche(0, 0);
	Obstaculo obs= new OtroCoche(10,15); 

	@Before
	public void setUp() throws Exception {
		
	}

	@After
	public void tearDown() throws Exception {
		
	}
	//Comprobar el metodo acelerar de la clase Coche
	@Test
	void testAcelerar() {
		c1.acelerar(4);
		assertEquals(4, c1.getVelocidad());
	}

	//Comprobar el metodo choca de la clase Coche
	@Test
	void testChoca() {
		OtroCoche obst1= new OtroCoche(0,0);
		Animal obst2= new Animal();
		assertTrue( c1.choca(obst1));
		assertFalse(c1.choca(obst2));
	}
	//Comprobar el metodo acelerar de la clase Coche y Obstaculo
	@Test
	void testMover() {
		//para coche
		c1.mover(5, 3);
		assertEquals(5, c1.getX());
		assertEquals(3, c1.getY());
		//para obst�culo
		
		obs.mover(4, 1); 
		assertEquals(14, obs.getX()); 
		assertEquals(16, obs.getY());
		 
	}
	

	
}
