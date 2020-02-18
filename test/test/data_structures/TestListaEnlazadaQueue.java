package test.data_structures;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import controller.Controller;
import model.data_structures.ListaEnlazadaQueue;
import model.logic.Comparendo;
import model.logic.Modelo;

public class TestListaEnlazadaQueue 
{
	private ListaEnlazadaQueue cola;

	private Modelo modelo;

	public void setUp()
	{
		modelo= new Modelo();
		cola= new ListaEnlazadaQueue<Integer>();
	}


	public void setUp1()
	{
		modelo = new Modelo();
		modelo.leerGeoJson(Controller.JUEGUEMOS);

		cola = new ListaEnlazadaQueue<Integer>();

		for (int i = 0; i<100; i++)
		{
			Comparendo nuevo = new Comparendo();
			nuevo.asignarObjectid(i);

			cola.enqueue(nuevo);
		}
	}

	@Test
	public void testEnqueue()
	{
		setUp();
		Comparendo nuevo= new Comparendo(), seg=new Comparendo(), ter=new Comparendo();
		nuevo.asignarObjectid(1);
		cola.enqueue(nuevo);
		assertEquals("No es el esperado", 1, cola.darPrimerElemento().darInfoDelComparendo().darObjectid());
		assertEquals("No es el esperado", 1, cola.darUltimoElemento().darInfoDelComparendo().darObjectid());

		seg.asignarObjectid(2);
		cola.enqueue(seg);

		assertEquals("No es el esperado", 2, cola.darUltimoElemento().darInfoDelComparendo().darObjectid());
		assertEquals("No es el esperado", 1, cola.darPrimerElemento().darInfoDelComparendo().darObjectid());

		ter.asignarObjectid(3);
		cola.enqueue(ter);

		assertEquals("No es el esperado", 3, cola.darUltimoElemento().darInfoDelComparendo().darObjectid());
		assertEquals("No es el esperado", 1, cola.darPrimerElemento().darInfoDelComparendo().darObjectid());
	}

	@Test
	public void testDequeue()
	{
		setUp();
		Comparendo nuevo= new Comparendo(), seg=new Comparendo(), ter=new Comparendo();
		nuevo.asignarObjectid(1);
		cola.enqueue(nuevo);
		seg.asignarObjectid(2);
		cola.enqueue(seg);
		ter.asignarObjectid(3);
		cola.enqueue(ter);

		assertEquals(1, cola.darPrimerElemento().darInfoDelComparendo().darObjectid());

		cola.dequeue();

		assertEquals(2, cola.darPrimerElemento().darInfoDelComparendo().darObjectid());

		cola.dequeue();

		assertEquals(3, cola.darPrimerElemento().darInfoDelComparendo().darObjectid());
	}


	@Test
	public void testCalcularTamanio()
	{
		setUp1();
		assertEquals("No es el esperado", 100, cola.darTamanio());
	}

	@Test
	public void emptyList()
	{
		setUp();
		assertTrue("No es el esperado", cola.emptyList());
		setUp1();
		assertFalse("No es el esperado", cola.emptyList());

	}

	@Test
	public void testCargaDeDatos()
	{
		setUp1();
		assertTrue(modelo.darDatos()!=null);
		assertEquals(20, modelo.darTamanio());
	}
	
	@Test
	public void testProbarNodoCorrecto()
	{
		setUp1();
		assertTrue(modelo.darDatos().darPrimerElemento()!=null);
		assertTrue(cola.darPrimerElemento()!=null);
	}
	

}
