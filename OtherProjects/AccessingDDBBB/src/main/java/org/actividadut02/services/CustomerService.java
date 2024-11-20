package org.actividadut02.services;

import org.actividadut02.entities.Customer;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface CustomerService {

	Long count();
	boolean existsById(int id);
	Optional<Customer> findById(int id) throws SQLException;
	List<Customer> findAll();
	Customer save(Customer orders) throws SQLException;
	void deleteById(int id);

}
