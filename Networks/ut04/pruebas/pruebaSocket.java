package es.davidclarkson.practicas.ut04.pruebas;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;

public class pruebaSocket {

	public static void main(String[] args) throws IOException {

		Socket cliente;

		cliente = new Socket("172.17.197.29", 38080);

		DataInputStream in = new DataInputStream(cliente.getInputStream());
		PrintStream out = new PrintStream(cliente.getOutputStream());

	}
}
