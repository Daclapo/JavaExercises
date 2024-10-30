package org.ejercicios.ut3.prob4_2;

public class Programa {

	private static final int MAX_CANIAS = 30;
	private static final int MAX_CLIENTES = 25;
	private static final int NUM_CLIENTES = 20;

	public static void main(String[] args) {

		Bar bar = new Bar(MAX_CANIAS);
		int canias = MAX_CANIAS;

		Thread camarero = new Thread(new Camarero(bar));
		camarero.start();

		Thread[] clientes = new Thread[NUM_CLIENTES];
		int quedan;


	}

}
