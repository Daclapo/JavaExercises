package es.davidclarkson.practicas.ut04.entrega1;

import java.io.*;
import java.net.*;

public class ClientHandler extends Thread {
	private final Socket clientSocket;
	private final Juego juego;
	private final String playerName;

	// Constructor para un cliente especifico.
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
			out.println("Buenos dias " + playerName + ".\nTienes 5 intentos para adivinar el número.");

			int intentos = 0;
			while (intentos < 5 && !juego.isGameFinished()) {
				out.println("Introduce tu conjetura:");
				String input = in.readLine();

				try {
					int guess = Integer.parseInt(input);
					intentos++;
					String response = juego.checkGuess(playerName, guess);
					out.println(response);

					if (juego.isGameFinished()) {
						out.println("Juego terminado. Ganador: " + juego.getWinner());
						break;
					} else if (intentos == 5) {
						out.println("Has usado todos tus intentos. Fin del juego.");
					}
				} catch (NumberFormatException e) {
					out.println("Entrada inválida. Por favor, introduce un número.");
				}
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