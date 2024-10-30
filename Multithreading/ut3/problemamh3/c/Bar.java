package org.ejercicios.ut3.problemamh3.c;

public class Bar {
	private int aforoMaximo;
	private int clientesDentro = 0;

	public Bar(int aforoMaximo) {
		this.aforoMaximo = aforoMaximo;
	}


	public synchronized void entrar() {
		while (clientesDentro >= aforoMaximo) {
			try {
				System.out.println(Thread.currentThread().getName() + " esperando para entrar. Aforo completo.");
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		clientesDentro++;
		System.out.println(Thread.currentThread().getName() + " ha entrado. Clientes dentro: " + clientesDentro);

		abrirPuertaE();
	}

	public synchronized void salir() {
		clientesDentro--;
		System.out.println(Thread.currentThread().getName() + " ha salido. Clientes dentro: " + clientesDentro);

		abrirPuertaS();

		notifyAll();
	}

	private void abrirPuertaE() {
		System.out.println("Puerta de entrada abierta. Se puede entrar.");
	}

	private void abrirPuertaS() {
		System.out.println("Puerta de salida abierta. Se puede salir.");
	}
}

