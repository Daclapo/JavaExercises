package es.davidclarkson.practicas.ut04.prueba1.entrega2;

public class Juego {
	private final int numberToGuess;
	private boolean gameFinished = false;
	private String winner = null;

	// Contructor que inicializa (aleatoriamente) el numero que los clientes tendran que adivinar.
	public Juego() {
		this.numberToGuess = (int) (Math.random() * 100) + 1;
	}

	// Comprueba el numero que ha "adivinado/probado" el jugador y actualiza el juego si ha acertado.
	public synchronized String checkGuess(String playerName, int guess) {
		if (gameFinished) {
			return "Ha terminado el juego;\nHa ganador: " + winner;
		}

		// CUando se cumple esta condicion, el juego se acaba.
		if (guess == numberToGuess) {
			gameFinished = true;
			winner = playerName;
			return "Has adivinado el número.\nEl número era " + numberToGuess;
		} else if (guess < numberToGuess) {
			return "El numero demasiado bajo.";
		} else {
			return "El numero es demasiado alto.";
		}
	}

	// Getters y flag para saber si el juego se ha acabado.

	public boolean isGameFinished() {
		return gameFinished;
	}

	public int getNumberToGuess() {
		return numberToGuess;
	}

	public String getWinner() {
		return winner;
	}
}