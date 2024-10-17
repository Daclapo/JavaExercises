package org.ejercicios.ut2.ej1;

import java.io.IOException;

public class Ejercicio1 {
	public static void main(String[] args) {
		String programPath = "C:\\Program Files\\Notepad++\\notepad++.exe"; // Cambia esta ruta según sea necesario

		ProcessBuilder processBuilder = new ProcessBuilder(programPath);

		try {
			Process process = processBuilder.start();

			System.out.println("PID del proceso: " + process.pid());

			int exitCode = process.waitFor();

			System.out.println("Código de salida del proceso: " + exitCode);
		} catch (IOException e) {
			System.err.println("Error de entrada/salida: " + e.getMessage());
		} catch (InterruptedException e) {
			System.err.println("El proceso fue interrumpido: " + e.getMessage());
		}
		System.out.println(processBuilder.environment());

		while (processBuilder.environment().containsKey("PID")){
			System.out.println(processBuilder.environment().get("PID"));

		}
	}
}
