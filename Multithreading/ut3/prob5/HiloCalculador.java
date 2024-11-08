package org.ejercicios.ut3.prob5;


import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class HiloCalculador extends Thread {
	private Calculadora calculadora;
	private boolean suspendido;
	private Lock lock;
	private Condition reanudable;

	public HiloCalculador() {
		calculadora = new Calculadora();
		suspendido = false;
		lock = new ReentrantLock();
		reanudable = lock.newCondition();
	}

	@Override
	public void run() {
		boolean terminar = false;
		do {
			lock.lock();
			try {
				while (suspendido) {
					reanudable.await();
				}
				terminar = calculadora.calcularParte();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				lock.unlock();
			}
		} while (!terminar);
	}

	public void suspender() {
		lock.lock();
		try {
			suspendido = true;
		} finally {
			lock.unlock();
		}
	}

	public void reanudar() {
		lock.lock();
		try {
			suspendido = false;
			reanudable.signal();
		} finally {
			lock.unlock();
		}
	}

	public boolean estaSuspendido() {
		return suspendido;
	}
}