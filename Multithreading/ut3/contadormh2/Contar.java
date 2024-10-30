package org.ejercicios.ut3.contadormh2;

public class Contar {

	public static void main(String[] args) {
		Thread[] hilos = new Thread[10];  // Array para almacenar los hilos
		Contador contador = new Contador();

		// Crear y lanzar 10 hilos
		for (int i = 0; i < 10; i++) {
			hilos[i] = new Thread(new HiloCuenta(contador), "Hilo-" + (i + 1));
			hilos[i].start();
		}

		// Esperar a que todos los hilos terminen
		for (int i = 0; i < 10; i++) {
			try {
				hilos[i].join();  // Espera a que cada hilo termine
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		// Mostrar el valor final del contador
		System.out.println("Valor final del contador: " + contador.valor());
	}
}
