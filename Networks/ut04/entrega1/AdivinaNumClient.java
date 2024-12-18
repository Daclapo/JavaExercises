package es.davidclarkson.practicas.ut04.entrega1;

import java.io.*;
import java.net.*;

public class AdivinaNumClient {
	public static void main(String[] args) {
		try (Socket socket = new Socket("localhost", 12345);
		     BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		     PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
		     BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {

			// Maneja la comunicación con el servidor
			String serverMessage;
			while ((serverMessage = in.readLine()) != null) {
				System.out.println(serverMessage);

				if (serverMessage.contains("Juego terminado") || serverMessage.contains("Ganador")) {
					break;
				}

				String userInput = reader.readLine();
				out.println(userInput);
			}
		} catch (IOException e) {
			System.out.println("Error conectando el cliente al servidor:\n" + e.getMessage());
		}
	}
}