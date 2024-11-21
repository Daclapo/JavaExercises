package es.davidclarkson.practicas.ut04;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class printIP {
	public static void main(String[] args) {
		for (int i = 0; i < 50; i++) {
			try {
				InetAddress ia = InetAddress.getLocalHost();

				String hostName = ia.getHostName();
				String ipAddress = ia.getHostAddress();
				byte[] dir = ia.getAddress();
				String dirS = printBinary(dir);

				System.out.println("Host Name: " + hostName);
				System.out.println("IP Address8: " + ipAddress);
				System.out.println("IP Address2: " + dirS);
				System.out.println("-----");

				Thread.sleep(3000);

			} catch (UnknownHostException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public static String printBinary(byte[] dir) {
		StringBuilder ret = new StringBuilder();
		for (int i = 0; i < dir.length; i++) {
			ret.append(String.format("%8s", Integer.toBinaryString(dir[i] & 0xFF)).replace(' ', '0'));
			if (i < dir.length - 1)
				ret.append(".");
		}
		return ret.toString();
	}
}
