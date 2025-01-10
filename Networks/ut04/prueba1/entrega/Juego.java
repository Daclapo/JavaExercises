package es.davidclarkson.practicas.ut04.prueba1.entrega;

public class Juego {
	private final int nAAdivinar;
	private String ganador;
	private boolean acabado;

	public Juego(int nAAdivinar) {
		this.nAAdivinar = nAAdivinar;
		this.acabado = false;
		this.ganador = "";
	}


	public synchronized String compIntento (int intento, String idJugador){

		if (intento == nAAdivinar) {
			return idJugador;
		}
		return "";
	}


}
