package es.davidclarkson.programas;

import es.davidclarkson.entities.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.time.Instant;
import java.util.Scanner;

public class AlquilerPelicula {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);

		try (EntityManagerFactory factory = Persistence.createEntityManagerFactory("sakila");
		     EntityManager em = factory.createEntityManager()) {

			System.out.println("\n\n_____________________________________\n   Sistema de Alquiler de Películas   \n_____________________________________\n");

			// Solicitar datos
			System.out.print("Introduzca el id del empleado (staff_id): ");
			String empleadoId = sc.nextLine();

			System.out.print("Introduzca el id de la película (film_id): ");
			String peliculaId = sc.nextLine();

			System.out.print("Introduzca el id del cliente (customer_id): ");
			String clienteId = sc.nextLine();

			// Validar el empleado y obtener su tienda
			Staff empleado = em.find(Staff.class, Byte.parseByte(empleadoId));
			if (empleado == null) {
				System.out.println("El empleado con ID " + empleadoId + " no existe. Operación cancelada.");
				return;
			}

			Store tienda = empleado.getStore();
			if (tienda == null) {
				System.out.println("No se encontró una tienda asociada al empleado con ID " + empleadoId + ". Operación cancelada.");
				return;
			}

			// Validar la película
			Film pelicula = em.find(Film.class, Short.parseShort(peliculaId));
			if (pelicula == null) {
				System.out.println("La película con ID " + peliculaId + " no existe. Operación cancelada.");
				return;
			}

			// Validar que la película está disponible en la tienda
			Inventory inventarioDisponible = null;
			for (Inventory inventario : pelicula.getInventories()) {
				if (inventario.getStore().getId().equals(tienda.getId())) {
					inventarioDisponible = inventario;
					break;
				}
			}
			if (inventarioDisponible == null) {
				System.out.println("La película no está disponible en el inventario de la tienda del empleado. Operación cancelada.");
				return;
			}

			// Verificar copias disponibles (puedes usar procedimientos almacenados si es necesario)
			boolean hayCopiasDisponibles = verificarCopiasDisponibles(em, inventarioDisponible);
			if (!hayCopiasDisponibles) {
				System.out.println("No hay copias disponibles de la película en el inventario. Operación cancelada.");
				return;
			}

			// Validar el cliente
			Customer cliente = em.find(Customer.class, Short.parseShort(clienteId));
			if (cliente == null) {
				System.out.println("El cliente con ID " + clienteId + " no existe. Operación cancelada.");
				return;
			}

			// Crear el alquiler
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

				System.out.println("El alquiler se ha registrado exitosamente.");
				System.out.println("Detalles del Alquiler:");
				System.out.println("Empleado: " + empleado.getFirstName() + " " + empleado.getLastName());
				System.out.println("Película: " + pelicula.getTitle());
				System.out.println("Cliente: " + cliente.getFirstName() + " " + cliente.getLastName());
			} catch (Exception e) {
				em.getTransaction().rollback();
				System.out.println("Ocurrió un error al registrar el alquiler. Operación cancelada.");
				e.printStackTrace();
			}

		} catch (NumberFormatException e) {
			System.out.println("Uno o más ID proporcionados no son válidos.");
		}
	}

	/**
	 * Verifica si hay copias disponibles de una película en un inventario.
	 * Aquí se puede usar un procedimiento almacenado de Sakila para obtener esta información.
	 */
	private static boolean verificarCopiasDisponibles(EntityManager em, Inventory inventario) {
		try {
			long alquileresActivos = em.createQuery(
							"SELECT COUNT(r) FROM Rental r WHERE r.inventory.id = :inventoryId AND r.returnDate IS NULL", Long.class)
					.setParameter("inventoryId", inventario.getId())
					.getSingleResult();
			return alquileresActivos == 0; // Si no hay alquileres activos, hay copias disponibles
		} catch (Exception e) {
			System.out.println("Ocurrió un error al verificar las copias disponibles.");
			return false;
		}
	}
}
