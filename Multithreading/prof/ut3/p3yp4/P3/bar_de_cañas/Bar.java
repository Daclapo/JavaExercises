package bar_de_cañas;

public class Bar {

	private int caben; // Número de clientes que pueden entrar al local
	
	public Bar (int aforo ) {
		caben = aforo;
	}
	
	public void entrar() {
		while ( caben == 0 ){ // Mientras no quepan más clientes
			// No hacer nada concreto, con lo que el bucle se repite
		}
		caben--;
		abrirPuertaE();
	}
	
	public void salir() {
		abrirPuertaS();
		caben++; // Incrementar el nº de clientes que caben en el local
	}
	
	private void abrirPuertaE() {
		System.out.println("Se puede entrar");
	}
	
	private void abrirPuertaS() {
		System.out.println("Se puede salir");
	}
	
}