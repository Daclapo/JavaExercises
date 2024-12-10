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
		String id = "valor inicial";


		try (EntityManagerFactory factory = Persistence.createEntityManagerFactory("sakila");
		     EntityManager em = factory.createEntityManager()) {

			Customer c = em.find(Customer.class, Short.parseShort(id));


			do {
				System.out.println("\n\n_____________________________________\n   Informe de Situacion de Cliente   \n_____________________________________\n");
				System.out.print("Introduzca el id del cliente (customer_id) que desee buscar: ");
				id = sc.nextLine();


				System.out.println("\n\nInforme de Situacion de Cliente:");
				System.out.println("\n_____________________\nDatos del Cliente:\n" + getDatosCliente(id, c));
				System.out.println("\n_____________________\nTiendas Asociadas:\n" + getTiendasAsociadas(id, c));
				System.out.println("\n_____________________\nHistorial de Alquileres:\n" + getHistAlquileres(id, c));
				System.out.println("\n_____________________\nHistorial de Pagos:\n" + getHistPagos(id, c));

			} while (!Objects.equals(id, "0") && !Objects.equals(id, ""));

		} catch (NumberFormatException e) {
			System.out.println("ID de cliente inválido");
		}
	}


	private static String getDatosCliente(String id, Customer c) {
		// Este primer metodo lo hice con concatenando a una String directamente, pero luego me acorde de que estaba
		// StringBulider.
		// He dejado esta forma de hacerlo ya que ya lo tenia hecho y funciona igual que los otrosmetodos con
		// StringBuilder
		String ret = "";

		if (c != null) {
			ret = ret + "ID:\t\t\t" + c.getId().toString();
			ret = ret + "\nFirst Name:\t" + c.getFirstName();
			ret += "\nLast Name:\t" + c.getLastName();
			ret += "\nEmail:\t\t" + c.getEmail();
			ret += "\nAddress:\t" + c.getAddress().getAddress() + ", " + c.getAddress().getPostalCode() + ", "
					+ c.getAddress().getDistrict() + ", " + c.getAddress().getCity().getName() + ", "
					+ c.getAddress().getCity().getCountry().getName();
		} else {
			ret = "No se ha encontrado el cliente con ID:" + id;
		}
		return ret;
	}


	private static String getTiendasAsociadas(String id, Customer c) {
		StringBuilder ret = new StringBuilder();

		if (c != null) {
			if (c.getStore() != null) {
				ret.append("Tienda ID: ").append(c.getStore().getId()).append("\n");
				ret.append("Dirección: ").append(c.getStore().getAddress().getAddress()).append(", ");
				ret.append(c.getStore().getAddress().getCity().getName()).append(", ");
				ret.append(c.getStore().getAddress().getCity().getCountry().getName()).append("\n");
			} else {
				ret.append("El cliente no tiene una tienda asociada.\n");
			}
		} else {
			ret.append("No se ha encontrado el cliente con ID: ").append(id);
		}

		return ret.toString();
	}



	private static String getHistAlquileres(String id, Customer c) {
		StringBuilder ret = new StringBuilder();

		if (c != null) {
			int i = 1;
			for (Rental rental : c.getRentalHistory()) {
				ret.append(i).append("º Alquiler ID: ").append(rental.getId()).append("\n");
				if (rental.getInventory() != null && rental.getInventory().getFilm() != null) {
					ret.append("  Película: ").append(rental.getInventory().getFilm().getTitle()).append("\n");
				} else {
					ret.append("  Película: No disponible\n");
				}
				ret.append("  Fecha de alquiler: ").append(rental.getRentalDate()).append("\n");
				if (rental.getReturnDate() != null) {
					ret.append("  Fecha de devolución: ").append(rental.getReturnDate()).append("\n");
				} else {
					ret.append("  Fecha de devolución: Sin devolver\n");
				}
				i++;
			}
		} else {
			ret.append("No se ha encontrado el cliente con ID: ").append(id);
		}

		return ret.toString();
	}





	private static String getHistPagos(String id,Customer c) {
		StringBuilder ret = new StringBuilder();
		if (c != null) {
			int i = 1;
			for (Payment payment : c.getPaymentHistory()) {
				ret.append(i + "º; Payment ID: ").append(payment.getId())
						.append(", Amount: ").append(payment.getAmount())
						.append(", Date: ").append(payment.getPaymentDate())
						.append("\n");
				i++;
			}
		} else {
			ret.append("No se ha encontrado el cliente con ID: ").append(id);
		}
		return ret.toString();
	}

}
