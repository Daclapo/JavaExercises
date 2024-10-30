package org.ejercicios.prof.ut3.prob4.bar_cañas_gratis_con_monitores;

public class Bar {
	private int cañasServidas; // Nº de cañas servidas hasta el momento
	private int maxCañas;      // Máximo número de cañas por barril
	
	public Bar ( int nCañas) {
		maxCañas = nCañas;
		cañasServidas = 0;
	}
	
	public synchronized void tomarCaña() throws InterruptedException {
			if ( cañasServidas >= maxCañas )   // Si el barril está vacío
				notifyAll(); 			       // Avisar al camarero
			while ( cañasServidas >= maxCañas) // Mientras el barril sigue vacío
				wait();                        // Esperar a que le despierte el camarero
			cañasServidas++;
			servirCaña();    // Servir una caña
	}
	
	public synchronized void reponer() throws InterruptedException{
			if ( cañasServidas < maxCañas )
				wait();          // Esperar a que le avisen
			cambiarBarril();     // Esta acción indica que se repone el barril
			cañasServidas = 0;
			notifyAll();      // Indicar a los clientes que le avisaron que ya se ha
							  // cambiado el barril
	}
	
	private void cambiarBarril() {
		System.out.println("Barril cambiado");
	}
	
	private void servirCaña() {
		System.out.println("Caña servida");
	}
}