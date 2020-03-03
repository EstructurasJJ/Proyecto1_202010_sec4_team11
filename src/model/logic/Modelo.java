package model.logic;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Comparator;
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

	static Comparator<Comparendo> infrac = new OrdenarComparendoIfracciones();
	static Comparator<Comparendo> locali = new OrdenarComparendoLocalidad();

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



	///////////////////////////REQUERIMIENTO 1A/////////////////////////



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


	/////////////////////////////REQUERIMIENTO 2A/////////////////////////////////////


	public Comparable[] CompisFecha (String fecha)
	{
		ListaEnlazadaQueue<Comparendo> respuesta=new ListaEnlazadaQueue<Comparendo>();
		Comparendo[] desordenada=null;
		Comparable[] ordenada=null;
		Node<Comparendo> actual=booty.darPrimerElemento();

		while (actual!=null)
		{
			if(actual.darInfoDelComparendo().darFecha_Hora().equals(fecha))
			{

				respuesta.enqueue(actual.darInfoDelComparendo());

			}

			actual=actual.darSiguiente();
		}

		desordenada = copiarComparendosAComparendo(respuesta);
		ordenada = shell_sort_Infraccion(desordenada);

		return ordenada;

	}


	///////////////////////////////////REQUERIMIENTO 3A///////////////////////////

	public ArrayList<String[]> infraccionEnFechaDada(String fecha1, String fecha2)
	{

		Comparendo[] aOrdenar = null;	
		Comparable[] ordenado = null;

		aOrdenar = copiarComparendos();
		ordenado = shell_sort_Infraccion(aOrdenar);

		String[] arreglo = new String[3];
		ArrayList<String[]> respuesta=new ArrayList<String[]>();


		int conta = 0;
		String codAnterior = ((Comparendo)ordenado[0]).darInfraccion();
		int primeraFecha=0,segundaFecha=0;

		while (conta < ordenado.length)
		{
			if (((Comparendo)ordenado[conta]).darInfraccion().equals(codAnterior))
			{
				if (((Comparendo)ordenado[conta]).darFecha_Hora().equals(fecha1))
				{
					primeraFecha++;
				}
				else if (((Comparendo)ordenado[conta]).darFecha_Hora().equals(fecha2))
				{
					segundaFecha++;
				}
			}
			else
			{
				if (primeraFecha != 0 | segundaFecha != 0)
				{
					arreglo = new String[3];

					arreglo[0]= codAnterior;
					arreglo[1]= "" + primeraFecha;
					arreglo[2]= "" + segundaFecha;

					respuesta.add(arreglo);
				}

				primeraFecha=0;
				segundaFecha=0;
				codAnterior=((Comparendo)ordenado[conta]).darInfraccion();

				if (((Comparendo)ordenado[conta]).darFecha_Hora().equals(fecha1))
				{
					primeraFecha++;
				}
				else if (((Comparendo)ordenado[conta]).darFecha_Hora().equals(fecha2))
				{
					segundaFecha++;
				}

			}
			conta++;
		}


		return respuesta;
	}


	//TODO NOS SIRVE A AMBOS
	public static void exchange(Comparable[] copia, int pos1, int pos2)
	{
		Comparable tempo = copia[pos1];
		copia[pos1] = copia[pos2];
		copia[pos2] = tempo;
	}

	////////////////////////////RUQERUMIENTO 1C/////////////////////////////////////////////////

	public ArrayList<String[]> InfraccionRepetidos(String fechaMin, String fechaMax, String localidad)
	{
		ListaEnlazadaQueue<Comparendo> respuesta=new ListaEnlazadaQueue<Comparendo>();
		Comparendo[] desordenada=null;
		Comparable[] ordenada=null;
		Node<Comparendo> actual=booty.darPrimerElemento();
		ArrayList<String []> estaEs= new ArrayList<String[]>();
		int numComparendos;

		while (actual!=null)
		{
			if(actual.darInfoDelComparendo().darFecha_Hora().compareTo(fechaMin)>=0 && actual.darInfoDelComparendo().darFecha_Hora().compareTo(fechaMax)<=0 && actual.darInfoDelComparendo().darLocalidad().equals(localidad))
			{

				respuesta.enqueue(actual.darInfoDelComparendo());

			}

			actual=actual.darSiguiente();
		}

		desordenada = copiarComparendosAComparendo(respuesta);
		ordenada = shell_sort_Infraccion(desordenada);

		String codActual;
		String codAnterior="";
		if (ordenada.length>0)
		{
			codAnterior=((Comparendo)ordenada[0]).darInfraccion();
		}
			
		int totalDatos=0;
		numComparendos=0;
		String[] parcial=new String[2];

		while(totalDatos<ordenada.length)
		{

			codActual=((Comparendo)ordenada[totalDatos]).darInfraccion();

			if(codActual.equals(codAnterior))
			{
				numComparendos++;
			}
			else
			{
				parcial = new String[2];
				parcial[0]=codAnterior;
				parcial[1]=numComparendos+"";
				estaEs.add(parcial);
				numComparendos=1;
				codAnterior=codActual;	
			}

			totalDatos++;
		}

		parcial=new String[2];
		parcial[0]=codAnterior;
		parcial[1]=numComparendos+"";
		estaEs.add(parcial);


		return estaEs;
	}



	//////////////////////////////////REUQERIMIENTO 2C////////////////////////////////////

	
	public ArrayList<String[]> InfraccionTopN(int N, String fechaMin, String fechaMax)
	{
		ListaEnlazadaQueue<Comparendo> respuesta=new ListaEnlazadaQueue<Comparendo>();
		Comparendo[] desordenada=null;
		Comparable[] ordenada=null;
		Node<Comparendo> actual=booty.darPrimerElemento();
		ArrayList<String []> estaEs= new ArrayList<String[]>();
		ArrayList<String[]> estaEsLaVerdadera=new ArrayList<String[]>();
		int numComparendos;

		while (actual!=null)
		{
			if(actual.darInfoDelComparendo().darFecha_Hora().compareTo(fechaMin)>=0 && actual.darInfoDelComparendo().darFecha_Hora().compareTo(fechaMax)<=0)
			{

				respuesta.enqueue(actual.darInfoDelComparendo());

			}

			actual=actual.darSiguiente();
		}

		desordenada = copiarComparendosAComparendo(respuesta);
		ordenada = shell_sort_Infraccion(desordenada);

		String codActual;
		String codAnterior="";
		if (ordenada.length>0)
		{
			codAnterior=((Comparendo)ordenada[0]).darInfraccion();
		}
			
		int totalDatos=0;
		numComparendos=0;
		String[] parcial=new String[2];

		while(totalDatos<ordenada.length)
		{

			codActual=((Comparendo)ordenada[totalDatos]).darInfraccion();

			if(codActual.equals(codAnterior))
			{
				numComparendos++;
			}
			else
			{
				parcial = new String[2];
				parcial[0]=codAnterior;
				parcial[1]=numComparendos+"";
				estaEs.add(parcial);
				numComparendos=1;
				codAnterior=codActual;	
			}

			totalDatos++;
		}

		parcial=new String[2];
		parcial[0]=codAnterior;
		parcial[1]=numComparendos+"";
		estaEs.add(parcial);
		
		
		int r=0;
		int pos;
		int max;
		while(N>0)
		{
			if (estaEs.size()>0)
			{
				r=0;
				pos=0;
				max=0;
				while (r<estaEs.size())
				{
					if (Integer.parseInt(estaEs.get(r)[1])>max)
					{
						pos=r;
						max=Integer.parseInt(estaEs.get(r)[1]);
					}
					
					r++;
				}
				
				estaEsLaVerdadera.add(estaEs.get(pos));
				estaEs.remove(pos);
				
			}
			
			N--;
		}
		



		return estaEsLaVerdadera;
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

		Comparendo[] aOrdenar = null;
		Comparable[] ordenado = null;

		while (actual!=null)
		{
			if(actual.darInfoDelComparendo().darInfraccion().equals(infra))
			{
				ListaConInfra.enqueue(actual.darInfoDelComparendo());
			}

			actual=actual.darSiguiente();
		}

		aOrdenar = copiarComparendosAComparendo(ListaConInfra);
		ordenado = shell_sort_Fecha(aOrdenar);

		return ordenado;
	}

	//TODO Requerimiento 3B
	public ArrayList<String[]> InfraccionEnTipoServicio()
	{
		//Recibo el arreglo y lo ordeno

		Comparendo[] aOrdenar = null;	
		Comparable[] ordenado = null;

		aOrdenar = copiarComparendos();
		ordenado = shell_sort_Infraccion(aOrdenar);

		//Donde voy a guardar la info
		String[] arreglo = new String[3];
		ArrayList<String[]> respuesta=new ArrayList<String[]>();

		//Recorro todo el arreglo

		int conta = 0;

		while (conta < ordenado.length)
		{
			boolean nextCod = false;
			int pos = conta;

			String Codigo = "";

			int particular = 0;
			int publico = 0;

			//Mientras este en el mismo codigo

			while(!nextCod)
			{
				//Ultimo dato para que no se desborde.

				if(pos+1 >= ordenado.length)
				{
					nextCod = true;
					conta = ordenado.length + ordenado.length;
					String Cod1 = ((Comparendo) ordenado[pos]).darInfraccion();

					//Verifico si cumplen el último

					if (((Comparendo) ordenado[pos]).darTipo_Servicio().equals("Particular"))
					{
						particular++;
					}

					if (((Comparendo) ordenado[pos]).darTipo_Servicio().equals("PÃºblico"))
					{
						publico++;
					}

					Codigo = Cod1;
				}

				//Resto de datos
				else
				{
					//Accedo a los codigos

					String Cod1 = ((Comparendo) ordenado[pos]).darInfraccion();
					String Cod2 = ((Comparendo) ordenado[pos+1]).darInfraccion();

					//Verifico si cumplen

					if (((Comparendo) ordenado[pos]).darTipo_Servicio().equals("Particular"))
					{
						particular++;
					}

					if (((Comparendo) ordenado[pos]).darTipo_Servicio().equals("PÃºblico"))
					{
						publico++;
					}

					//Avanzo al siguiente codigo

					pos++;

					//Reviso si tengo que salir al siguiente

					if (!Cod1.equals(Cod2))
					{
						nextCod = true;
						conta = pos;
						Codigo = Cod1;
					}
				}

			}

			if (particular != 0 | publico != 0)
			{
				arreglo = new String[3];

				arreglo[0]= Codigo;
				arreglo[1]= "" + particular;
				arreglo[2]= "" + publico;

				respuesta.add(arreglo);


				//System.out.println(Codigo + "  |" + particular + "  |" + publico);
			}

		}


		return respuesta;

	}

	//TODO Requerimiento 3C
	public ArrayList<String[]> Histograma()
	{
		//Recibo el arreglo y lo ordeno

		Comparendo[] aOrdenar = null;	
		Comparable[] ordenado = null;

		aOrdenar = copiarComparendos();
		ordenado = shell_sort_Localidad(aOrdenar);

		//Donde voy a guardar la info
		String[] arreglo = new String[2];
		ArrayList<String[]> respuesta=new ArrayList<String[]>();

		//Recorro todo el arreglo

		int conta = 0;

		while (conta < ordenado.length)
		{
			boolean nextCod = false;
			int pos = conta;

			String Localidad = "";

			int numInfra = 0;

			//Mientras este en el mismo codigo

			while(!nextCod)
			{
				//Ultimo dato para que no se desborde.

				if(pos+1 >= ordenado.length)
				{
					nextCod = true;
					conta = ordenado.length + ordenado.length;
					String Loca1 = ((Comparendo) ordenado[pos]).darLocalidad();

					//Tiene comparendo
					numInfra++;
					Localidad = Loca1;
				}

				//Resto de datos
				else
				{
					//Accedo a los codigos

					String Loca1 = ((Comparendo) ordenado[pos]).darLocalidad();
					String Loca2 = ((Comparendo) ordenado[pos+1]).darLocalidad();

					//Tiene comparendo
					numInfra++;

					//Avanzo al siguiente codigo

					pos++;

					//Reviso si tengo que salir al siguiente

					if (!Loca1.equals(Loca2))
					{
						nextCod = true;
						conta = pos;
						Localidad = Loca1;
					}
				}

			}

			if (numInfra != 0)
			{

				numInfra = (numInfra/50)+1;
				String aste = "";

				for(int i = 0; i < numInfra; i++)
				{
					aste = aste + "*";
				}

				arreglo = new String[2];

				arreglo[0]= Localidad;
				arreglo[1]= "" + aste;

				respuesta.add(arreglo);

				//System.out.println(Localidad + "--| " + aste + "  ");
			}

		}

		return respuesta;

	}


	/////////////////////////////////////////////////////////////////////TODO UTIL BOBBY 

	//COPIAR DATOS A UN ARREGLO BOBBY

	public Comparendo[] copiarComparendosAComparendo(ListaEnlazadaQueue<Comparendo> listaConInfra)
	{
		Comparendo[] comparendosCopia = new Comparendo[listaConInfra.darTamanio()];
		int contador = 0;

		Node<Comparendo> actual = listaConInfra.darPrimerElemento();

		while(actual != null)
		{
			Comparendo compi = actual.darInfoDelComparendo();
			comparendosCopia[contador] = compi;

			contador++;
			actual = actual.darSiguiente();

		}

		return comparendosCopia;
	}

	public Comparendo[] copiarComparendos()
	{
		Comparendo[] comparendosCopia = new Comparendo[booty.darTamanio()];
		int contador = 0;

		Node<Comparendo> actual = booty.darPrimerElemento();

		while(actual != null)
		{
			Comparendo compi = actual.darInfoDelComparendo();
			comparendosCopia[contador] = compi;

			contador++;
			actual = actual.darSiguiente();

		}

		return comparendosCopia;
	}
	/////////////////////////////////////////////////////////////////////TODO UTIL BOBBY 

	//Ordenar por codigo de fecha, infracción y localidad. 

	public static boolean less(Comparable compi1, Comparable compi2)
	{
		return compi1.compareTo(compi2) < 0;
	}

	public static int lessInfraccion(Comparendo compi1, Comparendo compi2)
	{
		return infrac.compare(compi1, compi2);
	}

	public static int lessLocalidad(Comparendo compi1, Comparendo compi2)
	{
		return locali.compare(compi1, compi2);
	}

	public Comparable[] shell_sort_Fecha(Comparendo[] copia)
	{
		int N = copia.length;
		int h = 1;

		while(h < N/3)
		{
			h = 3*h +1;
		}

		while(h>=1)
		{
			for (int i = h; i < N; i++)
			{
				for(int j = i; j>=h && less(copia[j], copia[j-h]); j = j -h)
				{
					exchange(copia,j,j-h);
				}
			}

			h = h/3;
		}

		return copia;

	}

	public Comparable[] shell_sort_Infraccion(Comparendo[] copia)
	{
		int N = copia.length;
		int h = 1;

		while(h < N/3)
		{
			h = 3*h +1;
		}

		while(h>=1)
		{
			for (int i = h; i < N; i++)
			{
				for(int j = i; j>=h && lessInfraccion(copia[j], copia[j-h]) < 0; j = j -h)
				{
					exchange(copia,j,j-h);
				}
			}

			h = h/3;
		}

		return copia;

	}

	public Comparable[] shell_sort_Localidad(Comparendo[] copia)
	{
		int N = copia.length;
		int h = 1;

		while(h < N/3)
		{
			h = 3*h +1;
		}

		while(h>=1)
		{
			for (int i = h; i < N; i++)
			{
				for(int j = i; j>=h && lessLocalidad(copia[j], copia[j-h]) < 0; j = j -h)
				{
					exchange(copia,j,j-h);
				}
			}

			h = h/3;
		}

		return copia;

	}

}
