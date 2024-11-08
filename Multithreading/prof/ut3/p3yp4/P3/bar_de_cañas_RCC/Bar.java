package bar_de_cañas_RCC;

//Solución CON RCC: Cuando un cliente quiere salir, como las instrucciones están dentro
//de synchronized, el otro cliente no puede entrar.
//Aunque la solución es correcta no es eficiente porque cuando el local no está lleno
//los clientes deberían poder salir mientras otros van entrando.
public class Bar {

	private int caben; // Número de clientes que pueden entrar al local
	
	public Bar (int aforo ) {
		caben = aforo;
	}
	
	public void entrar() throws InterruptedException {
		synchronized (this) {
		while ( caben == 0 ) // Mientras no quepan más clientes
		this.wait();         // Esperar a que avisen
		caben--;
		}
		abrirPuertaE();
	}
	
	public void salir() throws InterruptedException {
		abrirPuertaS();
		synchronized (this) {
		caben++;            // Incrementar el nº de clientes que caben en el local
		this.notifyAll();   // Avisar a un posible cliente que estuviera esperando
		}
	}
	private void abrirPuertaE() {
		System.out.println("Se puede entrar");
	}
	
	private void abrirPuertaS() {
		System.out.println("Se puede salir");
	}
	
}