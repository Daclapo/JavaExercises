package es.davidclarkson.practicas.ut04.tcpMt;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

	public static void main(String[] args) throws IOException {

		try (ServerSocket server = new ServerSocket(4321)){
			while (true) {
				Socket socket = server.accept();
				System.out.println("Conexion establecida");
				Thread petition = new Thread(new PetitionHandler(socket));
				petition.start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
