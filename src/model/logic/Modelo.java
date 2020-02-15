package model.logic;

import java.io.FileNotFoundException;
import java.io.FileReader;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSyntaxException;

import model.data_structures.ListaEnlazadaQueue;
import model.data_structures.ListaEnlazadaSimple;
import model.data_structures.Node;

//TODO Ambos

public class Modelo 
{
	private String parteDelComparendo; 
	private Comparendo compaAgregar;
	
	//TODO Bobby
	private ListaEnlazadaQueue<Comparendo> booty = new ListaEnlazadaQueue<Comparendo>();

	public Modelo()
	{
		parteDelComparendo = "";
		
		//TODO Bobby
		booty = new ListaEnlazadaQueue<Comparendo>();
	}
	
	
	public ListaEnlazadaQueue<Comparendo> darDatos()
	{
		return booty;
	}
	
	//TODO Bobby
	
	public int darTamanio()
	{
		return booty.darTamanio();
	}
	
	//TODO Ambos
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
	
	//TODO Ambos
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
				//System.out.println("###______________###");
				
				booty.enqueue(compaAgregar);
				
				
				parteDelComparendo = "";
				compaAgregar = null;
				
				//System.out.println("////////////////////////////////////////AGREGADO////////////////////////////////////////");
				
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
	
	//TODO Ambos
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
	}
	

	
	
}
