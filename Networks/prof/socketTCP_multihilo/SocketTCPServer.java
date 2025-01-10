package socketTCP_multihilo;

import java.io.*;
import java.net.*;

//Servidor multihilo que realiza un proceso que genera un número aleatorio
//y espera tantos segundos como indica dicho número, y lo devuelve al cliente

public class SocketTCPServer {
	private ServerSocket serverSocket;
	

	public static void main (String[] args) {
		try  {
			   SocketTCPServer servidor = new SocketTCPServer (49201) ;
			 } catch (IOException ioe) {
				 ioe.printStackTrace();
			 }
	}
	
	
	
	public SocketTCPServer (int puerto) throws IOException {
		serverSocket = new ServerSocket (puerto);
		while (true){
	        Socket socket =serverSocket.accept();
	        System.out.println("SERVIDOR: Conexion establecida");
	        Thread peticion = new Thread (new GestorPeticiones (socket));
	        peticion.start();
		}	
	}
	
	public void stop () throws IOException {
		serverSocket.close();
	}
	
}
