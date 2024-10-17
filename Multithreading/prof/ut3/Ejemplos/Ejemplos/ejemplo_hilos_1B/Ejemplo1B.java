package org.ejercicios.prof.ut3.Ejemplos.Ejemplos.ejemplo_hilos_1B;


public class Ejemplo1B {
	public static void main(String[] args) {
		Thread hilo;
		for (int i = 0; i < 10; i++) {
			// hilo = new Thread(new ejemplo_hilos_1B.Trabajador(i));  hilo.start();
		}
		System.out.println("Fin del main");
	}
}
