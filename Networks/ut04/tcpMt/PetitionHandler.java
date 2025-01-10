package es.davidclarkson.practicas.ut04.tcpMt;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Random;

public class PetitionHandler implements Runnable {

	private Socket socket;

	public PetitionHandler(Socket socket) throws IOException {
		socket = this.socket;
	}

	@Override
	public void run() {
		if (socket != null) {
			System.out.println("Cliente conectado desde " + socket.getInetAddress());

			DataOutputStream out = null;
			try {
				out = new DataOutputStream(socket.getOutputStream());
			} catch (IOException e) {
				throw new RuntimeException(e);
			}

			int ri = new Random().nextInt(1, 5);

			try {
				wait(ri * 1000L);
				out.write(ri);
			} catch (IOException | InterruptedException e) {
				throw new RuntimeException(e);
			}

			try {
				socket.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}
}
