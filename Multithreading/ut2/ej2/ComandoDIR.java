package org.ejercicios.ut2.ej2;

import java.io.IOException;
import java.io.InputStream;

public class ComandoDIR {
	public static void main(String[] args) throws IOException, InterruptedException {
		ProcessBuilder pb = new ProcessBuilder("CMD", "/C", "DIR");
		Process process = pb.start();

		try {
			InputStream is = process.getInputStream();
			int c;
			while ((c = is.read()) != -1)
				System.out.print((char) c);
				is.close();
		}catch (IOException e){
			e.printStackTrace();
		}
	}
}

