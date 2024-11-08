package org.ejercicios.ut3.prob5;

class Calculadora {
	public boolean calcularParte() {
		// Implementación del cálculo que realiza la clase Calculadora
		System.out.println("Realizando cálculos...");

		int resultado = 0;
		for (int i = 0; i < 1000000; i++) {
			resultado += i;
		}
		System.out.println("Cálculo completado. Resultado: " + resultado);
		return true; // Devuelve true si el cálculo ha terminado
	}
}


/*

class Calculadora {
	private int totalParts;
	private int currentPart;
	private int[] data;

	public Calculadora(int totalParts, int[] data) {
		this.totalParts = totalParts;
		this.currentPart = 0;
		this.data = data;
	}

	public boolean calcularParte() {
		System.out.println("Realizando cálculos para la parte " + currentPart + " de " + totalParts + "...");

		int partSize = data.length / totalParts;
		int start = currentPart * partSize;
		int end = (currentPart + 1) * partSize;
		if (currentPart == totalParts - 1) {
			end = data.length;
		}

		int resultado = 0;
		for (int i = start; i < end; i++) {
			resultado += data[i];
		}
		System.out.println("Cálculo completado para la parte " + currentPart + ". Resultado: " + resultado);

		currentPart++;
		return currentPart >= totalParts;
	}
}*/
