package es.davidclarkson.practicas.ut04.ejSockets1;

import java.io.*;
import java.net.*;

public class Server {
	public static void main(String[] args) {
		try (ServerSocket servidor = new ServerSocket(4301)) {
			System.out.println("Servidor iniciado. Esperando conexiones...");

			// Espera a la conexión del cliente
			try (Socket cliente = servidor.accept()) {
				System.out.println("Cliente conectado desde " + cliente.getInetAddress());


				// Creacion de flujos de entrada y salida
				DataInputStream entrada = new DataInputStream(cliente.getInputStream());
				DataOutputStream salida = new DataOutputStream(cliente.getOutputStream());

				// Leer el entero enviado por el cliente
				int numeroRecibido = entrada.readInt();
				System.out.println("Servidor: Recibido del cliente -> " + numeroRecibido);

				// Responder con el entero 200
				int respuesta = 200;
				salida.writeInt(respuesta);
				System.out.println("Servidor: Enviado al cliente -> " + respuesta);
			}

			System.out.println("Servidor: Conexión cerrada.");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

