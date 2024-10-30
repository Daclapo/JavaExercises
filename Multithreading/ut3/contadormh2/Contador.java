package org.ejercicios.ut3.contadormh2;

public class Contador {
	int contador = 0;

	// Método sincronizado para incrementar el contador
	public synchronized void incrementar() {
		contador++;
	}

	// Método para obtener el valor actual del contador
	public int valor() {
		return contador;
	}
}
