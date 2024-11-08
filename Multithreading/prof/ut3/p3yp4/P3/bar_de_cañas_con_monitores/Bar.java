package bar_de_cañas_con_monitores;

//Cuando casi todas las instrucciones de un método se ejecutan dentro de una RCC,
//se puede implementar usando MONITORES. En los monitores todos los métodos de una clase
//se ejecutan en exclusión mutua (declarados como synchronized).
//En este caso, abrirPuertaE() y cerrarPuertaS() se jecutarán en exclusión mutua, pero 
//aún más ineficiente.
public class Bar {

	private int caben; // Número de clientes que pueden entrar al local
	
	public Bar (int aforo ) {
		caben = aforo;
	}
	
	public synchronized void entrar() throws InterruptedException{
		while ( caben == 0 ) // Mientras no quepan más clientes
		wait(); // Esperar a que avisen
		caben--;
		abrirPuertaE();
		}
		public synchronized void salir() throws InterruptedException{
		abrirPuertaS();
		caben++; // Incrementar el nº de clientes que caben en el local
		notifyAll(); // Avisar a un posible cliente que estuviera esperando
		}
	private void abrirPuertaE() {
		System.out.println("Se puede entrar");
	}
	
	private void abrirPuertaS() {
		System.out.println("Se puede salir");
	}
	
}
