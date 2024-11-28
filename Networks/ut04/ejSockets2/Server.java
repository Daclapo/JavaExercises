package es.davidclarkson.practicas.ut04.ejSockets2;

import java.io.*;
import java.net.*;

public class Server {
	public static void main(String[] args) {
		try (ServerSocket servidor = new ServerSocket(4301)) {
			System.out.println("Servidor iniciado. Esperando conexiones...");

			try (Socket cliente = servidor.accept()) {
				System.out.println("Cliente conectado desde 172.17.225.67 (Jesus)");

				DataInputStream dataEntrada = new DataInputStream(cliente.getInputStream());
				DataOutputStream dataSalida = new DataOutputStream(cliente.getOutputStream());
				BufferedReader textoEntrada = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
				PrintWriter textoSalida = new PrintWriter(cliente.getOutputStream(), true);

				int numeroRecibido = dataEntrada.readInt();
				System.out.println("Servidor (DataInputStream): Recibido -> " + numeroRecibido);

				int respuesta = 200;
				dataSalida.writeInt(respuesta);
				System.out.println("Servidor (DataOutputStream): Enviado -> " + respuesta);

				System.out.println("Servidor: Ahora intercambiando mensajes de texto.");
				String mensajeCliente;
				while ((mensajeCliente = textoEntrada.readLine()) != null) {
					System.out.println("Servidor (BufferedReader): Recibido -> " + mensajeCliente);

					if (mensajeCliente.equalsIgnoreCase("salir")) {
						System.out.println("Servidor: Cliente solicitó terminar la conexión.");
						textoSalida.println("Conexión terminada. Adiós.");
						break;
					}

					textoSalida.println("Servidor recibió: " + mensajeCliente);
				}
			}

			System.out.println("Servidor: Conexión cerrada.");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
