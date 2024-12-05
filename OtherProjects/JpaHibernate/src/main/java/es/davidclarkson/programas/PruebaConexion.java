package es.davidclarkson.programas;

import es.davidclarkson.entities.Category;
import es.davidclarkson.entities.City;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class PruebaConexion {
	public static void main(String[] args) {
		try (EntityManagerFactory factory = Persistence.createEntityManagerFactory("sakila");
		     EntityManager manager = factory.createEntityManager()) {

			Category category = manager.find(Category.class, 1L);

			if (category != null)
				System.out.println(category);
			else
				System.out.println("No se puede obtener la categoria con ese id");

			City city = manager.find(City.class, 1L);
			System.out.println(city.getName());
			System.out.println(city.getCountry().getName());

		}
	}
}
