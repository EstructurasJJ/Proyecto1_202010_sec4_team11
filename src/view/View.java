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
			System.out.println("2. Consultar el primer comparendo en una localidad.");
			System.out.println("3. Consultar comparendos en una fecha.");
			System.out.println("4. Comparar por código, el número de comparendos para dos fechas.");
			System.out.println("5. Requerimiento 1 de Bobby");
			System.out.println("6. Requerimiento 2 de Bobby");
			System.out.println("7. Requerimiento 3 de Bobby");
			System.out.println("8. Mostrar el número de comparendos para cada código, que están en una localidad dada y entre dos fechas.");
			System.out.println("9. Consultar los N códigos más repetidos entre dos fechas");
			System.out.println("10. Requerimiento tres de los dos.");
			System.out.println("11. Exit");
			System.out.println("Dar el numero de opcion a resolver, luego oprimir tecla Return: (e.g., 1):");
		}

		public void printMessage(String mensaje) {

			System.out.println(mensaje);
		}	
}
