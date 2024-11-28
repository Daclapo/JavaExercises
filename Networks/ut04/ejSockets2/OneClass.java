package es.davidclarkson.practicas.ut04.ejSockets2;

import java.io.*;
import java.net.*;

public class OneClass {
	public static void main(String[] args) {
		Thread servidorThread = new Thread(OneClass::iniciarServidor);
		Thread clienteThread = new Thread(OneClass::iniciarCliente);

		// Inicia ambos hilos
		servidorThread.start();
		clienteThread.start();

		try {
			servidorThread.join();
			clienteThread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void iniciarServidor() {
		try (ServerSocket servidor = new ServerSocket(4301)) {
			System.out.println("[Servidor] Iniciado en IP: " + InetAddress.getLocalHost().getHostAddress() + " Puerto: 4301");

			// Espera la conexión del cliente
			try (Socket cliente = servidor.accept()) {
				System.out.println("[Servidor] Cliente conectado desde IP: " + cliente.getInetAddress().getHostAddress() +
						" Puerto: " + cliente.getPort());

				// Crear flujos para entrada y salida
				BufferedReader entrada = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
				PrintWriter salida = new PrintWriter(cliente.getOutputStream(), true);

				// Leer y responder mensajes
				String mensaje;
				while ((mensaje = entrada.readLine()) != null) {
					System.out.println("[Servidor] Recibido: " + mensaje);

					if (mensaje.equalsIgnoreCase("salir")) {
						salida.println("Conexión terminada. Adiós.");
						System.out.println("[Servidor] Cliente solicitó cerrar la conexión.");
						break;
					}

					salida.println("Servidor recibió: " + mensaje);
				}
			}

			System.out.println("[Servidor] Conexión cerrada.");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void iniciarCliente() {
		try {
			// Esperar un poco para dar tiempo al servidor a iniciarse
			Thread.sleep(1000);

			try (Socket socket = new Socket("localhost", 4301)) {
				System.out.println("[Cliente] Conectado al servidor en IP: " + socket.getInetAddress().getHostAddress() +
						" Puerto: " + socket.getPort());

				// Crear flujos para entrada y salida
				BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				PrintWriter salida = new PrintWriter(socket.getOutputStream(), true);

				// Enviar mensajes al servidor
				String[] mensajes = {"Hola servidor", "Este es un mensaje", "salir"};
				for (String mensaje : mensajes) {
					System.out.println("[Cliente] Enviando: " + mensaje);
					salida.println(mensaje);

					// Leer respuesta del servidor
					String respuesta = entrada.readLine();
					System.out.println("[Cliente] Recibido: " + respuesta);

					Thread.sleep(500); // Simular un pequeño retraso entre mensajes
				}
			}

			System.out.println("[Cliente] Conexión terminada.");
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}
}
