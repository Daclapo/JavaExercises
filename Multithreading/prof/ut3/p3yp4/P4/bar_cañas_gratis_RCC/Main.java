package bar_cañas_gratis_RCC;


public class Main {
	
	public static final int NUM_CLIENTES = 20;
	public static final int MAX_CLIENTES = 25;
	public static final int MAX_CAÑAS = 30;
	

	public static void main(String[] args) throws InterruptedException{
	   
		Bar bar =new Bar(MAX_CAÑAS);
		int cañas = MAX_CAÑAS;
			
		Thread camarero = new Thread(new Camarero(bar));
        camarero.start();
		
		Thread[] clientes=new Thread[NUM_CLIENTES];
		int quedan;
		
		
		for (int i = 0; i < NUM_CLIENTES; i++) {
			clientes[i] = new Thread (new Cliente(bar, cañas));
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

