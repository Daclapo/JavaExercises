package ejemplo_socketTCP;

import java.io.*;
import java.net.*;

public class SocketTCPClient {
	private String serverIP;
	private int serverPort;
	private Socket socket;
	private InputStream is;
	private OutputStream os;
	
	
	public static void main (String[] args) {
		
		SocketTCPClient cliente = new SocketTCPClient ("192.168.56.1", 49200) ;
		try  {
				cliente.start();
				cliente.os.write(250);
				System.out.println ("Mensaje del servidor:" + cliente.is.read());
				cliente.stop();
		} catch (UnknownHostException e) {
				e.printStackTrace();
		} catch (IOException e) {
				e.printStackTrace();
		}
	}
	
	public SocketTCPClient ( String serverIP, int serverPort)  {
		this.serverIP = serverIP;
		this.serverPort = serverPort;
	}

	public void start () throws UnknownHostException, IOException {
		System.out.println ("(Cliente) Estableciendo conexión...");
		socket = new Socket (serverIP, serverPort);
		is = socket.getInputStream();
		os = socket.getOutputStream();
		System.out.println ("(Cliente) Conexión establecida");
	}
	
	public void stop () throws IOException {
		System.out.println ("(Cliente) Cerrando conexiones...");
		is.close();
		os.close();
		socket.close();
		System.out.println ("(Cliente) Conexiones cerradas");
	}
	
}
