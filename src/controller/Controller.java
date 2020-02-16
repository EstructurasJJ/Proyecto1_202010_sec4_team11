package controller;

import java.util.Scanner;



import model.data_structures.ListaEnlazadaQueue;
import model.logic.Comparendo;
import model.logic.Modelo;
import view.View;

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
				
				view.printMessage("La información del máx Object Id es: ");
				view.printMessage("Object Id: " + modelo.UltimoComparendo().darObjectid());
				view.printMessage("Fecha Hora: " + modelo.UltimoComparendo().darFecha_Hora());
				view.printMessage("Infracción: " + modelo.UltimoComparendo().darInfraccion());
				view.printMessage("Clase Vehiculo: " + modelo.UltimoComparendo().darClase_Vehi());
				view.printMessage("Tipo Servicio: " + modelo.UltimoComparendo().darTipo_Servicio());
				view.printMessage("Localidad: " + modelo.UltimoComparendo().darLocalidad() + "\n----------");
				
				break;

			case 2:
				view.printMessage("--------- \n Hasta pronto !! \n---------"); 
				lector.close();
				fin = true;
				break;	

			default: 
				view.printMessage("--------- \n Opción Invalida !! \n---------");
				break;
			}
		}

	}	
}
