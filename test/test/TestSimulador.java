package test;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.Test;

import simulador.Coche;
import simulador.Obstaculo;
import simulador.Sensor;

class TestSimulador {
	
	Coche c1= new Coche(0, 0, 0, 0, 0, 0);
	Sensor s1=new Sensor(0, 0, 0);
	@Before
	public void setUp() throws Exception {
		
	}

	@After
	public void tearDown() throws Exception {
		
	}

	@Test
	void testChoca(Obstaculo obst) {
		assertTrue( (c1.getX()-obst.getX()<c1.getH()+obst.getH()) && (Math.abs(c1.getY()-obst.getY())<Math.abs(c1.getA()+obst.getA())) );
	}
	
	//@Test
	//public Obstaculo testDetectaObs(Obstaculo obs) {
		//double dist = Math.sqrt(Math.pow(s1.getX()-obs.getX(), 2)+Math.pow(s1.getY()-obs.getY(), 2));
		
	//}
	
	

}
