package org.ejercicios.ut3.contadormultihilo;

public class Contador {
	int contador = 1;

	public void incrementar() {
		contador = contador + 5;
	}
	public int valor() {
		return contador;
	}

}
