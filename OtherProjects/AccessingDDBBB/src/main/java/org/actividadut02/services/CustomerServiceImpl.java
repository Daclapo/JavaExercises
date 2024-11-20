package org.actividadut02.services;

import org.actividadut02.dataaccess.CustomerDataAccess;
import org.actividadut02.entities.Customer;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class CustomerServiceImpl implements CustomerService {

	private final CustomerDataAccess customerDataAccess;

	public CustomerServiceImpl(CustomerDataAccess customerDataAccess) {
		this.customerDataAccess = customerDataAccess;
	}


	@Override
	public Long count() {
		return customerDataAccess.count();
	}

	@Override
	public boolean existsById(int id) {
		return customerDataAccess.existsById(id);
	}

	@Override
	public Optional<Customer> findById(int id) throws SQLException {
		return customerDataAccess.findById(id);
	}

	@Override
	public List<Customer> findAll() {
		List<Customer> customers = customerDataAccess.findAll();
		try {
			if (customers.isEmpty()) {
				System.out.println("No existen clientes");
			}
		}catch (RuntimeException e){
			System.out.println(e.getMessage());
		}
		return customers;
	}

	@Override
	public Customer save(Customer customer) throws SQLException {
		if (customer.getCustomerNumber() <= 0) {
			throw new RuntimeException("El ID del cliente, customerNumber, no puede estar vacío\n");
		}
		if (customer.getCustomerName() == null ) {
			throw new RuntimeException("El nombre del cliente, customerName, no puede estar vacío\n");
		}
		return customerDataAccess.save(customer);
	}

	@Override
	public void deleteById(int id) {
		if (!customerDataAccess.existsById(id)) {
			throw new RuntimeException("Cliente no encontrado\n");
		}
		customerDataAccess.deleteById(id);
	}
}
