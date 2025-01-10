package es.davidclarkson.practicas.ut04.concsockets

.2daTarea-2daEval;

import java.io.*;
import java.net.*;

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

			while ((serverMessage = in.readLine()) != null) {
				System.out.println(serverMessage);

				if (serverMessage.contains("Introduce tu número:")) {
					String userInput = reader.readLine(); // Lee la entrada del usuario
					out.println(userInput);
				}
			}
		} catch (IOException e) {
			System.out.println("Error conectando el cliente al servidor:\n" + e.getMessage());
		}
	}
}
