package bar_cañas_gratis_cerrojo_VC;


public class Camarero implements Runnable {

	Bar bar;
	
	public Camarero (Bar bar) {
		this.bar=bar;
	}
	
	public void run() {
		while (true)
			for (int barriles = 0; barriles < 5; barriles++) {
				try {
					bar.reponer();       // Esperar a que le avisen que se ha
					                     // acabado el barril y entonces reponerlo.
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}						
	}
	
}