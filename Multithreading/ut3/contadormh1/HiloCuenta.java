package org.ejercicios.ut3.contadormh1;

public class HiloCuenta implements Runnable {
	final Contador contador;

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



