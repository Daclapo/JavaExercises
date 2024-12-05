package es.davidclarkson.practicas.ut04.ejSocket3;

import java.io.*;
import java.net.*;

public class Server {
	public static void main(String[] args) throws IOException {

		try (ServerSocket serverSocket = new ServerSocket(3333)){
			System.out.println("Programa servidor iniciado, y esperando a conexiones.");

			try (Socket clientSocket = serverSocket.accept()){
				System.out.println("Cliente conectado con IP: " + clientSocket.getInetAddress().getHostAddress());

				BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
				PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

				String path = in.readLine();
				System.out.println("Servidor recibe ruta: " + path);

				File file = new File(path);
				if (file.exists() && file.isFile()) {
					BufferedReader br = new BufferedReader(new FileReader(file));
					String line;
					while ((line = br.readLine()) != null) {
						out.println(line);
					}
					// No se si se tiene que cerrar, al estar en un try-with-resources,
					// pero una vez se lee el fichero entero, se cierra el BufferedReader.
					br.close();
					System.out.println("\n---\nYa se ha leido e impreso el fichero entero al cliente.");
				} else {
					out.println("No se ha podido llegar a el fichero al no existir o no ser un fichero valido.");
					System.out.println("No se ha encontrado el archivo en el servidor.");
				}
			}
		}catch (IOException e) {
			e.printStackTrace();
		}
	}

}
