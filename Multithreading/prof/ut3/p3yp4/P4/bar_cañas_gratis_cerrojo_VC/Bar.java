package bar_cañas_gratis_cerrojo_VC;

import java.util.concurrent.locks.*;

public class Bar {
	
	private int cañasServidas; 			// Nº de cañas servidas hasta el momento
	private int maxCañas; 				// Máximo número de cañas por barril
	private Lock mutexBarril; 			// Cerrojo para la RCC de reponer barril
	private Condition avisoCamarero; 	// Variable de condición para avisar al
										//camarero cuando se acaba el barril
	private Condition cambioBarril; 	// Variable de condición para esperar a que el
										//barril sea cambiado

	public Bar ( int nCañas ) {
		this.maxCañas = nCañas;
		cañasServidas = 0;
		mutexBarril = new ReentrantLock();
		avisoCamarero = mutexBarril.newCondition();
		cambioBarril = mutexBarril.newCondition();
	}
	
	public void tomarCaña() throws InterruptedException {
		mutexBarril.lock(); 	// Inicio de una RCC para que el acceso a cañasServidas se
								//haga en exclusión mutua
		try {
			if ( cañasServidas >= maxCañas ) { // Si el barril se ha agotado
				avisoCamarero.signal(); 	   // Avisar al camarero
				cambioBarril.await();          // Esperar a que el camarero reponga el barril
			}
			cañasServidas++;
		} finally {
			mutexBarril.unlock(); // Fin de la RC
		}
		servirCaña(); // Servir una caña.
	}
	
	public void reponer() throws InterruptedException {
		mutexBarril.lock(); 	// Inicio de una RCC para que el acceso a cañasServidas
								//se haga en exclusión mutua
		try {
			if ( cañasServidas < maxCañas )
				avisoCamarero.await(); 	// Esperar a que le avisen
			cambiarBarril(); 			// Esta acción indica que se repone el barril
			cañasServidas = 0;
			cambioBarril.signalAll(); 	// Indicar a los clientes que le avisaron que
										//ya se ha cambiado el barril
			} finally {
				mutexBarril.unlock(); // Fin de la RC
			}
	}
	
	private void cambiarBarril() {
		System.out.println("Barril cambiado");
	}
	
	private void servirCaña() {
		System.out.println("Caña servida");
	}
}