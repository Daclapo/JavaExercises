package es.jllopezalvarez.programacion.ut11.ejercicios.ejercicio13;

public interface Vehiculo {
	int VELOCIDAD_MAXIMA = 120;

	void frenar(int cuanto);

	void acelerar(int cuanto);

	int getNumPlazas();
}
