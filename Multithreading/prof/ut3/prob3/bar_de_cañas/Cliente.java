package org.ejercicios.prof.ut3.prob3.Problema3.Problema3.bar_de_cañas;


public class Cliente implements Runnable{

	//Solución CON PROBLEMAS DE CONCURRENCIA: la variable caben la pueden modificar varios hilos
	
	Bar bar;
	
	public Cliente (Bar bar) {
		this.bar=bar;
	}

	public Cliente(bar_de_cañas.Bar bar) {
	}

	public void run() {
		bar.entrar(); // Esta acción simula accionar el pulsador de
		// entrada y esperar a que se abra la puerta
		// Estar en el bar
		bar.salir(); // Esta acción simula accionar el pulsador de
		// salida y esperar a que se abra la puerta.
	}
}
