package org.ejercicios.ut3.contadormh2;

public class HiloCuenta implements Runnable {
	Contador contador;

	public HiloCuenta(Contador contador) {
		this.contador = contador;
	}

	@Override
	public void run() {
		// Cada hilo incrementa el contador 5 veces
		for (int i = 0; i < 5; i++) {
			synchronized (contador) {
				contador.incrementar();
				System.out.println("Hilo " + Thread.currentThread().getName() + " - Contador: " + contador.valor());
			}
			// Simulación de trabajo con un breve descanso para que los hilos se alternen
			try {
				Thread.sleep(100);  // Espera de 100 ms
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}



