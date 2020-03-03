package test.logic;

import static org.junit.Assert.*;

import java.util.ArrayList;

import model.logic.Comparendo;
import model.logic.Modelo;

import org.junit.Before;
import org.junit.Test;

import controller.Controller;

public class TestModelo {
	
	private Modelo conexion;
	
	@Before
	public void setUp() 
	{
		conexion = new Modelo();
		conexion.leerGeoJson(Controller.JUEGUEMOS);
	}

	@Test
	public void testModelo() 
	{
		assertTrue(conexion.darDatos()!=null);
	}

	@Test
	public void testDarTamano() 
	{
		assertEquals(20, conexion.darTamanio());
	}
	
	
	////////////////////////////////////////////////////////////////////TODO JUANJO
	
	@Test
	public void testDarPrimeroEnLocalidad()
	{
		//Requerimiento 1A
		setUp();
		Comparendo respuesta=conexion.darPrimeroLocalidad("BARRIOS UNIDOS");
		assertEquals("No es la esperada",29042,respuesta.darObjectid());
		
		respuesta=conexion.darPrimeroLocalidad("ENGATIVA");
		assertEquals("No es la esperada",209146,respuesta.darObjectid());
		
		respuesta=conexion.darPrimeroLocalidad("MARTIRES");
		assertEquals("No es la esperada",56712,respuesta.darObjectid());
	}
	
	
	@Test
	public void darComparendosEnFecha()
	{
		setUp();
		Comparable[] respuesta=conexion.CompisFecha("2018/01/17");
		assertEquals("No corresponde al valor esperado", ((Comparendo)respuesta[0]).darObjectid(),519553);
		assertEquals("No corresponde al valor esperado", ((Comparendo)respuesta[1]).darObjectid(),509329);
		
	}
	
	@Test 
	public void compararPorFecha()
	{
		setUp();
		ArrayList<String[]> respuesta = conexion.infraccionEnFechaDada("2018/01/17", "2018/12/10");
		
		assertEquals("No corresponde", respuesta.get(0)[0],"B02");
		assertEquals("No corresponde", respuesta.get(0)[1],"1");
		assertEquals("No corresponde", respuesta.get(0)[2],"0");
		
		assertEquals("No corresponde", respuesta.get(1)[0],"C02");
		assertEquals("No corresponde", respuesta.get(1)[1],"0");
		assertEquals("No corresponde", respuesta.get(1)[2],"1");
		
		assertEquals("No corresponde", respuesta.get(2)[0],"C35");
		assertEquals("No corresponde", respuesta.get(2)[1],"1");
		assertEquals("No corresponde", respuesta.get(2)[2],"0");
	}
	
	
	//Test para requerimientos en equipo///////////////////////////////////////
	@Test
	public void testLocalidadEntreFechas()
	{
		setUp();
		ArrayList<String[]> respuesta = conexion.InfraccionRepetidos("2018/01/01", "2018/12/31", "BOSA");
		
		assertEquals("No corresponde", respuesta.get(0)[0],"B02");
		assertEquals("No corresponde", respuesta.get(0)[1],"2");
		
		assertEquals("No corresponde", respuesta.get(1)[0],"C02");
		assertEquals("No corresponde", respuesta.get(1)[1],"1");
		
	}
	
	@Test
	public void testDarNMax()
	{
		ArrayList<String[]> respuesta = conexion.InfraccionTopN(10, "2018/01/01", "2018/12/31");
		
		assertEquals("No corresponde", respuesta.get(0)[0],"C02");
		assertEquals("No corresponde", respuesta.get(0)[1],"9");
		
		assertEquals("No corresponde", respuesta.get(2)[0],"C14");
		assertEquals("No corresponde", respuesta.get(2)[1],"2");
		
		assertEquals("No corresponde", respuesta.get(5)[0],"C32");
		assertEquals("No corresponde", respuesta.get(5)[1],"1");
		
		assertEquals("No corresponde", respuesta.get(9)[0],"H03");
		assertEquals("No corresponde", respuesta.get(9)[1],"1");
		
	}
	
	
	
	/////////////////////////////////////////////////////////////////////TODO BOBBY 
	
	//TODO Requerimiento 1B
	@Test
	public void testdarPrimeroInfraccion()
	{
		Comparendo compi = conexion.darPrimeroInfraccion("C02");
		
		assertEquals(29042, compi.darObjectid());
		assertEquals("2018/12/10", compi.darFecha_Hora());
		assertEquals("DEAP", compi.darMedio_Dete());
		assertEquals("AUTOMÓVIL", compi.darClase_Vehi());
		assertEquals("Particular", compi.darTipo_Servicio());
		assertEquals("BARRIOS UNIDOS", compi.darLocalidad());
		
	}
	
	//TODO Requerimiento 2B
	@Test
	public void testCompisInfraccion()
	{
		Comparable[] infracc = conexion.CompisInfraccion("C02");
		
		int i = 0;
		
		while (i < infracc.length)
		{
			assertEquals("C02", ((Comparendo) infracc[i]).darInfraccion());
			i++;
		}
		
		assertEquals(9, infracc.length);
	}
	
	//TODO Requerimiento 3B
	@Test
	public void testInfraccionEnTipoServicio()
	{
		ArrayList<String[]> bob = conexion.InfraccionEnTipoServicio();
		
		assertEquals("A08", bob.get(0)[0]);
		assertEquals("B02", bob.get(1)[0]);
		assertEquals("C02", bob.get(2)[0]);
		assertEquals("C14", bob.get(3)[0]);
		assertEquals("C31", bob.get(4)[0]);
		assertEquals("C32", bob.get(5)[0]);
		assertEquals("C35", bob.get(6)[0]);
		assertEquals("C38", bob.get(7)[0]);
		assertEquals("D02", bob.get(8)[0]);
		assertEquals("H03", bob.get(9)[0]);
		
		assertEquals("1", bob.get(0)[1]);
		assertEquals("2", bob.get(1)[1]);
		assertEquals("8", bob.get(2)[1]);
		assertEquals("2", bob.get(3)[1]);
		assertEquals("1", bob.get(4)[1]);
		assertEquals("1", bob.get(5)[1]);
		assertEquals("1", bob.get(6)[1]);
		assertEquals("1", bob.get(7)[1]);
		assertEquals("1", bob.get(8)[1]);
		assertEquals("1", bob.get(9)[1]);
		
		assertEquals("0", bob.get(0)[2]);
		assertEquals("0", bob.get(1)[2]);
		assertEquals("1", bob.get(2)[2]);
		assertEquals("0", bob.get(3)[2]);
		assertEquals("0", bob.get(4)[2]);
		assertEquals("0", bob.get(5)[2]);
		assertEquals("0", bob.get(6)[2]);
		assertEquals("0", bob.get(7)[2]);
		assertEquals("0", bob.get(8)[2]);
		assertEquals("0", bob.get(9)[2]);
		
	}

	//TODO Requerimiento 3C
	@Test
	public void testHistograma()
	{
		ArrayList<String[]> bob = conexion.Histograma();
		
		int i = 0;
		while (i < bob.size())
		{
			assertEquals("*", bob.get(i)[1]);
			i++;
		}
		
		assertEquals("BARRIOS UNIDOS", bob.get(0)[0]);
		assertEquals("BOSA", bob.get(1)[0]);
		assertEquals("CHAPINERO", bob.get(2)[0]);
		assertEquals("ENGATIVA", bob.get(3)[0]);
		assertEquals("FONTIBON", bob.get(4)[0]);
		assertEquals("MARTIRES", bob.get(5)[0]);
		assertEquals("PUENTE ARANDA", bob.get(6)[0]);
		assertEquals("TEUSAQUILLO", bob.get(7)[0]);
		assertEquals("TUNJUELITO", bob.get(8)[0]);
		assertEquals("USAQUEN", bob.get(9)[0]);
		
	}
}
