package es.davidclarkson.practicas.ut04.prueba1.entrega2;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

public class AdivinaNumServer {
	// Lista de clientes conectados
	private static final List<ClientHandler> clients = new ArrayList<>();

	public static void main(String[] args) {
		// Inicializa el juego
		Juego juego = new Juego();
		System.out.println("Servidor iniciado. Número a adivinar: " + juego.getNumberToGuess());

		try (ServerSocket serverSocket = new ServerSocket(12345)) {
			int playerCount = 0;

			while (!juego.isGameFinished()) {
				Socket clientSocket = serverSocket.accept();
				playerCount++;
				String playerName = "Jugador-" + playerCount;

				System.out.println(playerName + " conectado.");
				ClientHandler clientHandler = new ClientHandler(clientSocket, juego, playerName);

				// Añade el cliente a la lista
				synchronized (clients) {
					clients.add(clientHandler);
				}

				clientHandler.start();
			}
		} catch (IOException e) {
			System.out.println("Error al iniciar el servidor: " + e.getMessage());
		}
	}

	// Método para notificar a todos los clientes
	public static void notifyAllClients(String message) {
		synchronized (clients) {
			for (ClientHandler client : clients) {
				client.sendMessage(message);
			}
		}
	}
}
