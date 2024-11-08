package org.ejercicios.ut3.prob6;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Condition;

public class Cuenta {
	private final String numCuenta = "ES0XXXXXXXXXXX";
	private int saldo;
	private final Lock cerrojo;
	private final Lock embargoLock;
	private final Condition embargoCondicion;
	private boolean embargada;

	public Cuenta(int n) {
		saldo = n;
		cerrojo = new ReentrantLock();
		embargoLock = new ReentrantLock();
		embargoCondicion = embargoLock.newCondition();
		embargada = false;
	}

	public void ingreso(int cuanto) {
		cerrojo.lock();
		try {
			// Esperar si la cuenta está embargada
			embargoLock.lock();
			try {
				while (embargada) {
					embargoCondicion.await();  // Espera hasta que no esté embargada
				}
			} finally {
				embargoLock.unlock();
			}

			saldo = saldo + cuanto;
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();  // Restaurar la interrupción
		} finally {
			cerrojo.unlock();
		}
	}

	public void reintegro(int cuanto) throws SaldoInsuficienteException {
		cerrojo.lock();
		try {
			embargoLock.lock();
			try {
				while (embargada) {
					embargoCondicion.await();
				}
			} finally {
				embargoLock.unlock();
			}

			if (saldo < cuanto) {
				throw new SaldoInsuficienteException();
			} else {
				saldo = saldo - cuanto;
			}
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		} finally {
			cerrojo.unlock();
		}
	}

	public void transferencia(int cuanto, Cuenta destino) throws SaldoInsuficienteException {
		Lock primero = cerrojo;
		Lock segundo = destino.cerrojo;

		if (System.identityHashCode(primero) > System.identityHashCode(segundo)) {
			primero = destino.cerrojo;
			segundo = cerrojo;
		}

		primero.lock();
		segundo.lock();

		try {
			embargoLock.lock();
			try {
				while (embargada) {
					embargoCondicion.await();
				}
			} finally {
				embargoLock.unlock();
			}

			if (saldo < cuanto) {
				throw new SaldoInsuficienteException();
			} else {
				saldo = saldo - cuanto;
				destino.saldo = destino.saldo + cuanto;
			}
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		} finally {
			primero.unlock();
			segundo.unlock();
		}
	}

	public void embargar() {
		embargoLock.lock();
		try {
			embargada = true;
			System.out.println("Cuenta embargada. Todos los hilos están detenidos.");
		} finally {
			embargoLock.unlock();
		}
	}

	public void desembargar() {
		embargoLock.lock();
		try {
			embargada = false;
			embargoCondicion.signalAll();
			System.out.println("Cuenta desembargada. Los hilos pueden continuar.");
		} finally {
			embargoLock.unlock();
		}
	}

	public int getSaldo() {
		cerrojo.lock();
		try {
			return saldo;
		} finally {
			cerrojo.unlock();
		}
	}
}


