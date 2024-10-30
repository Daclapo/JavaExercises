package org.ejercicios.prof.ut3.concurrencia.problema1.problema1;

//Cuando se inicia un programa en Java, la JVM crea un hilo principal:
//1. El hilo se encarga de invocar el método main de la clase que se comienza 
//a ejecutar.
//2. El hilo termina cuando se acaba de ejecutar el método main,
//3. Como el hilo principal crea otros hilos, estos comenzarán su ejecuación de 
//forma concurrente.
//4. Solo cuando no queda ningún hilo activo, es cuando termina el programa.

//La clase para conseguir la concurrencia es Thread:
//1. Dispone de un método start() que ocasiona la ejecuación del código que tenga
//dentro de su método run() en un nuevo hilo.

public class Contar {
	public static void main(String[] args) {																						
		problema1.Contador contador=new problema1.Contador();
		for (int i = 0; i < 10; i++) {
			//Thread hilo = new Thread (new problema1.HiloCuenta(contador));
			//hilo.start();
		}
	}
}