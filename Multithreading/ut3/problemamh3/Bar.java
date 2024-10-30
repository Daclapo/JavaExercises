package org.ejercicios.ut3.problemamh3;

public class Bar {
// Atributos para controlar el acceso al bar.

	/**
	 * @param aforo Nº de clientes que caben en el local
	 */
	public Bar(int aforo) {
// inicializar el bar
	}

	public void entrar() {
// Permitir la entrada de un cliente si no se ha alcanzado
// el aforo, y quedarse bloqueado en caso contrario.
		abrirPuertaE(); // Muestra el letrero de “se puede entrar” y
// abre la puerta de entrada
	}
	public void salir() {
		abrirPuertaS(); // Muestra el letrero de “se puede salir” y
// abre la puerta de salida
// Anotar que un cliente sale del bar, y desbloquear a
// quien pudiera estar esperando, para que entre uno más
	}



	private void abrirPuertaE() {

	}

	private void abrirPuertaS() {

	}
}

