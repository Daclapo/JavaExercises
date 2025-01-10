package es.davidclarkson.practicas.ut04.prueba1.entrega2;

import java.io.*;
import java.net.*;

public class ClientHandler extends Thread {
	private final Socket clientSocket;
	private final Juego juego;
	private final String playerName;
	private PrintWriter out;

	// Constructor
	public ClientHandler(Socket socket, Juego juego, String playerName) {
		this.clientSocket = socket;
		this.juego = juego;
		this.playerName = playerName;
	}

	@Override
	public void run() {
		try (
				BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		) {
			out = new PrintWriter(clientSocket.getOutputStream(), true);

			// Mensaje de bienvenida
			out.println("Buenos días " + playerName + ".\nTienes 5 intentos para adivinar el número.");

			int attempts = 0;

			while (attempts < 5 && !juego.isGameFinished()) {
				out.println("Introduce tu número:");
				String input = in.readLine();

				if (input == null || input.isEmpty()) {
					out.println("Entrada inválida. Por favor, introduce un número.");
					continue;
				}

				try {
					int guess = Integer.parseInt(input);
					attempts++;
					String response = juego.checkGuess(playerName, guess);
					out.println(response);

					if (juego.isGameFinished()) {
						// Notifica a todos los clientes que el juego ha terminado
						AdivinaNumServer.notifyAllClients(
								"El número era " + juego.getNumberToGuess() + "\nJuego terminado. Ganador: " + juego.getWinner()
						);
						break;
					}
				} catch (NumberFormatException e) {
					out.println("Entrada inválida. Por favor, introduce un número válido.");
				}
			}

			if (!juego.isGameFinished() && attempts == 5) {
				out.println("Has usado todos tus intentos. Fin del juego.");
			}
		} catch (IOException e) {
			System.out.println("Error manejando al cliente: " + e.getMessage());
		} finally {
			try {
				clientSocket.close();
			} catch (IOException e) {
				System.out.println("Error cerrando el socket: " + e.getMessage());
			}
		}
	}

	// Método para enviar mensajes al cliente
	public void sendMessage(String message) {
		if (out != null) {
			out.println(message);
		}
	}
}
