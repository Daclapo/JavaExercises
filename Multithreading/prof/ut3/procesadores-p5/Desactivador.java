package procesadores;

import java.util.concurrent.locks.*;

//La clase Desactivador usa un cerrojo para implementar la RC, y una única variable 
//de condición para que los hilos calculadores se queden bloqueados, que consultan todos 
//los hilos calculadores y la modifica la clase Aplicación mediante los métodos que llama.
public class Desactivador {
	private int numDesactivacionesPendientes;
	private Lock cerrojo;
	private Condition condicion;
	
	
		public Desactivador() {
		numDesactivacionesPendientes = 0;
		cerrojo = new ReentrantLock();
		condicion = cerrojo.newCondition();
	}
	
	//Método que llama el programa principal para suspender un hilo
	public void desactivarHilo() {
		try {
			cerrojo.lock();
			numDesactivacionesPendientes++;
		}
		finally {
			cerrojo.unlock();
		}
	}
	
	//Método que llama el programa principal para reanudar los hilos que están suspendidos
	public void reactivarTodos() {
		try {
			cerrojo.lock();
			numDesactivacionesPendientes = 0;
			condicion.signalAll();
		}
		finally {
			cerrojo.unlock();
		}
	}
	
	//Método bloqueante que usan los hilos calculadores para saber si se tienen que suspender
	//si antes se ha desactivado el hilo, en caso contrario no hace nada.
	//Del bloqueo se sale cuando desde el programa principal se reactiven todos lo hilos.
	
	
	
	public void consultarDesactivacion() throws InterruptedException {
		try {
			cerrojo.lock();
			while (numDesactivacionesPendientes > 0) {
				numDesactivacionesPendientes--;
				condicion.await();
			}
		}
		finally {
			cerrojo.unlock();
		}
	}
}
