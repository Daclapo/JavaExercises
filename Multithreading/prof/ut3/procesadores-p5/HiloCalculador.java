package procesadores;


public class HiloCalculador implements Runnable {
	
	Desactivador  desactivador;
	
	public HiloCalculador(Desactivador desactivador) {
		this.desactivador = desactivador;
		}

	public void run() {
		boolean terminar;
		Calculadora calculadora = new Calculadora();
		do {
			try {
				desactivador.consultarDesactivacion();//Usado por los hilos calculadores para saber si se tienen que suspender
			 //Cuando un hilo invoca este método puede quedarse bloqueado
			 //en el caso de que antes se haya llamado desde el main a desactivarHilo() 
			
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			terminar = calculadora.calcularParte();
		} while (! terminar);
	}
}
