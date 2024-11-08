package bar_de_cañas_cerrojo_VC;

import java.util.concurrent.locks.*;

//Para entrar en la RC se cierra el cerrojo y se sale abriendo el cerrojo, y utilizamos una variable
//de condición para que los hilos se queden bloqueados cuando no se cumple la condición, así se evita
//la espera activa.
//Tiene el mismo problema de ineficiencia que los monitores (no se puede entrar y salir a la vez
//utilizando el mismo cerrojo).
public class Bar {

	private int caben; // Número de clientes que pueden entrar al local
	private Lock cerrojoClientes; // Cerrojo para implementar la RC
	private Condition esperarEntrar;// Variable de condición para esperar
	
	public Bar (int aforo ) {
		caben = aforo;
		cerrojoClientes = new ReentrantLock();
		esperarEntrar = cerrojoClientes.newCondition();
		}
	
	public void entrar() throws InterruptedException{
		cerrojoClientes.lock();   // Inicio de una RC para que el acceso a caben
		                        // se haga en exclusión mutua
		try {
		while ( caben == 0 )    // Mientras no quepan más clientes
		esperarEntrar.await();  // Esperar a que avisen
		caben--;                // Decrementar la cuenta de clientes que caben en el local
		} finally {
			cerrojoClientes.unlock(); // Fin de la RC
		}
		abrirPuertaE();         // Esta acción puede quedar fuera de la RC pues no
		                        // afecta a la sincronización de caben
	}
		
		
	public void salir() throws InterruptedException{
		abrirPuertaS(); // Esta acción puede quedar fuera de la RC pues no
		// afecta a la sincronización de caben
		cerrojoClientes.lock();// Inicio de la RC para el acceso a caben se haga
		// en exclusión mutua
		try {
		caben++; // Incrementar el nº de clientes que caben en el local
		esperarEntrar.signal(); // Avisar a un posible cliente que estuviera esperando
		// para entrar
		} finally {
		cerrojoClientes.unlock(); // Fin de la RC
		}
	}
	
	private void abrirPuertaE() {
		System.out.println("Se puede entrar");
	}
	
	private void abrirPuertaS() {
		System.out.println("Se puede salir");
	}
	
}

