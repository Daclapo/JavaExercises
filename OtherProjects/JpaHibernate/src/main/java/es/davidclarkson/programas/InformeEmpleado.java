package es.davidclarkson.programas;

import es.davidclarkson.entities.Rental;
import es.davidclarkson.entities.Staff;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.Scanner;

public class InformeEmpleado {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		String id;

		do {
			System.out.println("\n\n______________________________________\n   Informe de Situación de Empleado  \n______________________________________\n");
			System.out.print("Introduzca el id del empleado (staff_id) que desee buscar: ");
			id = sc.nextLine();

			if (!id.equals("0") && !id.isEmpty()) {
				System.out.println("\n\nInforme de Situación de Empleado:");
				System.out.println("\nDatos del Empleado:\n" + getDatosEmpleado(id));
				System.out.println("\nDirección del Empleado:\n" + getDireccionEmpleado(id));
				System.out.println("\nTienda Asociada:\n" + getTiendaEmpleado(id));
				System.out.println("\nAlquileres Realizados:\n" + getAlquileresEmpleado(id));
			}
		} while (!id.equals("0"));

		System.out.println("Programa terminado.");
	}

	private static String getDatosEmpleado(String id) {
		StringBuilder ret = new StringBuilder();

		try (EntityManagerFactory factory = Persistence.createEntityManagerFactory("sakila");
		     EntityManager em = factory.createEntityManager()) {

			Staff staff = em.find(Staff.class, Byte.parseByte(id));

			if (staff != null) {
				ret.append("ID: ").append(staff.getId()).append("\n")
						.append("Nombre: ").append(staff.getFirstName()).append(" ").append(staff.getLastName()).append("\n")
						.append("Email: ").append(staff.getEmail()).append("\n")
						.append("Usuario: ").append(staff.getUsername()).append("\n")
						.append("Activo: ").append(staff.getActive() ? "Sí" : "No").append("\n")
						.append("Última Actualización: ").append(staff.getLastUpdate());
			} else {
				ret.append("No se encontró ningún empleado con ID: ").append(id);
			}
		} catch (NumberFormatException e) {
			ret.append("ID de empleado inválido.");
		}

		return ret.toString();
	}

	private static String getDireccionEmpleado(String id) {
		StringBuilder ret = new StringBuilder();

		try (EntityManagerFactory factory = Persistence.createEntityManagerFactory("sakila");
		     EntityManager em = factory.createEntityManager()) {

			Staff staff = em.find(Staff.class, Byte.parseByte(id));

			if (staff != null && staff.getAddress() != null) {
				ret.append("Dirección: ").append(staff.getAddress().getAddress()).append("\n")
						.append("Ciudad: ").append(staff.getAddress().getCity().getName()).append("\n")
						.append("Provincia: ").append(staff.getAddress().getCity().getCountry().getName());
			} else {
				ret.append("No se encontró la dirección del empleado con ID: ").append(id);
			}
		} catch (NumberFormatException e) {
			ret.append("ID de empleado inválido.");
		}

		return ret.toString();
	}

	private static String getTiendaEmpleado(String id) {
		StringBuilder ret = new StringBuilder();

		try (EntityManagerFactory factory = Persistence.createEntityManagerFactory("sakila");
		     EntityManager em = factory.createEntityManager()) {

			Staff staff = em.find(Staff.class, Byte.parseByte(id));

			if (staff != null && staff.getStore() != null) {
				ret.append("Tienda ID: ").append(staff.getStore().getId()).append("\n")
						.append("Dirección: ").append(staff.getStore().getAddress().getAddress()).append("\n")
						.append("Ciudad: ").append(staff.getStore().getAddress().getCity().getName()).append("\n")
						.append("Provincia: ").append(staff.getStore().getAddress().getCity().getCountry().getName());
			} else {
				ret.append("No se encontró la tienda asociada para el empleado con ID: ").append(id);
			}
		} catch (NumberFormatException e) {
			ret.append("ID de empleado inválido.");
		}

		return ret.toString();
	}

	private static String getAlquileresEmpleado(String id) {
		StringBuilder ret = new StringBuilder();

		try (EntityManagerFactory factory = Persistence.createEntityManagerFactory("sakila");
		     EntityManager em = factory.createEntityManager()) {

			Staff staff = em.find(Staff.class, Byte.parseByte(id));

			if (staff != null) {
				int i = 1;
				for (Rental rental : staff.getRentals()) {
					ret.append(i).append("º Alquiler ID: ").append(rental.getId()).append("\n")
							.append("  Película: ").append(
									rental.getInventory() != null && rental.getInventory().getFilm() != null ?
											rental.getInventory().getFilm().getTitle() : "No disponible"
							).append("\n")
							.append("  Fecha de Alquiler: ").append(rental.getRentalDate()).append("\n")
							.append("  Fecha de Devolución: ").append(
									rental.getReturnDate() != null ? rental.getReturnDate() : "Sin devolver"
							).append("\n\n");
					i++;
				}
			} else {
				ret.append("No se encontraron alquileres realizados por el empleado con ID: ").append(id);
			}
		} catch (NumberFormatException e) {
			ret.append("ID de empleado inválido.");
		}

		return ret.toString();
	}
}
