package org.ejercicios.ut3.prob4;

public class Cliente implements Runnable {
	Bar bar;
	int canias;
	public Cliente (Bar bar, int cañas) {
		this.canias=cañas;
		this.bar=bar;
	}
	public void run() {
		for (int i=0;i<canias;i++) {
			bar.tomarCania();
		}
	}

}
