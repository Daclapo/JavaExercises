package es.davidclarkson.practicas.ut04.ejSocket3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;

public class Client {

	public static void main(String[] args) throws IOException {

		try (Socket socket = new Socket("localhost", 3333)) {
			System.out.println("Cliente conectado.");

			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

			BufferedReader inputConsola = new BufferedReader(new InputStreamReader(System.in));
			System.out.print("Clienete, introduzca la ruta del archivo a leer aquí: ");
			String path = inputConsola.readLine();
			out.println(path);

			String line;
			while ((line = in.readLine()) != null) {
				System.out.println(line);
			}


		}catch (IOException e) {
			e.printStackTrace();
		}

	}
}
