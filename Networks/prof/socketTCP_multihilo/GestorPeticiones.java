package socketTCP_multihilo;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class GestorPeticiones implements Runnable{
	private Socket socket;
	private OutputStream os;
	
    public GestorPeticiones (Socket socket){
            this.socket=socket;
    }
	
	
	
    public void run(){
        try {
              realizarProceso();  
        } catch (IOException e) {
        }
	}	

	public void realizarProceso () throws IOException {
	
		os = this.socket.getOutputStream();
		Random generadorNumerosAleatorios = new Random();
		int tiempoEspera = generadorNumerosAleatorios.nextInt(60);
		try {
            TimeUnit.SECONDS.sleep(tiempoEspera);
            os.write (tiempoEspera); 
      	} catch (InterruptedException e) {
      		e.printStackTrace();
        } finally {
        	os.close();	
        }
}

}
