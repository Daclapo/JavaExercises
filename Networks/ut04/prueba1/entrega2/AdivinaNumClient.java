package es.davidclarkson.practicas.ut04.prueba1.entrega2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class AdivinaNumClient {
	public static void main(String[] args) {
		try (
				// Conexión al servidor
				Socket socket = new Socket("localhost", 12345);
				BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
				BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))
		) {
			String serverMessage;

			// Lee mensajes del servidor
			while ((serverMessage = in.readLine()) != null) {
				System.out.println(serverMessage); // Muestra mensaje del servidor

				// Si el juego ha terminado, rompe el bucle
				if (serverMessage.contains("Juego terminado") || serverMessage.contains("Ganador") || serverMessage.contains("Fin del juego")) {
					break;
				}

				// Solo pide entrada si el servidor lo solicita
				if (serverMessage.contains("Introduce tu número:")) {
					String userInput = reader.readLine(); // Lee la entrada del usuario
					out.println(userInput); // Envía la entrada al servidor
				}
			}
		} catch (IOException e) {
			System.out.println("Error conectando el cliente al servidor:\n" + e.getMessage());
		}
	}
}
