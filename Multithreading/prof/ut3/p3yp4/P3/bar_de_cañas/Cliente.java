package bar_de_cañas;


public class Cliente implements Runnable{

	//Solución CON PROBLEMAS DE CONCURRENCIA: la variable caben la pueden modificar varios hilos
	
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
