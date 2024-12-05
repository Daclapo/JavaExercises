package es.davidclarkson.practicas.ut04.socketUDP;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class Server {
	public static void main(String[] args) {
		try {
			DatagramSocket socket = new DatagramSocket(9876);
			byte[] buffer = new byte[1024];

			System.out.println("Servidor UDP esperando mensajes...");

			while (true) {
				DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
				socket.receive(packet);

				String received = new String(packet.getData(), 0, packet.getLength());
				System.out.println("Mensaje recibido: " + received);

				// Responder al cliente
				String response = "Mensaje recibido: " + received;
				byte[] responseBytes = response.getBytes();
				DatagramPacket responsePacket = new DatagramPacket(
						responseBytes, responseBytes.length,
						packet.getAddress(), packet.getPort()
				);
				socket.send(responsePacket);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
