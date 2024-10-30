package org.ejercicios.ut3.prob4;

public class Camarero implements Runnable
{
	Bar bar;
	public Camarero (Bar bar) {
		this.bar=bar;
	}
	public void run()
	{
		while (true)
			bar.reponer();
	}
}