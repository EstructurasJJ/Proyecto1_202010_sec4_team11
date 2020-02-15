package model.data_structures;

import java.util.Iterator;

public class ListaEnlazadaSimple <T> implements IListaEnlazadaSimple<T> 
{
	
	//Atributos
	
	private Node<T> primerElemento;
	private int numeroElementos;
	
	//Contructor
	
	public ListaEnlazadaSimple()
	{
		primerElemento = null;
		numeroElementos = 0;
	}
	
	//Tamaño y primer nodo.
	
	public int darTamaño() 
	{
		return numeroElementos;
	}
	
	public Node<T> devolverPrimerElemento()
	{
		return primerElemento;
	}
	
	//Agregar
	
	public void agregarAlComienzo (T elemento)
	{
		Node<T> nuevo = new Node<T>(elemento);
		
		if (primerElemento == null)
		{
			primerElemento = nuevo;
		}
		else
		{
			nuevo.cambiarSiguiente(primerElemento);
			primerElemento = nuevo;
		}
		
		numeroElementos++;
	}
	
	public void agregarAlFinal(T elemento)
	{
		Node<T> nuevo = new Node<T>(elemento);
		
		if (primerElemento == null)
		{
			primerElemento = nuevo;
		}
		else
		{
			Node<T> ulti = primerElemento;
			
			while (ulti.darSiguiente() != null)
			{
				ulti = ulti.darSiguiente();
			}
			
			ulti.cambiarSiguiente(nuevo);
		}
		
		numeroElementos++;
		
	}
	
	public void agregarEnPosDada(int posi, T elemento)
	{
		Node<T> nuevo = new Node<T>(elemento);
		
		if (posi >= 0 && posi < numeroElementos)
		{
			int elementoActual = 0;
			Node<T> actual = primerElemento;
			
			while(elementoActual+1 != posi)
			{
				actual = actual.darSiguiente();
				elementoActual++;
			}
			
			nuevo.cambiarSiguiente(actual.darSiguiente());
			actual.cambiarSiguiente(nuevo);
			numeroElementos++;
			
		}
	}
	
	//Eliminar
	
	public void elimarCabeza()
	{
		if (primerElemento.darSiguiente() != null)
		{	
			Node<T> temporal = primerElemento;
			primerElemento =  primerElemento.darSiguiente();
			temporal.cambiarSiguiente(null);
		}
		else
		{
			primerElemento = null;
		}
		
		numeroElementos--;
	}
	
	public void eliminarPorPosicion(int posi)
	{
		if (posi >= 0 && posi < numeroElementos)
		{
			int elementoActual = 0;
			Node<T> actual = primerElemento;
			
			while(elementoActual+1 != posi)
			{
				actual = actual.darSiguiente();
				elementoActual++;
			}
			
			Node<T> temporal = actual.darSiguiente();
			actual.cambiarSiguiente(actual.darSiguiente().darSiguiente());
			temporal.cambiarSiguiente(null);
		
			numeroElementos--;
		}
	}
	
	public void eliminarGeneralPorObjeto (Object elemento)
	{
		if (primerElemento.equals(elemento))
		{
			if (primerElemento.darSiguiente() == null)
			{
				primerElemento = null;
			}
			else
			{
				primerElemento = primerElemento.darSiguiente();
			}
			
			numeroElementos--;
		}
		else
		{
			Node<T> actu = primerElemento;
			
			while (actu != null && !actu.darSiguiente().equals(elemento))
			{
				actu = actu.darSiguiente();
			}
			
			if (actu != null)
			{
				if(actu.darSiguiente().darSiguiente() == null)
				{
					actu.cambiarSiguiente(null);
				}
				else
				{
					actu.cambiarSiguiente(actu.darSiguiente().darSiguiente());
				}
			}
			
			numeroElementos--;

		}
	}
	
	//Acceder
	
	public Node<T> accederPorPos(int posi)
	{
		Node<T> actual = primerElemento;
		int conta = 0;
		
		while(conta != posi)
		{
			actual = actual.darSiguiente();
			conta++;
		}
		
		return actual;
		
	}
	
	//Vacio 

	public boolean estaVacia()
	{
		if (primerElemento == null)
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	//Iterador - No lo uso :) 
	
	public Iterator<T> iterator() 
	{
		return null;
	}
	
}
