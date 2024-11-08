package bar_de_cañas_semáforos;

import bar_de_cañas.Bar;

public class Cliente implements Runnable{

	
	Bar bar;
	
	public Cliente (Bar bar) {
		this.bar=bar;
	}
	
	public void run() {
		bar.entrar(); // Esta acción simula accionar el pulsador de
		// entrada y esperar a que se abra la puerta
		// Estar en el bar
		bar.salir(); // Esta acción simula accionar el pulsador de
		// salida y esperar a que se abra la puerta.
	}
}