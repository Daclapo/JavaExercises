package org.ejercicios.ut3.prob6;


public class TestBanco {

	public static void main(String[] args) {
		final Cuenta cc = new Cuenta(1000);
		final int N = 10;
		final Thread[] clientes = new Thread[N];

		for (int i = 0; i < clientes.length; i++) {
			clientes[i] = new Thread(new Cliente(cc));
		}
		for (Thread cliente : clientes) {
			cliente.start();
		}
		try {
			Thread.sleep(2000);
			cc.embargar();
			Thread.sleep(2000);
			cc.desembargar();
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}

		for (Thread cliente : clientes) {
			try {
				cliente.join();
			} catch (InterruptedException ignorada) { }
		}

		System.out.println("Saldo final: " + cc.getSaldo());
	}
}