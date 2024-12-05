package es.davidclarkson.programas;

import es.davidclarkson.entities.Film;
import es.davidclarkson.entities.Inventory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.Scanner;

public class InformePelicula {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		String id;

		do {
			System.out.println("\n\n_________________________________\n   Informe de Situación de Película  \n_________________________________\n");
			System.out.print("Introduzca el id de la película (film_id) que desee buscar: ");
			id = sc.nextLine();

			if (!id.equals("0") && !id.isEmpty()) {
				System.out.println("\n\nInforme de Situación de Película:");
				System.out.println("\nDatos de la Película:\n" + getDatosPelicula(id));
				System.out.println("\nCategorías de la Película:\n" + getCategoriasPelicula(id));
				System.out.println("\nIdiomas:\n" + getIdiomasPelicula(id));
				System.out.println("\nActores de la Película:\n" + getActoresPelicula(id));
				System.out.println("\nCopias Disponibles:\n" + getCopiasInventario(id));
			}
		} while (!id.equals("0"));

		System.out.println("Programa terminado.");
	}

	private static String getDatosPelicula(String id) {
		StringBuilder ret = new StringBuilder();

		try (EntityManagerFactory factory = Persistence.createEntityManagerFactory("sakila");
		     EntityManager em = factory.createEntityManager()) {

			Film film = em.find(Film.class, Short.parseShort(id));

			if (film != null) {
				ret.append("ID: ").append(film.getId()).append("\n")
						.append("Título: ").append(film.getTitle()).append("\n")
						.append("Descripción: ").append(film.getDescription()).append("\n")
						.append("Año de Estreno: ").append(film.getReleaseYear()).append("\n")
						.append("Duración del Alquiler: ").append(film.getRentalDuration()).append("\n")
						.append("Precio del Alquiler: ").append(film.getRentalRate()).append("\n")
						.append("Duración: ").append(film.getLength()).append("\n")
						.append("Coste de Reemplazo: ").append(film.getReplacementCost()).append("\n")
						.append("Clasificación: ").append(film.getRating()).append("\n")
						.append("Características Especiales: ").append(film.getSpecialFeatures());
			} else {
				ret.append("No se encontró ninguna película con ID: ").append(id);
			}
		} catch (NumberFormatException e) {
			ret.append("ID de película inválido.");
		}

		return ret.toString();
	}

	private static String getCategoriasPelicula(String id) {
		StringBuilder ret = new StringBuilder();

		try (EntityManagerFactory factory = Persistence.createEntityManagerFactory("sakila");
		     EntityManager em = factory.createEntityManager()) {

			Film film = em.find(Film.class, Short.parseShort(id));

			if (film != null) {
				film.getFilmCateories().forEach(category ->
						ret.append("- ").append(category.getName()).append("\n")
				);
			} else {
				ret.append("No se encontró ninguna película con ID: ").append(id);
			}
		} catch (NumberFormatException e) {
			ret.append("ID de película inválido.");
		}

		return ret.toString();
	}

	private static String getIdiomasPelicula(String id) {
		StringBuilder ret = new StringBuilder();

		try (EntityManagerFactory factory = Persistence.createEntityManagerFactory("sakila");
		     EntityManager em = factory.createEntityManager()) {

			Film film = em.find(Film.class, Short.parseShort(id));

			if (film != null) {
				ret.append("Idioma Original: ").append(film.getOriginalLanguage() != null ? film.getOriginalLanguage().getName() : "No disponible").append("\n")
						.append("Idioma: ").append(film.getLanguage().getName());
			} else {
				ret.append("No se encontró ninguna película con ID: ").append(id);
			}
		} catch (NumberFormatException e) {
			ret.append("ID de película inválido.");
		}

		return ret.toString();
	}


	// No esta correcto, ya que todavia no se puede acced
	private static String getActoresPelicula(String id) {
		StringBuilder ret = new StringBuilder();

/*
		try (EntityManagerFactory factory = Persistence.createEntityManagerFactory("sakila");
		     EntityManager em = factory.createEntityManager()) {

			Film film = em.find(Film.class, Short.parseShort(id));

			if (film != null) {
				film.getFilmActors().forEach(actor ->
						ret.append("- ").append(actor.ge()).append(" ").append(actor.getLastName()).append("\n")
				);
			} else {
				ret.append("No se encontró ninguna película con ID: ").append(id);
			}
		} catch (NumberFormatException e) {
			ret.append("ID de película inválido.");
		}
*/

		return ret.toString();
	}

	private static String getCopiasInventario(String id) {
		StringBuilder ret = new StringBuilder();

		try (EntityManagerFactory factory = Persistence.createEntityManagerFactory("sakila");
		     EntityManager em = factory.createEntityManager()) {

			Film film = em.find(Film.class, Short.parseShort(id));

			if (film != null) {
				for (Inventory inventory : film.getInventories()) {
					ret.append("Copia ID: ").append(inventory.getId()).append("\n")
							.append("Tienda: ").append(inventory.getStore().getId()).append("\n")
							.append("Dirección: ").append(inventory.getStore().getAddress().getAddress()).append(", ")
							.append(inventory.getStore().getAddress().getCity().getName()).append(", ")
							.append(inventory.getStore().getAddress().getCity().getCountry().getName()).append("\n\n");
				}
			} else {
				ret.append("No se encontró ninguna película con ID: ").append(id);
			}
		} catch (NumberFormatException e) {
			ret.append("ID de película inválido.");
		}

		return ret.toString();
	}
}
