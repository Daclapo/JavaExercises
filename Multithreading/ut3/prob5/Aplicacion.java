package org.ejercicios.ut3.prob5;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class Aplicacion {
	private static final int NUM_CPU = 10;
	private static List<HiloCalculador> hilosCalculadores = new ArrayList<>();
	private static Lock lock = new ReentrantLock();
	private static Condition todosActivos = lock.newCondition();
	private static int hilosSuspendidos = 0;

	public static void main(String[] args) {
		// Crear y lanzar los hilos calculadores
		for (int i = 0; i < NUM_CPU; i++) {
			HiloCalculador hilo = new HiloCalculador();
			hilosCalculadores.add(hilo);
			hilo.start();
		}
		// Menú de usuario
		int opcion;
		do {
			opcion = pedirOpcion();
			switch (opcion) {
				case 1:
					suspenderHilo();
					break;
				case 2:
					reanudarHilos();
					break;
			}
		} while (opcion != 3);
	}

	private static int pedirOpcion() {
		// Implementación del metodo para pedir la opción al usuario.
		Scanner scanner = new Scanner(System.in);
		System.out.println("Menú:");
		System.out.println("1. Suspender un hilo");
		System.out.println("2. Reanudar todos los hilos");
		System.out.println("3. Salir");
		System.out.print("Elija una opción: ");
		return scanner.nextInt();
	}

	private static void suspenderHilo() {
		lock.lock();
		try {
			if (hilosSuspendidos < NUM_CPU) {
				HiloCalculador hiloASuspender = hilosCalculadores.get(hilosSuspendidos);
				hiloASuspender.suspender();
				hilosSuspendidos++;
			} else {
				System.out.println("No se pueden suspender más hilos.");
			}
		} finally {
			lock.unlock();
		}
	}

	private static void reanudarHilos() {
		lock.lock();
		try {
			if (hilosSuspendidos > 0) {
				for (HiloCalculador hilo : hilosCalculadores) {
					if (hilo.estaSuspendido()) {
						hilo.reanudar();
						hilosSuspendidos--;
					}
				}
				todosActivos.signalAll();
			} else {
				System.out.println("Todos los hilos ya están activos.");
			}
		} finally {
			lock.unlock();
		}
	}
}