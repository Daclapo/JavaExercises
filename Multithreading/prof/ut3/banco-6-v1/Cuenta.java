package banco;


import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


	public class Cuenta {
		private final String numCuenta = "ES0XXXXXXXXXXX";
		private int saldo;
		private Lock cerrojo;
		
		public Cuenta (int n) {
			saldo = n;
			cerrojo = new ReentrantLock();
			}
		
		public void ingreso ( int cuánto ) {
			cerrojo.lock();
			try {
				saldo = saldo + cuánto;
			}
			finally { cerrojo.unlock(); }
		}
		
		public void reintegro ( int cuánto ) throws SaldoInsuficienteException {
			cerrojo.lock();
			try {
				if ( saldo < cuánto )
					throw new SaldoInsuficienteException();
				else
					saldo = saldo - cuánto;
			}catch (SaldoInsuficienteException e){
				System.out.println("Saldo insuficiente");
				
			}
			finally { cerrojo.unlock(); }
		}
		
		public void transferencia ( int cuánto, Cuenta destino ) throws SaldoInsuficienteException {
			// Bloquear la cuenta actual y la de destino siempre en un orden específico
			if ( numCuenta.compareTo ( destino.numCuenta ) < 0 ) {
				cerrojo.lock();
				destino.cerrojo.lock();
			}
			else {
				destino.cerrojo.lock();
				cerrojo.lock();
			}
			try {
				if ( saldo < cuánto )
					throw new SaldoInsuficienteException();
				else {
					saldo = saldo - cuánto;
					destino.saldo = destino.saldo + cuánto;	}
			}catch (SaldoInsuficienteException e){
					System.out.println("Saldo insuficiente");
				
			}finally {
				cerrojo.unlock();
				destino.cerrojo.unlock();
			}	
		}
		
		public int getSaldo() {
			int saldoActual;
			cerrojo.lock();
			try {
			saldoActual= saldo;
			}
			finally {
			cerrojo.unlock();
			}
			return saldoActual;
			}
	}
