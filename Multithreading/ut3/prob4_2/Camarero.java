package org.ejercicios.ut3.prob4_2;

public class Camarero implements Runnable
{
	Bar bar;

	public Camarero (Bar bar) {
		this.bar = bar;
	}

	public void run() {
		while (true) {
			for (int barriles = 0; barriles < 5; barriles++) {
				try {
					bar.reponer();
				} catch (InterruptedException e) {
					throw new RuntimeException(e);
				}
			}
		}
	}
}