package ejemplo_hilos_1A;


public class Ejemplo1A {
public static void main (String[] args) {
Thread hilo;

for (int i = 0; i < 10; i++) {
	hilo = new Trabajador(i);  
	hilo.start();
	}
System.out.println("Fin del main");
}
}
