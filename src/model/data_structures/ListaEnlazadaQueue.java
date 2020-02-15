package model.data_structures;

import java.util.Iterator;

import model.logic.Comparendo;

public class ListaEnlazadaQueue <T> implements IListaEnlazadaQueue <T> 
{
	
	private int tamanio;
	
	private Node<T> primerNodo;
	
	private Node<T> lastNode;
	
	public ListaEnlazadaQueue()
	{
		primerNodo=null;
		lastNode=null;
		tamanio=0;
	}
		
	public int darTamanio()
	{
		return tamanio;
	}
	
	public boolean emptyList() 
	{
		return tamanio==0;
	}

	public Node<T> darPrimerElemento() 
	{
		return primerNodo;
	}

	public Node<T> darUltimoElemento() 
	{
		return lastNode;
	}
	
	
	public void enqueue(T parte) 
	{
		Node<T> nuevo = new Node<T>(parte);
		
		if (tamanio == 0)
		{
			primerNodo=nuevo;
			lastNode=nuevo;
		}
		else
		{
			Node <T> oldLast = lastNode;
			oldLast.cambiarSiguiente(nuevo);
			lastNode=nuevo;	
		}
		tamanio++;
	}

	public T dequeue() 
	{
		T eliminado;
		
		if (tamanio==0)
		{
			eliminado = null;
		}
		else if(tamanio==1)
		{
			eliminado = (T) primerNodo.darInfoDelComparendo();
			primerNodo=null;
			lastNode=null;
			tamanio--;
		}
		else
		{
			eliminado=(T)primerNodo.darInfoDelComparendo();
			Node<T> anteriorPrimero=primerNodo;
			Node<T> nuevoPrimero=primerNodo.darSiguiente();
			
			primerNodo=nuevoPrimero;
			anteriorPrimero.cambiarSiguiente(null);
			tamanio--;
		}
		
		
		return eliminado;
	}
	
	//No utilizado
	public Iterator iterator() 
	{
		// TODO Auto-generated method stub
		return null;
	}
}
