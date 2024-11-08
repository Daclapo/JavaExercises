package bar_cañas_gratis_con_monitores;

import bar_cañas_gratis_RCC.Bar;

public class Camarero implements Runnable {

	Bar bar;
	
	public Camarero (Bar bar) {
		this.bar=bar;
	}
	
	public void run() {
		while (true)
			try {
				for (int barriles = 0; barriles < 5; barriles++) {
				bar.reponer();
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} // Esperar a que le avisen que se ha
						   // acabado el barril y entonces reponerlo.
	}
	
}
