package banco;


public class TestBanco {
	
	public static void main(String[] args) {
		final Cuenta cc = new Cuenta(0);
		final int N = 10;
		final Thread[] clientes = new Thread[N];
		
		for (int i = 0; i < clientes.length; i++)
			clientes[i] = new Thread (new Cliente(cc));
		
		for (Thread cliente : clientes)
			cliente.start();
		
		for (Thread cliente : clientes)
			try {
				cliente.join();
			} catch (InterruptedException ignorada) { }
		System.out.println("Saldo: " + cc.getSaldo());
	}
}