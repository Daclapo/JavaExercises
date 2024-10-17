package org.ejercicios.ut3.contadormultihilo;

public class HiloCuenta implements Runnable {
	Contador contador;

	public HiloCuenta(Contador contador) {
		this.contador = contador;
	}

	@Override
	public void run() {
		synchronized (contador) {
			contador.incrementar();
			System.out.println(contador.valor());
		}
	}
}



