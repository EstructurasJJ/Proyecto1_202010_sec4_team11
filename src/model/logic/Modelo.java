package model.logic;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSyntaxException;

import model.data_structures.ListaEnlazadaQueue;
import model.data_structures.Node;



public class Modelo 
{
	private String parteDelComparendo; 
	private Comparendo compaAgregar;
	private boolean coordenadas = false;

	private double minLatitud = 1000000000;
	private double minLongitud = 1000000000;
	private double maxLatitud = -1000000000;
	private double maxLongitud = -1000000000;

	private ListaEnlazadaQueue<Comparendo> booty = new ListaEnlazadaQueue<Comparendo>();

	public Modelo()
	{
		parteDelComparendo = "";
		booty = new ListaEnlazadaQueue<Comparendo>();
	}


	public ListaEnlazadaQueue<Comparendo> darDatos()
	{
		return booty;
	}

	public int darTamanio()
	{
		return booty.darTamanio();
	}

	public double darMinLatitud()
	{
		return minLatitud;
	}
	public double darMinLongitud()
	{
		return minLongitud;
	}
	public double darMaxLatitud()
	{
		return maxLatitud;
	}
	public double darMaxLongitud()
	{
		return maxLongitud;
	}

	public Comparendo PrimerComparendo()
	{
		return booty.darPrimerElemento().darInfoDelComparendo();
	}

	public Comparendo UltimoComparendo()
	{
		return booty.darUltimoElemento().darInfoDelComparendo();
	}

	public void leerGeoJson(String pRuta) 
	{	
		JsonParser parser = new JsonParser();
		FileReader fr = null;

		try 
		{
			fr = new FileReader(pRuta);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}

		JsonElement datos = parser.parse(fr);
		dumpJSONElement(datos);

	}

	private void dumpJSONElement(JsonElement elemento) 
	{


		if (elemento.isJsonObject()) 
		{

			JsonObject obj = elemento.getAsJsonObject();

			java.util.Set<java.util.Map.Entry<String,JsonElement>> entradas = obj.entrySet();
			java.util.Iterator<java.util.Map.Entry<String,JsonElement>> iter = entradas.iterator();

			while (iter.hasNext()) 
			{
				java.util.Map.Entry<String,JsonElement> entrada = iter.next();
				componentesDelComparendo(entrada.getKey());	            

				dumpJSONElement(entrada.getValue());
			}

		}
		else if (elemento.isJsonArray()) 
		{			
			JsonArray array = elemento.getAsJsonArray();
			java.util.Iterator<JsonElement> iter = array.iterator();

			while (iter.hasNext()) 
			{
				JsonElement entrada = iter.next();
				dumpJSONElement(entrada);
			}

		} 
		else if (elemento.isJsonPrimitive()) 
		{
			JsonPrimitive valor = elemento.getAsJsonPrimitive();

			if(compaAgregar == null)
			{
				compaAgregar = new Comparendo();
			}

			if(parteDelComparendo.equals("OBJECTID"))
			{
				compaAgregar.asignarObjectid(valor.getAsInt());
				//System.out.println(valor);
			}
			else if (parteDelComparendo.equals("FECHA_HORA"))
			{
				compaAgregar.asignarFecha_Hora(valor.getAsString());
				//System.out.println(valor);
			}
			else if (parteDelComparendo.equals("MEDIO_DETE"))
			{
				compaAgregar.asignarMedio_Dete(valor.getAsString());
				//System.out.println(valor);
			}
			else if (parteDelComparendo.equals("CLASE_VEHI"))
			{
				compaAgregar.asignarClase_Vehi(valor.getAsString());
				//System.out.println(valor);
			}
			else if (parteDelComparendo.equals("TIPO_SERVI"))
			{
				compaAgregar.asignarTipo_Servicio(valor.getAsString());
				//System.out.println(valor);
			}
			else if (parteDelComparendo.equals("INFRACCION"))
			{
				compaAgregar.asignarInfraccion(valor.getAsString());
				//System.out.println(valor);
			}
			else if (parteDelComparendo.equals("DES_INFRAC"))
			{
				compaAgregar.asignarDes_Infrac(valor.getAsString());
				//System.out.println(valor);

			}
			else if (parteDelComparendo.equals("LOCALIDAD"))
			{				
				compaAgregar.asignarLocalidad(valor.getAsString());
				//System.out.println(valor);	
				parteDelComparendo = "";
			}
			else if (parteDelComparendo.equals("coordinates"))
			{
				agregarCoordenada(valor.getAsDouble());				
			}

		} 
		else if (elemento.isJsonNull()) 
		{
			System.out.println("Es NULL");
		} 
		else 
		{
			System.out.println("Es otra cosa");
		}

	}

	public void componentesDelComparendo(String palabra)
	{
		if (palabra.equals("OBJECTID"))
		{
			parteDelComparendo = "OBJECTID";
		}
		else if (palabra.equals("FECHA_HORA"))
		{
			parteDelComparendo = "FECHA_HORA";
		}
		else if (palabra.equals("MEDIO_DETE"))
		{
			parteDelComparendo = "MEDIO_DETE";
		}
		else if (palabra.equals("CLASE_VEHI"))
		{
			parteDelComparendo = "CLASE_VEHI";
		}
		else if (palabra.equals("TIPO_SERVI"))
		{
			parteDelComparendo = "TIPO_SERVI";
		}
		else if (palabra.equals("INFRACCION"))
		{
			parteDelComparendo = "INFRACCION";
		}
		else if (palabra.equals("DES_INFRAC"))
		{
			parteDelComparendo = "DES_INFRAC";
		}
		else if (palabra.equals("LOCALIDAD"))
		{
			parteDelComparendo = "LOCALIDAD";
		}
		else if (palabra.equals("coordinates"))
		{
			parteDelComparendo = "coordinates";
		}
	}

	public void agregarCoordenada(double pCor)
	{
		if(coordenadas == false)
		{
			compaAgregar.asignarLongitud(pCor);
			//System.out.println("Longitud: " + pCor);

			if (pCor < minLongitud)
			{
				minLongitud = pCor;
			}
			else if (pCor > maxLongitud)
			{
				maxLongitud = pCor;
			}

			coordenadas = true;
		}

		else
		{
			compaAgregar.asignarLatitud(pCor);
			//System.out.println("Latitud: " + pCor);

			if (pCor < minLatitud)
			{
				minLatitud = pCor;
			}
			else if (pCor > maxLatitud)
			{
				maxLatitud = pCor;
			}

			//AGREGAR//

			coordenadas = false;
			parteDelComparendo = "";

			booty.enqueue(compaAgregar);
			compaAgregar = null;

			//System.out.println("///AGREGADO///");

		}

	}

	///////////////////////////////////////////////////////////////////
	////////////////////////Comienza JuanJo////////////////////////////
	///////////////////////////////////////////////////////////////////

	public Comparendo darPrimeroLocalidad (String loca)
	{

		Node<Comparendo> actual=booty.darPrimerElemento();

		while (actual!=null)
		{
			if (actual.darInfoDelComparendo().darLocalidad().equals(loca))
			{
				return actual.darInfoDelComparendo();
			}

			actual=actual.darSiguiente();
		}

		return null;
	}



	public ArrayList<ListaEnlazadaQueue<Comparendo>> CompisFecha (String fecha)
	{
		ArrayList<ListaEnlazadaQueue<Comparendo>> respuesta=new ArrayList<ListaEnlazadaQueue<Comparendo>>();
		Node<Comparendo> actual=booty.darPrimerElemento();
		ArrayList<String> codsComparendos=new ArrayList<String>();
		int pos;

		while (actual!=null)
		{
			if(actual.darInfoDelComparendo().darFecha_Hora().equals(fecha))
			{

				if (darPos(actual.darInfoDelComparendo().darInfraccion(), codsComparendos)>=0)
				{
					pos=darPos(actual.darInfoDelComparendo().darInfraccion(), codsComparendos);

					respuesta.get(pos).enqueue(actual.darInfoDelComparendo());
				}
				else
				{
					ListaEnlazadaQueue<Comparendo> parcial= new ListaEnlazadaQueue<Comparendo>();
					parcial.enqueue(actual.darInfoDelComparendo());
					respuesta.add(parcial);					
					codsComparendos.add(actual.darInfoDelComparendo().darInfraccion());
				}

			}

			actual=actual.darSiguiente();
		}


		Comparable[] copia = generarCopiaCods(codsComparendos);
		ordenamientoPorQuickSort(copia);


		int k=0,p;
		ListaEnlazadaQueue<Comparendo> viejo, nuevo;

		while (k<respuesta.size())
		{
			String codi=respuesta.get(k).darPrimerElemento().darInfoDelComparendo().darInfraccion();
			p=buscarBinarioPorCod(codi, copia);

			viejo=respuesta.get(k);
			nuevo=respuesta.get(p);
			respuesta.set(k, nuevo);
			respuesta.set(p, viejo);
			k++;
		}

		return respuesta;
	}


	public ArrayList<String[]> infraccionEnFechaDada(String fecha1, String fecha2)
	{
		int pos=0;
		String[] arrAux=new String[3];
		ArrayList<String[]> respuesta=new ArrayList<String[]>();
		ArrayList<String> codsComparendos=new ArrayList<String>();

		Node<Comparendo> actual=booty.darPrimerElemento();	

		while (actual!=null)
		{
			if (actual.darInfoDelComparendo().darFecha_Hora().equals(fecha1))
			{
				if (darPos(actual.darInfoDelComparendo().darInfraccion(), codsComparendos)>=0)
				{
					pos=darPos(actual.darInfoDelComparendo().darInfraccion(), codsComparendos);
					arrAux=new String[3];
					arrAux[0]=actual.darInfoDelComparendo().darInfraccion();
					arrAux[1]=(Integer.parseInt(respuesta.get(pos)[1])+1)+"";
					arrAux[2]=respuesta.get(pos)[2];
					respuesta.set(pos, arrAux);
				}
				else
				{
					arrAux=new String[3];
					arrAux[0]=actual.darInfoDelComparendo().darInfraccion();
					arrAux[1]="1";
					arrAux[2]="0";
					respuesta.add(arrAux);
					codsComparendos.add(actual.darInfoDelComparendo().darInfraccion());
				}
			}
			else if (actual.darInfoDelComparendo().darFecha_Hora().equals(fecha2))
			{
				if (darPos(actual.darInfoDelComparendo().darInfraccion(), codsComparendos)>=0)
				{
					pos=darPos(actual.darInfoDelComparendo().darInfraccion(), codsComparendos);
					arrAux=new String[3];
					arrAux[0]=actual.darInfoDelComparendo().darInfraccion();
					arrAux[1]=respuesta.get(pos)[1];
					arrAux[2]=(Integer.parseInt(respuesta.get(pos)[2])+1)+"";
					respuesta.set(pos, arrAux);
				}
				else
				{
					arrAux=new String[3];
					arrAux[0]=actual.darInfoDelComparendo().darInfraccion();
					arrAux[1]="0";
					arrAux[2]="1";
					respuesta.add(arrAux);
					codsComparendos.add(actual.darInfoDelComparendo().darInfraccion());
				}
			}

			actual=actual.darSiguiente();		
		}

		//Hasta este punto se llenaron las listas con los c�digos, ahora se ordenan
		Comparable[] aOrdenar = generarCopiaCods(codsComparendos);
		ordenamientoPorQuickSort(aOrdenar);
		//Ya se tienen ordenados los c�digos de los comparendos, ahora se debe reflejar el orden en la lista que contiene los n�meros 

		int a=0, p;
		String[] viejo, nuevo;
		while (a<respuesta.size())
		{
			String codi=respuesta.get(a)[0];
			p=buscarBinarioPorCod(codi, aOrdenar);

			viejo=respuesta.get(a);
			nuevo=respuesta.get(p);
			respuesta.set(a, nuevo);
			respuesta.set(p, viejo);
			a=a+1;
		}


		return respuesta;
	}

	private int darPos(String codComparendo, ArrayList<String> comparendos)
	{
		int i=0;
		while (i<comparendos.size())
		{
			if (comparendos.get(i).equals(codComparendo))
			{
				return i;
			}
			i++;
		}

		return -1;
	}

	public Comparable[] generarCopiaCods(ArrayList<String> codsComparendos)
	{
		Comparable[] copia = new Comparable[codsComparendos.size()];
		int i=0;

		while(i<codsComparendos.size())
		{
			String auxiliar = codsComparendos.get(i);
			copia[i] = auxiliar;		
			i++;	
		}

		return copia;
	}


	public static void ordenamientoPorQuickSort(Comparable [] copia)
	{

		Random rnd = ThreadLocalRandom.current();

		for (int i = copia.length - 1; i > 0; i--)
		{
			int index = rnd.nextInt(i + 1);
			Comparable x =copia[index];
			copia[index] = copia[i];
			copia[i] = x;
		}

		quickSort(copia, 0, copia.length-1);

	}


	private static void  quickSort(Comparable[] copia, int lo, int hi)
	{
		if (hi<=lo) return;

		int j = partition (copia,lo,hi);
		quickSort(copia,lo,j-1);
		quickSort(copia,j+1,hi);
	}

	private static int partition(Comparable[] copia, int lo, int hi)
	{
		int i=lo, j=hi+1;
		Comparable v=copia[lo];

		while (true)
		{
			while (copia[++i].compareTo(v)<0) if (i==hi) break;
			while (v.compareTo(copia[--j])<0) if (j==lo) break;
			if (i>=j) break;
			exchange(copia,i,j);
		}

		exchange(copia,lo,j);
		return j;
	}

	public static void exchange(Comparable[] copia, int pos1, int pos2)
	{
		Comparable tempo = copia[pos1];
		copia[pos1] = copia[pos2];
		copia[pos2] = tempo;
	}


	public int buscarBinarioPorCod( String cod, Comparable[] codsComparendos )
	{
		int posicion=-1;
		int inicio=0;
		int fin=codsComparendos.length-1;

		while(inicio<=fin && posicion==-1)
		{
			int medio=(inicio+fin)/2;
			Comparable deLaMitad=codsComparendos[medio];

			if(deLaMitad.compareTo(cod)==0)
			{
				posicion=medio;
			}
			else if(deLaMitad.compareTo(cod)>0)
			{
				fin=medio-1;
			}
			else
			{
				inicio=medio+1;
			}
		}
		return posicion;

	}

	public ArrayList<String[]> InfraccionRepetidos(String fechaMin, String fechaMax, String localidad)
	{

		int pos=0;
		String[] arrAux=new String[2];
		ArrayList<String[]> respuesta=new ArrayList<String[]>();
		ArrayList<String> codsComparendos=new ArrayList<String>();

		Node<Comparendo> actual=booty.darPrimerElemento();	

		while (actual!=null)
		{
			if (actual.darInfoDelComparendo().darFecha_Hora().compareTo(fechaMin)>=0 && actual.darInfoDelComparendo().darFecha_Hora().compareTo(fechaMax)<=0 && actual.darInfoDelComparendo().darLocalidad().equals(localidad))
			{
				if (darPos(actual.darInfoDelComparendo().darInfraccion(), codsComparendos)>=0)
				{
					pos=darPos(actual.darInfoDelComparendo().darInfraccion(), codsComparendos);
					arrAux=new String[2];
					arrAux[0]=actual.darInfoDelComparendo().darInfraccion();
					arrAux[1]=(Integer.parseInt(respuesta.get(pos)[1])+1)+"";
					respuesta.set(pos, arrAux);
				}
				else
				{
					arrAux=new String[2];
					arrAux[0]=actual.darInfoDelComparendo().darInfraccion();
					arrAux[1]="1";
					respuesta.add(arrAux);
					codsComparendos.add(actual.darInfoDelComparendo().darInfraccion());
				}
			}

			actual=actual.darSiguiente();
		}


		//Hasta este punto se llenaron las listas con los c�digos, ahora se ordenan
		Comparable[] aOrdenar = generarCopiaCods(codsComparendos);
		ordenamientoPorQuickSort(aOrdenar);
		//Ya se tienen ordenados los c�digos de los comparendos, ahora se debe reflejar el orden en la lista que contiene los n�meros 

		int a=0, p;
		String[] viejo, nuevo;
		while (a<respuesta.size())
		{
			String codi=respuesta.get(a)[0];
			p=buscarBinarioPorCod(codi, aOrdenar);

			viejo=respuesta.get(a);
			nuevo=respuesta.get(p);
			respuesta.set(a, nuevo);
			respuesta.set(p, viejo);
			a=a+1;
		}

		return respuesta;
	}

	public ArrayList<Comparendo> InfraccionTopN(int N, String fechaMin, String fechaMax)
	{
		return null;
	}



	///////////////////////////////////////////////////////////////////
	////////////////////////Termina JuanJo/////////////////////////////
	///////////////////////////////////////////////////////////////////



	///////////////////////////////////////////////////////////////////
	////////////////////////TODO Comienza Bobby////////////////////////
	///////////////////////////////////////////////////////////////////

	//TODO Requerimiento 1B
	public Comparendo darPrimeroInfraccion (String infra)
	{
		Node<Comparendo> actual = booty.darPrimerElemento();
		Comparendo primeroInfra = null;
		boolean encontro = false;

		while(actual != null && !encontro)
		{
			if (actual.darInfoDelComparendo().darInfraccion().equals(infra))
			{
				primeroInfra = actual.darInfoDelComparendo();
				encontro = true;
			}

			actual = actual.darSiguiente();
		}

		return primeroInfra;
	}


	//TODO Requerimiento 2B
	public Comparable[] CompisInfraccion (String infra)
	{
		ListaEnlazadaQueue<Comparendo> ListaConInfra = new ListaEnlazadaQueue<>();
		Node<Comparendo> actual = booty.darPrimerElemento();

		Comparable[] aOrdenar = null;
		Comparable[] ordenado = null;

		while (actual!=null)
		{
			if(actual.darInfoDelComparendo().darInfraccion().equals(infra))
			{
				ListaConInfra.enqueue(actual.darInfoDelComparendo());
			}

			actual=actual.darSiguiente();
		}

		aOrdenar = copiarComparendosAUsar(ListaConInfra);
		ordenado = MergeSort(aOrdenar);

		return ordenado;
	}

	//TODO Requerimiento 3B
	public ArrayList<String[]> InfraccionEnTipoServicio()
	{
		return null;
	}
	public ArrayList<Comparendo> Histograma()
	{
		return null;
	}

	/////////////////////////////////////////////////////////////////////TODO UTIL BOBBY 

	//Merge Sort FECHAS BOBBY

	public Comparable[] MergeSort(Comparable[] copia)
	{
		Comparable[] auxiliar = new Comparable[copia.length];

		sort_subpartes(copia, 0, copia.length-1, auxiliar);

		return copia;
	}

	private static void sort_subpartes(Comparable[] copia, int low, int height, Comparable[] auxiliar) 
	{
		if(height <= low)
			return;

		int mid = low + (height-low)/2;

		sort_subpartes(copia, low, mid, auxiliar);

		sort_subpartes(copia, mid+1, height, auxiliar);

		merge(copia, low, mid, height, auxiliar);
	}

	private static void merge(Comparable[] copia, int low, int mid, int height, Comparable[] auxiliar) 
	{
		int i = low;
		int j = mid+1;
		for (int k = low; k <= height; k++)
		{
			auxiliar[k] = copia[k];
		}

		for (int k = low; k <= height; k++)
		{
			if(i > mid)
			{
				copia[k] = auxiliar[j++];
			}
			else if(j > height)
			{
				copia[k] = auxiliar[i++];
			}
			else if(less(auxiliar[j], auxiliar[i]))
			{
				copia[k] = auxiliar[j++];
			}
			else
			{
				copia[k] = auxiliar[i++];
			}
		}

	}

	public static boolean less(Comparable compi1, Comparable compi2)
	{
		return compi1.compareTo(compi2) < 0;
	}

	/////////////////////////////////////////////////////////////////////TODO UTIL BOBBY 

	//COPIAR DATOS A UN ARREGLO BOBBY

	public Comparable[] copiarComparendosAUsar(ListaEnlazadaQueue<Comparendo> lista)
	{
		Comparable[] comparendosCopia = new Comparable[lista.darTamanio()];
		int contador = 0;

		Node<Comparendo> actual = lista.darPrimerElemento();

		while(actual != null)
		{
			Comparendo compi = actual.darInfoDelComparendo();
			comparendosCopia[contador] = compi;

			contador++;
			actual = actual.darSiguiente();

		}

		return comparendosCopia;
	}




}
