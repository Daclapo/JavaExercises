package es.davidclarkson.programas;

import es.davidclarkson.entities.Film;
import es.davidclarkson.entities.Inventory;
import es.davidclarkson.entities.Language;
import es.davidclarkson.entities.Actor;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.Scanner;

public class InformePeliculaMejorado {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		String id;

		try (EntityManagerFactory factory = Persistence.createEntityManagerFactory("sakila");
		     EntityManager em = factory.createEntityManager()) {

			do {
				System.out.println("\n\n______________________________________\n   Informe de Situación de Película  \n______________________________________\n");
				System.out.print("Introduzca el id de la película (film_id) que desee buscar: ");
				id = sc.nextLine();

				if (!id.equals("0") && !id.isEmpty()) {
					try {
						Film film = em.find(Film.class, Short.parseShort(id));

						if (film != null) {
							System.out.println("\n\nInforme de Situación de Película:");
							System.out.println("\n_____________________\nDatos de la Película:\n" + getDatosPelicula(film));
							System.out.println("\n_____________________\nCategorías de la Película:\n" + getCategoriasPelicula(film));
							System.out.println("\n_____________________\nIdiomas:\n" + getIdiomasPelicula(film));
							System.out.println("\n_____________________\nActores de la Película:\n" + getActoresPelicula(film));
							System.out.println("\n_____________________\nCopias Disponibles:\n" + getCopiasInventario(film));
						} else {
							System.out.println("No se ha encontrado ninguna película con ID: " + id);
						}
					} catch (NumberFormatException e) {
						System.out.println("ID de película inválido.");
					}
				}
			} while (!id.equals("0"));

			System.out.println("Programa terminado.");
		}
	}

	private static String getDatosPelicula(Film film) {
		StringBuilder ret = new StringBuilder();
		ret.append("ID:\t\t\t\t\t\t").append(film.getId()).append("\n")
				.append("Título:\t\t\t\t\t").append(film.getTitle()).append("\n")
				.append("Descripción:\t\t\t").append(film.getDescription()).append("\n")
				.append("Año de Estreno:\t\t\t").append(film.getReleaseYear()).append("\n")
				.append("Duración del Alquiler:\t").append(film.getRentalDuration()).append("\n")
				.append("Precio del Alquiler:\t").append(film.getRentalRate()).append("\n")
				.append("Duración:\t\t\t\t").append(film.getLength()).append("\n")
				.append("Coste de Reemplazo:\t\t").append(film.getReplacementCost()).append("\n")
				.append("Clasificación:\t\t\t").append(film.getRating()).append("\n")
				.append("Características Especiales:\t").append(film.getSpecialFeatures());
		return ret.toString();
	}

	private static String getCategoriasPelicula(Film film) {
		StringBuilder ret = new StringBuilder();
		for (var category : film.getFilmCateories()) {
			ret.append("- ").append(category.getName()).append("\n");
		}

		if (ret.isEmpty()) {
			ret.append("No existen categorías para esta película.\n");
		}

		return ret.toString();
	}

	private static String getIdiomasPelicula(Film film) {
		StringBuilder ret = new StringBuilder();

		Language originalLanguage = film.getOriginalLanguage();
		Language language = film.getLanguage();

		ret.append("Idioma Original:\t ");
		if (originalLanguage != null) {
			ret.append(originalLanguage.getName());
		} else {
			ret.append("No disponible");
		}
		ret.append("\n");

		ret.append("Idioma de la Copia:\t ");
		if (language != null) {
			ret.append(language.getName());
		} else {
			ret.append("No disponible");
		}

		return ret.toString();
	}

	private static String getActoresPelicula(Film film) {
		StringBuilder ret = new StringBuilder();

		for (Actor actor : film.getFilmActors()) {
			ret.append("- ").append(actor.getFirstName()).append(" ").append(actor.getLastName()).append("\n");
		}

		if (ret.isEmpty()) {
			ret.append("No existen actores para esta película.\n");
		}

		return ret.toString();
	}

	private static String getCopiasInventario(Film film) {
		StringBuilder ret = new StringBuilder();

		for (Inventory inventory : film.getInventories()) {
			ret.append("Copia ID:\t").append(inventory.getId()).append("\n")
					.append("Tienda:\t").append(inventory.getStore().getId()).append("\n")
					.append("Dirección:\t").append(inventory.getStore().getAddress().getAddress()).append(", ")
					.append(inventory.getStore().getAddress().getCity().getName()).append(", ")
					.append(inventory.getStore().getAddress().getCity().getCountry().getName()).append("\n\n");
		}

		if (ret.isEmpty()) {
			ret.append("No existen copias disponibles para esta película.\n");
		}

		return ret.toString();
	}
}
