package model.data_structures;

import model.logic.Comparendo;

//TODO Ambos

public class Node <T>
{
	Comparendo viejoCompa;
	Node<T> siguiente;
	
	public Node (T elemento)
	{
		viejoCompa = (Comparendo) elemento;
		siguiente = null;
	}
	
	public Comparendo darInfoDelComparendo()
	{
		return viejoCompa;
	}
	
	public void definirComparendo(Comparendo nuevo)
	{
		viejoCompa = nuevo;
	}
	
	public Node<T> darSiguiente()
	{
		return siguiente;
	}
	public void cambiarSiguiente(Node<T> primerElemento)
	{
		siguiente = primerElemento;
	}
	
}
