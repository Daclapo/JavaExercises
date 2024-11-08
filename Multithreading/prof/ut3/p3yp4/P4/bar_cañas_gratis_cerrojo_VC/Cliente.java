package bar_cañas_gratis_cerrojo_VC;



public class Cliente implements Runnable {
	Bar bar;
	int cañas;
	
	public Cliente (Bar bar, int cañas) {
		this.cañas=cañas;
		this.bar=bar;
	}

	public void run() {
		for (int i=0;i<cañas;i++) { // Disfrutar hasta tener sed
				try {
					bar.tomarCaña();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
									// Esta acción simula elegir alguno de
		} 							// los grifos disponibles para servirse
									// una caña.
	}
}
