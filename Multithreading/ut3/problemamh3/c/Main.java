package org.ejercicios.ut3.problemamh3.c;

public class Main {
	public static void main(String[] args) {
		Bar bar = new Bar(100);

		for (int i = 1; i <= 1000; i++) {
			Thread cliente = new Thread(new Cliente(bar), "Cliente-" + i);
			cliente.start();
		}
	}
}
