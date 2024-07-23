package es.jllopezalvarez.programacion.ut11.ejercicios.ejercicio2021.figuras;

public abstract class Paralelogramo extends Figura {
	private static final int NUM_LADOS = 4;

	private double base;
	private double altura;

	public Paralelogramo(double base, double altura) {
		this.base = base;
		this.altura = altura;
	}

	@Override
	public double perimetro() {

		return (this.getBase() + this.altura) * 2;
	}

	@Override
	public double area() {
		return this.getBase() * this.altura;
	}

	double getBase() {
		return base;
	}

	@Override
	public int getNumLados() {
		return NUM_LADOS;
	}

	@Override
	public void escalar(double factor) {
		this.base *= factor;
		this.altura *= factor;
	}

}
