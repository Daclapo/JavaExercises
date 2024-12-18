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

		try (EntityManagerFactory factory = Persistence.createEntityManagerFactory("sakila");
		     EntityManager em = factory.createEntityManager()) {

			do {
				System.out.println("\n\n______________________________________\n   Informe de Situación de Empleado  " +
						"\n______________________________________\n");
				System.out.print("Introduzca el id del empleado (staff_id) que desee buscar: ");
				id = sc.nextLine();

				if (!id.equals("0") && !id.isEmpty()) {
					try {
						Staff staff = em.find(Staff.class, Byte.parseByte(id));

						if (staff != null) {
							System.out.println("\n\nInforme de Situación de Empleado:");
							System.out.println("\nDatos del Empleado:\n" + getDatosEmpleado(staff));
							System.out.println("\nDirección del Empleado:\n" + getDireccionEmpleado(staff));
							System.out.println("\nTienda Asociada:\n" + getTiendaEmpleado(staff));
							System.out.println("\nAlquileres Realizados:\n" + getAlquileresEmpleado(staff));
						} else {
							System.out.println("No se encontró ningún empleado con ID: " + id);
						}
					} catch (NumberFormatException e) {
						System.out.println("ID de empleado inválido.");
					}
				}
			} while (!id.equals("0"));

			System.out.println("Programa terminado.");
		}
	}

	private static String getDatosEmpleado(Staff staff) {
		StringBuilder ret = new StringBuilder();
		ret.append("ID: ").append(staff.getId()).append("\n")
				.append("Nombre: ").append(staff.getFirstName()).append(" ").append(staff.getLastName()).append("\n")
				.append("Email: ").append(staff.getEmail()).append("\n")
				.append("Usuario: ").append(staff.getUsername()).append("\n")
				.append("Activo: ");
		if (staff.getActive()) {
			ret.append("Sí");
		} else {
			ret.append("No");
		}
		ret.append("\nÚltima Actualización: ").append(staff.getLastUpdate());

		return ret.toString();
	}

	private static String getDireccionEmpleado(Staff staff) {
		StringBuilder ret = new StringBuilder();
		if (staff.getAddress() != null) {
			ret.append("Dirección: ").append(staff.getAddress().getAddress()).append("\n")
					.append("Ciudad: ").append(staff.getAddress().getCity().getName()).append("\n")
					.append("Provincia: ").append(staff.getAddress().getCity().getCountry().getName());
		} else {
			ret.append("No se encontró la dirección del empleado.");
		}
		return ret.toString();
	}

	private static String getTiendaEmpleado(Staff staff) {
		StringBuilder ret = new StringBuilder();
		if (staff.getStore() != null) {
			ret.append("Tienda ID: ").append(staff.getStore().getId()).append("\n")
					.append("Dirección: ").append(staff.getStore().getAddress().getAddress()).append("\n")
					.append("Ciudad: ").append(staff.getStore().getAddress().getCity().getName()).append("\n")
					.append("Provincia: ").append(staff.getStore().getAddress().getCity().getCountry().getName());
		} else {
			ret.append("No se encontró la tienda asociada para el empleado.");
		}
		return ret.toString();
	}

	private static String getAlquileresEmpleado(Staff staff) {
		StringBuilder ret = new StringBuilder();

		int i = 1;
		for (Rental rental : staff.getRentals()) {
			ret.append(i).append("º Alquiler ID: ").append(rental.getId()).append("\n")
					.append("  Película: ");
			if (rental.getInventory() != null && rental.getInventory().getFilm() != null) {
				ret.append(rental.getInventory().getFilm().getTitle());
			} else {
				ret.append("No disponible");
			}
			ret.append("\n")
					.append("  Fecha de Alquiler: ").append(rental.getRentalDate()).append("\n")
					.append("  Fecha de Devolución: ");
			if (rental.getReturnDate() != null) {
				ret.append(rental.getReturnDate());
			} else {
				ret.append("Sin devolver");
			}
			ret.append("\n\n");
			i++;
		}

		if (ret.isEmpty()) {
			ret.append("No se encontraron alquileres realizados por el empleado.");
		}

		return ret.toString();
	}
}
