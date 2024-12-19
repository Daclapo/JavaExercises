package es.davidclarkson.practicas.ut04.entrega1;

import java.io.*;
import java.net.*;

public class ClientHandler extends Thread {
	private final Socket clientSocket;
	private final Juego juego;
	private final String playerName;

	// Constructor para un cliente específico
	public ClientHandler(Socket socket, Juego juego, String playerName) {
		this.clientSocket = socket;
		this.juego = juego;
		this.playerName = playerName;
	}

	@Override
	public void run() {
		try (
				BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
				PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)
		) {
			out.println("Buenos días " + playerName + ".\nTienes 5 intentos para adivinar el número.");

			int intentos = 0;

			// Mientras queden intentos y el juego no haya terminado, se piden numeros
			while (intentos < 5 && !juego.isGameFinished()) {
				out.println("Introduce tu número:");
				String input = in.readLine();

				if (input == null || input.isEmpty()) {
					out.println("Entrada inválida. Por favor, introduce un número.");
					continue; // Pide otra entrada
				}

				try {
					int guess = Integer.parseInt(input);
					intentos++;

					// Verifica el intento y envía la respuesta al cliente
					String response = juego.checkGuess(playerName, guess);
					out.println(response);

					// Si el juego terminó, informa al cliente
					if (juego.isGameFinished()) {
						out.println("Juego terminado.\nGanador: " + juego.getWinner());
						break;
					}
				} catch (NumberFormatException e) {
					out.println("Entrada inválida. Por favor, introduce un número válido.");
				}
			}

			// Si se agotaron los intentos
			if (!juego.isGameFinished() && intentos == 5) {
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
}
