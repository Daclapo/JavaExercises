package org.ejercicios.ut3.problemamh3.c;

public class Cliente implements Runnable {
	private Bar bar;

	public Cliente(Bar bar) {
		this.bar = bar;
	}

	@Override
	public void run() {
		bar.entrar();

		try {
			System.out.println(Thread.currentThread().getName() + " está en el bar.");
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		bar.salir();
	}
}

