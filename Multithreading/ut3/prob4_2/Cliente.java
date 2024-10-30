package org.ejercicios.ut3.prob4_2;

public class Cliente implements Runnable {
	Bar bar;
	int canias;

	public Cliente (Bar bar, int canias) {
		this.canias = canias;
		this.bar = bar;
	}

	public void run() {
		for (int i=0;i<canias;i++) {
			try {
				bar.tomarCania();
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		}
	}

}
