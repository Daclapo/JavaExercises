package problema1;

//Como nos piden máxima concurrencia debemos incluir en la región crítica 
//únicamente la instrucción que tiene los problemas de concurrencia, es decir
//la del incremento. Ya que si incluyésemos el bucle for completo, cuando un hilo
//entre en la región crítica el resto de los hilos que darían bloqueados y es como si fuera
//una ejecución secuencial (sin concurrencia).

public class HiloCuenta2 implements Runnable{
	problema1.Contador contador;
	
	public HiloCuenta2 (problema1.Contador contador){
		this.contador=contador;
	}
	
	public void run() {
		for (int i = 0; i < 5; i++)
			synchronized (contador){
				contador.incrementar();
		}
	}
}