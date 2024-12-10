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
			System.out.println("\n\n______________________________________\n   Informe de Situación de Película  \n______________________________________\n");
			System.out.print("Introduzca el id de la película (film_id) que desee buscar: ");
			id = sc.nextLine();

			if (!id.equals("0") && !id.isEmpty()) {
				System.out.println("\n\nInforme de Situación de Película:");
				System.out.println("\n_____________________\nDatos de la Película:\n" + getDatosPelicula(id));
				System.out.println("\n_____________________\nCategorías de la Película:\n" + getCategoriasPelicula(id));
				System.out.println("\n_____________________\nIdiomas:\n" + getIdiomasPelicula(id));
				System.out.println("\n_____________________\nActores de la Película:\n" + getActoresPelicula(id));
				System.out.println("\n_____________________\nCopias Disponibles:\n" + getCopiasInventario(id));
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
			} else {
				ret.append("No se ha encontrado ninguna película con ID: ").append(id);
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
				if (ret.isEmpty())
					ret.append("No existe un listado de actores para esta película.\n");
			} else {
				ret.append("No se ha encontrado ninguna película con ID: ").append(id);
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
				ret.append("Idioma Original:\t ").append(film.getOriginalLanguage() != null ? film.getOriginalLanguage().getName() : "No disponible").append("\n")
						.append("Idioma de la Copia:\t ").append(film.getLanguage().getName());
			} else {
				ret.append("No se ha encontrado ninguna película con ID: ").append(id);
			}
		} catch (NumberFormatException e) {
			ret.append("ID de película inválido.");
		}

		return ret.toString();
	}


	private static String getActoresPelicula(String id) {
		StringBuilder ret = new StringBuilder();

		try (EntityManagerFactory factory = Persistence.createEntityManagerFactory("sakila");
		     EntityManager em = factory.createEntityManager()) {

			Film film = em.find(Film.class, Short.parseShort(id));

			if (film != null) {
				film.getFilmActors().forEach(actor ->
						ret.append("- ").append(actor.getFirstName()).append(" ").append(actor.getLastName()).append("\n")
				);
			} else {
				ret.append("No se ha encontrado ninguna película con ID: ").append(id);
			}
		} catch (NumberFormatException e) {
			ret.append("ID de película inválido.");
		}

		return ret.toString();
	}

	private static String getCopiasInventario(String id) {
		StringBuilder ret = new StringBuilder();

		try (EntityManagerFactory factory = Persistence.createEntityManagerFactory("sakila");
		     EntityManager em = factory.createEntityManager()) {

			Film film = em.find(Film.class, Short.parseShort(id));

			if (film != null) {
				for (Inventory inventory : film.getInventories()) {
					ret.append("Copia ID:\t").append(inventory.getId()).append("\n")
							.append("Tienda:\t").append(inventory.getStore().getId()).append("\n")
							.append("Dirección:\t").append(inventory.getStore().getAddress().getAddress()).append(", ")
							.append(inventory.getStore().getAddress().getCity().getName()).append(", ")
							.append(inventory.getStore().getAddress().getCity().getCountry().getName()).append("\n\n");
				}
			} else {
				ret.append("No se ha encontrado ninguna película con ID: ").append(id);
			}
		} catch (NumberFormatException e) {
			ret.append("ID de película inválido.");
		}

		return ret.toString();
	}
}
