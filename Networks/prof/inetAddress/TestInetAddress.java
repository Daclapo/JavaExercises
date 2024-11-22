package ejemplo1_URL;

import java.net.*;

public class TestInetAddress {

	public static void main (String [] args) { 
		
		InetAddress dir = null;
		System.out.println ("===================================");
		System.out.println ("SALIDA PARA LOCAL HOST :");
		try {
			//LOCAL HOST
			dir = InetAddress.getByName("localhost");
			metodos (dir);	
		
			//URL www.google.es
			System.out.println ("===================================");
			System.out.println ("SALIDA PARA UNA URL :");
			dir = InetAddress.getByName("www.google.es");
			metodos (dir);
		
			//Array de tipo InetAddress con todas las direcciones IP asignadas a google.es
			System.out.println ("\tDIRRECIONES IP PARA: "+ dir.getHostName());
			InetAddress [] direcciones = InetAddress.getAllByName (dir.getHostName());
		
			for (int i=0; i<direcciones.length; i++)
				System.out.println ("\t\\t" + direcciones[i].toString());
		
				System.out.println ("===================================");
		} catch (UnknownHostException e1) {e1.printStackTrace();}
	} //main
	
	private static void metodos (InetAddress dir) {
		System.out.println ("\tMetodo getByName (): " + dir);
		InetAddress dir2;
		try {
			dir2 = InetAddress.getLocalHost();
			System.out.println ("\tMetodo getByName (): " + dir2);
		} catch (UnknownHostException e) {e.printStackTrace();}
		
		//USAMOS LOS MÉTODOS DE LA CLASE
		System.out.println ("\tMetodo getHostName (): " + dir.getHostName());
		System.out.println ("\tMetodo getHostAddress (): " + dir.getHostAddress());
		
		System.out.println ("\tMetodo toString (): " + dir.toString());
		System.out.println ("\tMetodo getCanonicalHostName (): " + dir.getCanonicalHostName());
		}
	}
