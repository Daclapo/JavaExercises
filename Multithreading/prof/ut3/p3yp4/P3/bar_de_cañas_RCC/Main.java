package bar_de_cañas_RCC;

import bar_de_cañas.Bar;
import bar_de_cañas.Cliente;

public class Main {
	
	public static final int NUM_CLIENTES = 100;
	public static final int MAX_CLIENTES = 50;

	public static void main(String[] args) throws InterruptedException{
	   
		Bar bar =new Bar(MAX_CLIENTES);
		Thread[] clientes=new Thread[NUM_CLIENTES];
		int quedan;
		
		for (int i = 0; i < NUM_CLIENTES; i++) {
			clientes[i] = new Thread (new Cliente(bar));
			clientes[i].start();
		}
		quedan=clientes.length-1;
		do {
			try {
				clientes[quedan].join();
				quedan--;
			} catch (InterruptedException e) {}
		} while (quedan>0);
		
		}
	}

