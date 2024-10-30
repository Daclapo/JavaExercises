package org.ejercicios.ut3.contadormh1;

public class Contar {

	public static void main(String[] args) {
		Contador contador = new Contador();
		Thread[] hilos = new Thread[10];
		int quedan;

		for (int i = 0; i <= 10; i++) {
			hilos[i] = new Thread(new HiloCuenta(contador));
			hilos[i].start();
		}
		quedan = hilos.length -1;
		do {
			try {
				hilos[quedan].join();
				quedan--;
			}catch (InterruptedException e) {}
		}while (quedan > 0);
		System.out.println(contador.valor());
	}
}
