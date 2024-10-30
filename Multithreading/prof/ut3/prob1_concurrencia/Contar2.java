package org.ejercicios.prof.ut3.concurrencia.problema1.problema1;


public class Contar2 {
	//Para que la clase Principal pueda esperar a que terminen todos los hilos,
	//debe invocar al método join() de cada hilo.
	//Puesto que se está reutilizando la variable hilo en cada instanciación de los hilos 
	//HiloCuenta, se necesita almacenar su valor por ejemplo en un array. De esta forma 
	//al terminar la creación de los hilos habrá que realizar otro bucle para recorrer el array 
	//y ejecutar join() sobre cada elemento del array.
	
	//Como el método join() puede producir una excepción sin haber terminado en realidad el 
	//hilo, por eso es capturada sin hacer nada para así poder relanzar el join().
	//
	
   public static void main(String[] args)
   {
	problema1.Contador contador=new problema1.Contador();
	Thread[] hilos=new Thread[10];
	int quedan;
	
	for (int i = 0; i < 10; i++) {
		hilos[i] = new Thread (new HiloCuenta(contador));
		hilos[i].start();
	}
	quedan=hilos.length-1;
	do {
		try {
			hilos[quedan].join();
			quedan--;
		} catch (InterruptedException e) {}
	} while (quedan>0);
	System.out.println(contador.valor());
	}
}