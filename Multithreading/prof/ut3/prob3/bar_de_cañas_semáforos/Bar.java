package org.ejercicios.prof.ut3.prob3.Problema3.Problema3.bar_de_cañas_semáforos;

import java.util.concurrent.*;

public class Bar {

	private Semaphore caben;    // Controla el nº de clientes que caben en el local
		
	public Bar (int aforo ) {
		caben = new Semaphore (aforo); // Semáforo con valor inicial el nº
		                               // max de clientes que caben en el local
		}
	public void entrar() throws InterruptedException {
		caben.acquire();              // Decrementar semáforo. Si llega a 0 (local lleno)
		                              // bloquea al hilo que lo ejecuta
		abrirPuertaE();
		}
		public void salir() throws InterruptedException {
		abrirPuertaS();
		caben.release();             // Incrementar semáforo, despertando a quien estuviera
		                             // bloqueado en un acquire()
		}
	
	
	private void abrirPuertaE() {
		System.out.println("Se puede entrar");
	}
	
	private void abrirPuertaS() {
		System.out.println("Se puede salir");
	}
	
}

