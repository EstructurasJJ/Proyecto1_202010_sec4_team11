package test.data_structures;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import controller.Controller;
import model.data_structures.ListaEnlazadaSimple;
import model.data_structures.Node;
import model.logic.Comparendo;
import model.logic.Modelo;

public class TestListaEnlazadaSimple 
{
	private Modelo conexion;
	private ListaEnlazadaSimple<Comparendo> lista; 
	
	@Before
	public void setUp()
	{
		conexion = new Modelo();
		conexion.leerGeoJson(Controller.JUEGUEMOS);
		
		lista = new ListaEnlazadaSimple<>();
		
		for (int i = 0; i<100; i++)
		{
			Comparendo nuevo = new Comparendo();
			nuevo.asignarObjectid(i);
			
			lista.agregarAlComienzo(nuevo);
		}
		
	}
	
	@Test
	public void testListaSimpleCarga()
	{
		assertTrue(conexion.darDatos()!=null);
		assertEquals(20, conexion.darTamanio());
	}
	
	@Test
	public void testProbarNodoCargado()
	{
		//assertTrue(conexion.darDatos().devolverPrimerElemento()!=null);
	}
	
	@Test
	public <T> void testAgregarAlComienzo()
	{
		assertEquals(100, lista.darTamaño());
		
		Comparendo nuevo = new Comparendo();
		lista.agregarAlComienzo(nuevo);
		
		assertEquals(101, lista.darTamaño());
	}
	
	@Test
	public <T> void testAgregarAlFinal()
	{
		assertEquals(100, lista.darTamaño());
		
		Comparendo nuevo = new Comparendo();
		lista.agregarAlFinal(nuevo);
		
		assertEquals(101, lista.darTamaño());
	}
	
	@Test
	public <T> void testAgregarEnPosDada()
	{
		assertEquals(100, lista.darTamaño());
		
		Comparendo nuevo = new Comparendo();
		lista.agregarEnPosDada(45, nuevo);
		
		assertEquals(101, lista.darTamaño());
	}
	
	@Test
	public <T> void testElimarCabeza()
	{
		assertEquals(100, lista.darTamaño());

		lista.elimarCabeza();
		
		assertEquals(99, lista.darTamaño());
	}
	
	@Test
	public <T> void testEliminarPorPosicion()
	{
		assertEquals(100, lista.darTamaño());

		lista.eliminarPorPosicion(56);
		
		assertEquals(99, lista.darTamaño());
	}
	
	@Test
	public <T> void testEliminarGeneralPorObjeto()
	{
		assertEquals(100, lista.darTamaño());
		
		lista.eliminarGeneralPorObjeto(lista.accederPorPos(87));
		
		assertEquals(99, lista.darTamaño());
	}
	
	@Test
	public <T> void testAccederPorPos()
	{		
		Node<T> buscado = (Node<T>) lista.accederPorPos(87);
		assertTrue(buscado != null);
	}
	
	@Test
	public void testEstaVacia()
	{
		boolean sera = lista.estaVacia();
		assertTrue(sera == false);
	}
	
}
