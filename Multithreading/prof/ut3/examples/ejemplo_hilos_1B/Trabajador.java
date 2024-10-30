package org.ejercicios.prof.ut3.Ejemplos.Ejemplos.ejemplo_hilos_1B;


public class Trabajador implements Runnable
{
	private int num;

	public Trabajador(int num) {
		this.num = num;
}	

public void run() {
System.out.println("Soy el trabajador " + num);
}
}

