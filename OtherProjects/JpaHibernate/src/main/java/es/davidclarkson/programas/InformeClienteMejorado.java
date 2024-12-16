package es.davidclarkson.programas;

import es.davidclarkson.entities.Customer;
import es.davidclarkson.entities.Payment;
import es.davidclarkson.entities.Rental;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.Objects;
import java.util.Scanner;

public class InformeClienteMejorado {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		String id;

		try (EntityManagerFactory factory = Persistence.createEntityManagerFactory("sakila");
		     EntityManager em = factory.createEntityManager()) {

			do {
				System.out.println("\n\n_____________________________________\n   Informe de Situacion de Cliente   \n_____________________________________\n");
				System.out.print("Introduzca el id del cliente (customer_id) que desee buscar: ");
				id = sc.nextLine();

				if (!id.equals("0") && !id.isEmpty()) {
					try {
						Customer c = em.find(Customer.class, Short.parseShort(id));

						System.out.println("\n\nInforme de Situacion de Cliente:");
						System.out.println("\n_____________________\nDatos del Cliente:\n" + getDatosCliente(c));
						System.out.println("\n_____________________\nTiendas Asociadas:\n" + getTiendasAsociadas(c));
						System.out.println("\n_____________________\nHistorial de Alquileres:\n" + getHistAlquileres(c));
						System.out.println("\n_____________________\nHistorial de Pagos:\n" + getHistPagos(c));
					} catch (NumberFormatException e) {
						System.out.println("ID de cliente inválido.");
					}
				}
			} while (!Objects.equals(id, "0"));

			System.out.println("Programa terminado.");
		}
	}

	private static String getDatosCliente(Customer c) {
		if (c == null) {
			return "No se ha encontrado el cliente.";
		}

		return "ID:\t\t\t" + c.getId().toString() +
				"\nFirst Name:\t" + c.getFirstName() +
				"\nLast Name:\t" + c.getLastName() +
				"\nEmail:\t\t" + c.getEmail() +
				"\nAddress:\t" + c.getAddress().getAddress() + ", " +
				c.getAddress().getPostalCode() + ", " +
				c.getAddress().getDistrict() + ", " +
				c.getAddress().getCity().getName() + ", " +
				c.getAddress().getCity().getCountry().getName();
	}

	private static String getTiendasAsociadas(Customer c) {
		if (c == null || c.getStore() == null) {
			return "El cliente no tiene una tienda asociada.";
		}

		return "Tienda ID: " + c.getStore().getId() +
				"\nDirección: " + c.getStore().getAddress().getAddress() + ", " +
				c.getStore().getAddress().getCity().getName() + ", " +
				c.getStore().getAddress().getCity().getCountry().getName();
	}

	private static String getHistAlquileres(Customer c) {
		if (c == null) {
			return "No se ha encontrado el cliente.";
		}

		StringBuilder ret = new StringBuilder();
		int i = 1;
		for (Rental rental : c.getRentalHistory()) {
			ret.append(i).append("º Alquiler ID: ").append(rental.getId()).append("\n")
					.append("  Película: ").append(
							rental.getInventory() != null && rental.getInventory().getFilm() != null ?
									rental.getInventory().getFilm().getTitle() : "No disponible"
					).append("\n")
					.append("  Fecha de alquiler: ").append(rental.getRentalDate()).append("\n")
					.append("  Fecha de devolución: ").append(
							rental.getReturnDate() != null ? rental.getReturnDate() : "Sin devolver"
					).append("\n\n");
			i++;
		}

		return ret.toString();
	}

	private static String getHistPagos(Customer c) {
		if (c == null) {
			return "No se ha encontrado el cliente.";
		}

		StringBuilder ret = new StringBuilder();
		int i = 1;
		for (Payment payment : c.getPaymentHistory()) {
			ret.append(i).append("º; Payment ID: ").append(payment.getId())
					.append(", Amount: ").append(payment.getAmount())
					.append(", Date: ").append(payment.getPaymentDate()).append("\n");
			i++;
		}

		return ret.toString();
	}
}
