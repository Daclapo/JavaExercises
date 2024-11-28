package es.davidclarkson.practicas.ut04.ejSockets2;

import java.io.*;
import java.net.*;

public class Client {
	public static void main(String[] args) {
		try (Socket socket = new Socket("localhost", 4301)) {
			System.out.println("Cliente: Conectado al servidor.");

			DataOutputStream dataSalida = new DataOutputStream(socket.getOutputStream());
			DataInputStream dataEntrada = new DataInputStream(socket.getInputStream());
			BufferedReader textoEntrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			PrintWriter textoSalida = new PrintWriter(socket.getOutputStream(), true);

			int numero = 250;
			System.out.println("Cliente (DataOutputStream): Enviando -> " + numero);
			dataSalida.writeInt(numero);

			int respuesta = dataEntrada.readInt();
			System.out.println("Cliente (DataInputStream): Recibido -> " + respuesta);

			System.out.println("Cliente: Intercambio de mensajes de texto.");
			BufferedReader consola = new BufferedReader(new InputStreamReader(System.in));
			String mensaje;

			while (true) {
				System.out.print("Cliente: Escribe un mensaje (o 'salir' para terminar): ");
				mensaje = consola.readLine();

				textoSalida.println(mensaje);
				String respuestaServidor = textoEntrada.readLine();
				System.out.println("Cliente (BufferedReader): Recibido -> " + respuestaServidor);

				if (mensaje.equalsIgnoreCase("salir")) {
					System.out.println("Cliente: Conexión terminada.");
					break;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
