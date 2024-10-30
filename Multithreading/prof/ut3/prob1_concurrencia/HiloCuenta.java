package org.ejercicios.prof.ut3.concurrencia.problema1.problema1;


public class HiloCuenta implements Runnable{
	problema1.Contador contador;
	
	public HiloCuenta (problema1.Contador contador){
		this.contador=contador;
	}

	public void run() {
		//synchronized (contador){
		contador.incrementar();
		System.out.println(contador.valor());
		//}
	}
}