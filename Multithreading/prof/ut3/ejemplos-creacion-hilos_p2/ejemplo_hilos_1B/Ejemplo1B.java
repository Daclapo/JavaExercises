package ejemplo_hilos_1B;


public class Ejemplo1B {
	public static void main(String[] args) {
		Thread hilo;
		for (int i = 0; i < 10; i++) {
			hilo = new Thread(new Trabajador(i));  hilo.start();
		}
		System.out.println("Fin del main");
	}
}
