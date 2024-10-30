package org.ejercicios.ut3.prob4_2;

public class Bar
{
	/**
	 * @param nCanias Nº de cañas que pueden ser servidas de un barril
	 */
	private int maxCanias;
	private int caniasServidas;

	public Bar (int nCanias) {
		maxCanias = nCanias;
		caniasServidas = 0;

	}
	public void tomarCania () throws InterruptedException {
		synchronized (this){
			if (caniasServidas >= maxCanias)
				this.notifyAll();
			while (caniasServidas >= maxCanias)
				this.wait();
			caniasServidas++;
		}
		servirCania();
	}

	public void reponer() throws InterruptedException {
		synchronized (this)
		{
			if (caniasServidas < maxCanias)
				this.wait();
			cambiarBarril();
			caniasServidas = 0;
			this.notifyAll();
		}
		cambiarBarril();
	}


	private void servirCania() {
		System.out.println("Caña servida");
	}

	private void cambiarBarril()  {
		System.out.println("Barril cambiado");
	}
}
