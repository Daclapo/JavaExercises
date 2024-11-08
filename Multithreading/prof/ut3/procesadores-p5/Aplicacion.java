package procesadores;

import java.io.IOException;
import java.util.*;

//Una empresa utiliza un ordenador con un número de procesadores para parelelizar 
//aplicaciones de cálculo intensivo.
//La clase Aplicación lanzará los hilos, y pregunta al usuario qué quiere hacer

//La clase HiloCalculador implementa los hilos, y estarán en bucle calculando parte a parte
//(hasta que nos devuelva TRUE.

//La clase Aplicación indica a los hilos qué tienen que hacer, para eso instanciamos la clase
//Desactivador en Aplicación y la comparten todos los hilos.



public class Aplicacion {
	private static final int NUM_CPU = 5;
	
	public static void main(String[] args) throws IOException{
		int opcion;
		Desactivador desactivador = new Desactivador();
	
		for (int i = 0; i < NUM_CPU; i++) {
			Thread hilo  = new Thread(new HiloCalculador (desactivador));
			hilo.start();
		}
						
		do {
			opcion = pedirOpcion();
			switch(opcion) {
			case 1:
				// Suspender un hilo calculador
				desactivador.desactivarHilo();
				break;
			case 2:
				// Reanudar todos los hilos calculadores
				desactivador.reactivarTodos();
				break;
			}
		} while (opcion!=3);
	}
	
		
	private static int pedirOpcion() {
		try (Scanner teclado = new Scanner( System.in)) {
			System.out.println("Opción 1 si desea suspender el hilo calculador, y opción 2 para reanudar todos los hilos calculadores: ");

			while (teclado.hasNextInt()) {
				int opcion = teclado.nextInt();
			    System.out.println("Ha introducido la opción: " + opcion);
			}
			teclado.close();
		}
		return pedirOpcion();
	}
	
	}
