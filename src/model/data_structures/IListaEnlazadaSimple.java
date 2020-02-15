package model.data_structures;

import java.util.Iterator;

public interface IListaEnlazadaSimple <T> extends Iterable <T>
{
	int darTamaño();
	
	public Node<T> devolverPrimerElemento();
	
	public void agregarAlComienzo (T elemento);
	public void agregarAlFinal(T elemento);
	public void agregarEnPosDada(int posi, T elemento);
	
	public void elimarCabeza();
	public void eliminarPorPosicion(int posi);
	public void eliminarGeneralPorObjeto (Object elemento);
	
	public Node<T> accederPorPos(int posi);
	public boolean estaVacia();
	
	public Iterator<T> iterator();
}
