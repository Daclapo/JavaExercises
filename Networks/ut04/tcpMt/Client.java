package es.davidclarkson.practicas.ut04.tcpMt;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

public class Client {
	public static void main(String[] args) throws IOException {

		try (Socket socket = new Socket("localhost", 4321)) {
			System.out.println("Cliente: Conectado al servidor.");

			DataInputStream in = new DataInputStream(socket.getInputStream());

			int respuesta = in.readInt();
			System.out.println("Cliente: Recibido del servidor -> " + respuesta);

		} catch (IOException e) {
			e.printStackTrace();
		}


		}
}
