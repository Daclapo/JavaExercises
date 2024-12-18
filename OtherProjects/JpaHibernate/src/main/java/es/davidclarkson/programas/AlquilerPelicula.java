package es.davidclarkson.programas;

import es.davidclarkson.entities.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.time.Instant;
import java.util.List;
import java.util.Scanner;

public class AlquilerPelicula {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);

		try (EntityManagerFactory factory = Persistence.createEntityManagerFactory("sakila");
		     EntityManager em = factory.createEntityManager()) {

			System.out.println("\n\n______________________________________\n   Sistema de Alquiler de Películas   " +
					"\n______________________________________\n");

			System.out.print("Introduzca el id del empleado (staff_id): ");
			String empleadoId = sc.nextLine();

			System.out.print("Introduzca el id de la película (film_id): ");
			String peliculaId = sc.nextLine();

			System.out.print("Introduzca el id del cliente (customer_id): ");
			String clienteId = sc.nextLine();


			Staff empleado = em.find(Staff.class, Short.parseShort(empleadoId));
			if (empleado == null) {
				System.out.println("El empleado con ID " + empleadoId + " no existe." +
						"\n(Solo existen empleados con ID (1 y 2))" +
						"\nProceso de creación de alquiler cancelado.");
				return;
			}

			Store tienda = empleado.getStore();
			if (tienda == null) {
				System.out.println("No se encontró una tienda asociada al empleado con ID " + empleadoId +
						"\nProceso de creación de alquiler cancelado.");
				return;
			}

			Film pelicula = em.find(Film.class, Short.parseShort(peliculaId));
			if (pelicula == null) {
				System.out.println("La película con ID " + peliculaId + " no existe." +
						"\nProceso de creación de alquiler cancelado.");
				return;
			}

			Inventory inventarioDisponible = null;
			for (Inventory inventario : pelicula.getInventories()) {
				if (inventario.getStore().getId().equals(tienda.getId())) {
					inventarioDisponible = inventario;
					break;
				}
			}
			if (inventarioDisponible == null) {
				System.out.println("La película no está disponible en el inventario de la tienda del empleado." +
						"\nProceso de creación de alquiler cancelado.");
				return;
			}

			boolean hayCopiasDisponibles = verificarCopiasDisponibles(
					em,
					inventarioDisponible.getFilm().getId(),
					inventarioDisponible.getStore().getId()
			);

			if (!hayCopiasDisponibles) {
				System.out.println("No hay copias de la película disponibles actualmente en el inventario." +
						"\nProceso de creación de alquiler cancelado.");
				return;
			}

			Customer cliente = em.find(Customer.class, Short.parseShort(clienteId));
			if (cliente == null) {
				System.out.println("El cliente con ID " + clienteId + " no existe." +
						"\nProceso de creación de alquiler cancelado.");
				return;
			}

			em.getTransaction().begin();
			try {
				Rental nuevoAlquiler = new Rental();
				nuevoAlquiler.setRentalDate(Instant.now());
				nuevoAlquiler.setInventory(inventarioDisponible);
				nuevoAlquiler.setCustomer(cliente);
				nuevoAlquiler.setRentor(empleado);
				nuevoAlquiler.setLastUpdate(Instant.now());

				em.persist(nuevoAlquiler);
				em.getTransaction().commit();

				System.out.println("El alquiler se ha registrado correctamente.");
				System.out.println("Detalles del Alquiler:");
				System.out.println("Empleado:\t" + empleado.getFirstName() + " " + empleado.getLastName());
				System.out.println("Película:\t" + pelicula.getTitle());
				System.out.println("Cliente:\t" + cliente.getFirstName() + " " + cliente.getLastName());
			} catch (Exception e) {
				em.getTransaction().rollback();
				System.out.println("Hubo un error al registrar el alquiler.\nProceso de creacion de alquiler cancelado.");
				e.printStackTrace();
			}

		} catch (NumberFormatException e) {
			System.out.println("Por lo menos un ID de los proporcionados no es válido.");
		}
	}


	private static boolean verificarCopiasDisponibles(EntityManager em, short filmId, short storeId) {
		try {
			List<Integer> copiasDisponibles = (List<Integer>) em.createNativeQuery(
							"SELECT i.inventory_id " +
									"FROM inventory i " +
									"WHERE i.film_id = ? AND i.store_id = ? " +
									"AND NOT EXISTS (" +
									"  SELECT 1 FROM rental r " +
									"  WHERE r.inventory_id = i.inventory_id AND r.return_date IS NULL" +
									")")
					.setParameter(1, filmId)
					.setParameter(2, storeId)
					.getResultList();
			return !copiasDisponibles.isEmpty(); // Si hay al menos una copia, hay disponibilidad.
		} catch (Exception e) {
			System.out.println("Hubo un error al verificar las copias disponibles.");
			return false;
		}
	}
}
