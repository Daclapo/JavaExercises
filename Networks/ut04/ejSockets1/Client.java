package es.davidclarkson.practicas.ut04.ejSockets1;

import java.io.*;
import java.net.*;

public class Client {
	public static void main(String[] args) {
		try (Socket socket = new Socket("localhost", 4301)) {
			System.out.println("Cliente: Conectado al servidor.");

			// Creacion de flujos de entrada y salida
			DataOutputStream salida = new DataOutputStream(socket.getOutputStream());
			DataInputStream entrada = new DataInputStream(socket.getInputStream());

			// Enviar entero 250 al servidor
			int numero = 250;
			System.out.println("Cliente: Enviando al servidor -> " + numero);
			salida.writeInt(numero);

			// Leer respuesta del servidor
			int respuesta = entrada.readInt();
			System.out.println("Cliente: Recibido del servidor -> " + respuesta);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
