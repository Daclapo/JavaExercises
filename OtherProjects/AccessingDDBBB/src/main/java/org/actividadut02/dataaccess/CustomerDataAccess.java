package org.actividadut02.dataaccess;

import org.actividadut02.entities.Customer;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface CustomerDataAccess {
	Long count();
	boolean existsById(int id);
	Optional<Customer> findById(int id) throws SQLException;
	List<Customer> findAll();
	Customer save(Customer customers) throws SQLException;
	void deleteById(int id);
}
