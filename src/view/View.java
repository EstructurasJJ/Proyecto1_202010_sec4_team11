package view;

import model.logic.Modelo;

public class View 
{
	/**
	 * Metodo constructor
	 */
	public View()
	{

	}

	public void printMenu()
	{
		System.out.println("1. Cargar archivo");
		System.out.println("2. Buscar el primer comparendo dada una localidad");
		System.out.println("3. Consultar los comparendos en una fecha dada");
		System.out.println("4. Comparar los comparendos para dos fechas dadas");
		System.out.println("5. Buscar el primer comparendo dada una infracción");
		System.out.println("6. Consultar los comparendos de una infracción dada");
		System.out.println("7. Requerimiento parte 3 Bob");
		System.out.println("8. Dar el número de comparendos entre dos fechas, dada una localidad");
		System.out.println("9. Requerimiento 2 ambos");
		System.out.println("10. Requerimiento 3 ambos");
		System.out.println("11. Salir");
		System.out.println("Dar el numero de opcion a resolver, luego oprimir tecla Return: (e.g., 1):");
	}

	public void printMessage(String mensaje) {

		System.out.println(mensaje);
	}	
}
