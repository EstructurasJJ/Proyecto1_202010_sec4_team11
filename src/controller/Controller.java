package controller;

import java.util.ArrayList;
import java.util.Scanner;



import model.data_structures.ListaEnlazadaQueue;
import model.logic.Comparendo;
import model.logic.Modelo;
import view.View;
import model.data_structures.Node;

public class Controller {

	private Modelo modelo;
	private View view;
	public final static String RUTAGEOJASON = "./data/comparendos_dei_2018.geojson";
	public final static String JUEGUEMOS = "./data/comparendos_dei_2018_small.geojson";

	public Controller ()
	{
		view = new View();
		modelo = new Modelo();
	}

	public void run() 
	{
		Scanner lector = new Scanner(System.in);
		boolean fin = false;
		String dato = "";
		String respuesta = "";

		while( !fin )
		{
			view.printMenu();

			int option = lector.nextInt();
			switch(option)
			{
			case 1:
				modelo.leerGeoJson(RUTAGEOJASON);

				view.printMessage("Archivo GeoJSon Cargado");
				view.printMessage("Numero actual de comparendos " + modelo.darTamanio() + "\n----------");

				view.printMessage("El Minimax es: (" + modelo.darMinLatitud() + " , " + modelo.darMinLongitud() + ")(" + modelo.darMaxLatitud() + " ," + modelo.darMaxLongitud() + ")" + "\n----------");

				view.printMessage("La informaci�n del m�x Object Id es: ");
				view.printMessage("Object Id: " + modelo.UltimoComparendo().darObjectid());
				view.printMessage("Fecha Hora: " + modelo.UltimoComparendo().darFecha_Hora());
				view.printMessage("Infracci�n: " + modelo.UltimoComparendo().darInfraccion());
				view.printMessage("Clase Vehiculo: " + modelo.UltimoComparendo().darClase_Vehi());
				view.printMessage("Tipo Servicio: " + modelo.UltimoComparendo().darTipo_Servicio());
				view.printMessage("Localidad: " + modelo.UltimoComparendo().darLocalidad() + "\n----------");
				
				break;

			case 2:
				
				String a="";	
				System.out.println("Por favor ingrese la localidad del comparendo a buscar. Si son palabras separadas, por favor escriba en una l�nea diferente cada una");
				
				a =lector.next();
				
				if (a.equals("SANTA") || a.equals("BARRIOS") || a.equals("CIUDAD")|| a.equals("SAN") || a.equals("BOGOTA")||a.equals("RAFAEL")||a.equals("PUENTE")||a.equals("ANTONIO"))
				{
					a=a+ " "+lector.next();
				}
					

				Comparendo r=modelo.darPrimeroLocalidad(a);

				if (r!=null)
				{
					System.out.println("El primer comparendo en "+dato+" fue el de los siguientes datos:");
					System.out.println(r.darObjectid());
					System.out.println(r.darFecha_Hora());
					System.out.println(r.darInfraccion());
					System.out.println(r.darClase_Vehi());
					System.out.println(r.darTipo_Servicio());
					System.out.println(r.darLocalidad());
				}
				else
				{
					System.out.println("No hay ning�n comparendo en la localidad dada");
				}

				break;

			case 3:

				int contador=0;
				System.out.println("Por favor ingrese la fecha");

				dato = lector.next();


				ArrayList<ListaEnlazadaQueue<Comparendo>> cola=modelo.CompisFecha(dato);

				if (cola.size()>0)
				{
					int z=cola.size()-1;
					while (z>=0)
					{

						Node<Comparendo> primero=cola.get(z).darPrimerElemento();

						while(primero!=null)
						{
							System.out.println("----------------------------------------------");
							System.out.println(primero.darInfoDelComparendo().darObjectid());
							System.out.println(primero.darInfoDelComparendo().darFecha_Hora());
							System.out.println(primero.darInfoDelComparendo().darInfraccion());
							System.out.println(primero.darInfoDelComparendo().darClase_Vehi());
							System.out.println(primero.darInfoDelComparendo().darTipo_Servicio());
							System.out.println(primero.darInfoDelComparendo().darLocalidad());
							System.out.println("------------------------------------------------");

							primero=primero.darSiguiente();
							contador++;
						}
						z--;
					}

					System.out.println("Se encontraron un total de "+contador+" comparendos");
				}
				else
				{
					System.out.println("No se encontraron comparendos para esa fecha");
				}

				break;

			case 4: 

				System.out.println("Por favor ingrese la primera fecha");
				dato = lector.next();

				System.out.println("Por favor ingrese la segunda fecha");
				String dato2=lector.next();

				ArrayList<String[]> datos=modelo.infraccionEnFechaDada(dato, dato2);

				System.out.println("Infracci�n     |   "+dato+"   |   "+dato2);

				int k=0;

				while (k<datos.size())
				{
					System.out.println(datos.get(k)[0]+"            |"+datos.get(k)[1]+"              |"+datos.get(k)[2]);
					k++;
				}

				break;

			case 8:

				System.out.println("Ingrese la localidad a buscar");
				dato = lector.next();

				System.out.println("Ingrese la fecha 1 del intervalo");
				String fecha1 = lector.next();
				System.out.println("Ingrese la fecha 2 del intervalo");
				String fecha2=lector.next();

				ArrayList<String[]> resp=modelo.InfraccionRepetidos(fecha1, fecha2, dato);

				System.out.println("Infracci�n  |   Comparendos");

				int b=0;

				while (b<resp.size())
				{
					System.out.println(resp.get(b)[0]+"         | "+resp.get(b)[1]);
					b++;
				}

				break;

				////////////////////////////
				/////////TODO BOBBY/////////
				////////////////////////////

			case 5:

				System.out.println("Ingrese la infracci�n para buscar la primera coincidencia.");
				dato = lector.next();

				Comparendo compi = modelo.darPrimeroInfraccion(dato);

				if (compi!=null)
				{
					System.out.println("El primer comparendo con "+dato+" fue :");
					System.out.println(compi.darObjectid());
					System.out.println(compi.darFecha_Hora());
					System.out.println(compi.darInfraccion());
					System.out.println(compi.darClase_Vehi());
					System.out.println(compi.darTipo_Servicio());
					System.out.println(compi.darLocalidad());
				}
				else
				{
					System.out.println("No hay ning�n comparendo en la infracci�n dada.");
				}

				break;

			case 6:

				System.out.println("Ingrese la infracci�n a buscar");
				dato = lector.next();

				Comparable[] lista = modelo.CompisInfraccion(dato);

				int i = 0;

				while (i < lista.length)
				{
					System.out.println("----------------------------------------------");
					System.out.println(((Comparendo) lista[i]).darObjectid());
					System.out.println(((Comparendo) lista[i]).darFecha_Hora());
					System.out.println(((Comparendo) lista[i]).darInfraccion());
					System.out.println(((Comparendo) lista[i]).darClase_Vehi());
					System.out.println(((Comparendo) lista[i]).darTipo_Servicio());
					System.out.println(((Comparendo) lista[i]).darLocalidad());
					System.out.println("-----------------------------------------------");

					i++;
				}

				break;

			case 7:
				
				ArrayList<String[]> datInfracciones = modelo.InfraccionEnTipoServicio();

				System.out.println("Infracci�n            |Particular            |P�blico");
				
				int infra = 0;
				while (infra < datInfracciones.size())
				{
					System.out.println(datInfracciones.get(infra)[0] + "            |" + datInfracciones.get(infra)[1] + "              |" + datInfracciones.get(infra)[2]);
					infra++;
				}

				view.printMessage("\n----------");

				break;
				
			case 10:
				
				datInfracciones = modelo.Histograma();
				
				int loca = 0;
				while (loca < datInfracciones.size())
				{
					System.out.println(datInfracciones.get(loca)[0] + "  ---|" + datInfracciones.get(loca)[1]);
					loca++;
				}

				view.printMessage("\n----------");


				break;

			case 11:
				view.printMessage("--------- \n Hasta pronto !! \n---------"); 
				lector.close();
				fin = true;
				break;	

			default: 
				view.printMessage("--------- \n Opci�n Invalida !! \n---------");
				break;
			}
		}

	}	
}
