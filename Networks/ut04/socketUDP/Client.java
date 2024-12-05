package es.davidclarkson.practicas.ut04.socketUDP;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Client {
	public static void main(String[] args) {
		try {
			DatagramSocket socket = new DatagramSocket();
			InetAddress serverAddress = InetAddress.getByName("localhost");

			String message = "Hola servidor, soy David";
			byte[] buffer = message.getBytes();

			DatagramPacket packet = new DatagramPacket(buffer, buffer.length, serverAddress, 9876);
			socket.send(packet);
			System.out.println("Mensaje enviado: " + message);


			// Recibir respuesta del servidor
			byte[] responseBuffer = new byte[1024];
			DatagramPacket responsePacket = new DatagramPacket(responseBuffer, responseBuffer.length);
			socket.receive(responsePacket);

			String response = new String(responsePacket.getData(), 0, responsePacket.getLength());
			System.out.println("Respuesta del servidor: " + response);

			socket.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
