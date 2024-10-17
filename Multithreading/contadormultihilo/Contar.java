package org.ejercicios.ut3.contadormultihilo;

public class Contar {

	public static void main(String[] args) {
		Thread hilo;
		for (int i = 1; i <= 10; i++) {
			Contador contador = new Contador();
			hilo = new Thread(new HiloCuenta(contador));
			hilo.start();
		}
	}

}
