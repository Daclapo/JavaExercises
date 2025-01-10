package es.davidclarkson.practicas.ut04.concsockets

.2daTarea-2daEval;

import java.io.*;
import java.net.*;

public class AdivinaNumServer {
	public static void main(String[] args) {
		// Inicializa el juengo, creando el numero aleatorio que los clientes/usuarios tendran que adivinar.
		Juego juego = new Juego();
		System.out.println("Servidor iniciado. Número a adivinar: " + juego.getNumberToGuess());

		try (ServerSocket serverSocket = new ServerSocket(12345)) {
			int playerCount = 0;

			while (!juego.isGameFinished()) {
				Socket clientSocket = serverSocket.accept();
				playerCount++;
				String playerName = "Jugador " + playerCount;

				System.out.println(playerName + " conectado.");
				ClientHandler clientHandler = new ClientHandler(clientSocket, juego, playerName);
				clientHandler.start();
			}
		} catch (IOException e) {
			System.out.println("Error al iniciar el servidor: " + e.getMessage());
		}
	}
}