package bar_cañas_gratis_RCC;


public class Bar {
	private int cañasServidas; // Nº de cañas servidas hasta el momento
	private int maxCañas;      // Máximo número de cañas por barril
	
	public Bar ( int nCañas) {
		maxCañas = nCañas;
		cañasServidas = 0;
	}
	
	public void tomarCaña() throws InterruptedException {
		synchronized (this) {  //Como la clase Cliente no usa ningún objeto para distinguir esta RC, 
			                   //utilizamos el propio objeto de la clase
			if ( cañasServidas >= maxCañas )   // Si el barril está vacío
				this.notifyAll(); 			   // Avisar al camarero
			while ( cañasServidas >= maxCañas) // Mientras el barril sigue vacío
				this.wait();                   // Esperar a que le despierte el camarero
			cañasServidas++;
		}
		servirCaña();    // Servir una caña
	}
	
	public void reponer() throws InterruptedException{
		synchronized (this) {
			if ( cañasServidas < maxCañas )
				this.wait(); // Esperar a que le avisen
			cambiarBarril(); // Esta acción indica que se repone el barril
			cañasServidas = 0;
			this.notifyAll(); // Indicar a los clientes que le avisaron que ya se ha
							  // cambiado el barril
		}
	}
	
	private void cambiarBarril() {
		System.out.println("Barril cambiado");
	}
	
	private void servirCaña() {
		System.out.println("Caña servida");
	}
}