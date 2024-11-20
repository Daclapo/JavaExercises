package org.actividadut02.dataaccess;

import org.actividadut02.entities.Customer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Clase Principal. Manejo de conexiones relacionadas con la tabla 'customers' de la base de datos 'classicmodels'.
 */
public class CustomerDataAccessImpl implements CustomerDataAccess {

	/**
	 * Cuenta el número total de clientes en la base de datos.
	 *
	 * @return el número total de clientes.
	 * @throws RuntimeException si ocurre un error al acceder a la base de datos.
	 */
	@Override
	public Long count() {
		String sql = "select count(*) from customers";
		try (Connection connection = ClassicModelsCP.getInstance().getConnection();
			 PreparedStatement preparedStatement = connection.prepareStatement(sql);
			 ResultSet resultSet = preparedStatement.executeQuery()) {
			resultSet.next();
			return resultSet.getLong(1);

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Verifica si un cliente existe en la base de datos por su ID.
	 *
	 * @param id el ID del cliente a verificar, que sera el 'customerNumber' en la BBDD.
	 * @return true si el cliente existe, false en caso contrario.
	 */
	@Override
	public boolean existsById(int id) {
		String sql = "SELECT COUNT(*) FROM customers WHERE customerNumber = ?;";
		try (Connection connection = ClassicModelsCP.getInstance().getConnection();
			 PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
			preparedStatement.setInt(1, id);

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				if (resultSet.next()) {
					return resultSet.getInt(1) > 0;
				}
			}
		} catch (SQLException e) {
			System.out.println("Error al verificar la existencia del cliente: " + e.getMessage());
		}
		return false;
	}

	/**
	 * Busca un cliente en la base de datos por su ID.
	 *
	 * @param id el ID del cliente a buscar, que será el 'customerNumber' en la BBDD.
	 * @return un Optional que contiene el cliente si se encuentra, o un Optional vacío si no.
	 * @throws SQLException si ocurre un error al acceder a la base de datos.
	 */
	@Override
	public Optional<Customer> findById(int id) throws SQLException {
		String sql = "SELECT * FROM customers WHERE customerNumber = ?;";
		Customer customer = null;

		try (Connection connection = ClassicModelsCP.getInstance().getConnection()) {
			PreparedStatement statement = connection.prepareStatement(sql);

			statement.setInt(1, id);
			try (ResultSet resultSet = statement.executeQuery()) {
				if (resultSet.next()) {
					customer = new Customer();
					customer.setCustomerNumber(resultSet.getInt("customerNumber"));
					customer.setCustomerName(resultSet.getString("customerName"));
					customer.setContactLastName(resultSet.getString("contactLastName"));
					customer.setContactFirstName(resultSet.getString("contactFirstName"));
					customer.setPhone(resultSet.getString("phone"));
					customer.setAddressLine1(resultSet.getString("addressLine1"));
					customer.setAddressLine2(resultSet.getString("addressLine2"));
					customer.setCity(resultSet.getString("city"));
					customer.setState(resultSet.getString("state"));
					customer.setPostalCode(resultSet.getString("postalCode"));
					customer.setCountry(resultSet.getString("country"));
					customer.setSalesRepEmployeeNumber(resultSet.getInt("salesRepEmployeeNumber")); // No tengo claro si podra utilizar ints
					customer.setCreditLimit(resultSet.getInt("creditLimit"));
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return Optional.ofNullable(customer);
	}

	/**
	 * Devuelve todos los clientes de la base de datos.
	 *
	 * @return una lista (ArrayList<Customer>) de todos los clientes.
	 * @throws RuntimeException si ocurre un error al acceder a la base de datos.
	 */
	@Override
	public List<Customer> findAll() {
		List<Customer> customersList = new ArrayList<>();
		String sql = "select * from customers;";

		try (Connection connection = ClassicModelsCP.getInstance().getConnection()) {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);

			while (resultSet.next()) {
				Customer customer = new Customer();
				customer.setCustomerNumber(resultSet.getInt("customerNumber"));
				customer.setCustomerName(resultSet.getString("customerName"));
				customer.setContactLastName(resultSet.getString("contactLastName"));
				customer.setContactFirstName(resultSet.getString("contactFirstName"));
				customer.setPhone(resultSet.getString("phone"));
				customer.setAddressLine1(resultSet.getString("addressLine1"));
				customer.setAddressLine2(resultSet.getString("addressLine2"));
				customer.setCity(resultSet.getString("city"));
				customer.setState(resultSet.getString("state"));
				customer.setPostalCode(resultSet.getString("postalCode"));
				customer.setCountry(resultSet.getString("country"));
				customer.setSalesRepEmployeeNumber(resultSet.getInt("salesRepEmployeeNumber"));
				customer.setCreditLimit(resultSet.getDouble("creditLimit"));

				customersList.add(customer);
			}
		} catch (SQLException e) {
			System.out.println("Error al recuperar clientes de la base de datos: " + e.getMessage());
		}
		return customersList;
	}


	/**
	 * Guarda un cliente en la base de datos. Si el id del cliente no existe, lo inserta un nuevo cliente; si el id ya existe, lo actualiza, modificando sus atributos.
	 *
	 * @param customer el cliente a guardar.
	 * @return el cliente guardado.
	 * @throws SQLException si ocurre un error al acceder a la base de datos.
	 */
	@Override
	public Customer save(Customer customer) throws SQLException {
		if (!isSalesRepEmployeeNumberValid(customer.getSalesRepEmployeeNumber())) {
			throw new IllegalArgumentException("El número de representante de ventas proporcionado no es válido.");
		}
		if (!existsById(customer.getCustomerNumber())) {
			return insertCustomer(customer);
		} else {
			return updateCustomer(customer);
		}
	}

	private Customer insertCustomer(Customer customer) {
		String SQL_INSERT_CUSTOMER = "INSERT INTO customers VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

		try (Connection connection = ClassicModelsCP.getInstance().getConnection();
			 PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT_CUSTOMER, PreparedStatement.RETURN_GENERATED_KEYS)) {
			connection.setAutoCommit(false);

			preparedStatement.setInt(1, customer.getCustomerNumber());
			preparedStatement.setString(2, customer.getCustomerName());
			preparedStatement.setString(3, customer.getContactLastName());
			preparedStatement.setString(4, customer.getContactFirstName());
			preparedStatement.setString(5, customer.getPhone());
			preparedStatement.setString(6, customer.getAddressLine1());
			preparedStatement.setString(7, customer.getAddressLine2());
			preparedStatement.setString(8, customer.getCity());
			preparedStatement.setString(9, customer.getState());
			preparedStatement.setString(10, customer.getPostalCode());
			preparedStatement.setString(11, customer.getCountry());
			preparedStatement.setInt(12, customer.getSalesRepEmployeeNumber());
			preparedStatement.setDouble(13, customer.getCreditLimit());

			int rowsAffected = preparedStatement.executeUpdate();

			if (rowsAffected > 0) {
				System.out.println("El cliente ha sido insertado correctamente.");
			} else {
				System.out.println("No se insertó ninguna fila.");
			}
			connection.commit();

		} catch (SQLException e) {
			throw new RuntimeException("Error al insertar el cliente: " + e.getMessage());
		}
		return customer;
	}

	private Customer updateCustomer(Customer customer) {
		String SQL_UPDATE_CUSTOMER = """
        UPDATE customers SET customerName = ?, contactLastName = ?, contactFirstName = ?,
        phone = ?, addressLine1 = ?, addressLine2 = ?, city = ?, state = ?, postalCode = ?, country = ?,
        salesRepEmployeeNumber = ?, creditLimit = ? WHERE customerNumber = ?
        """;

		try (Connection connection = ClassicModelsCP.getInstance().getConnection();
			 PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_CUSTOMER)) {
			connection.setAutoCommit(false);

			preparedStatement.setString(1, customer.getCustomerName());
			preparedStatement.setString(2, customer.getContactLastName());
			preparedStatement.setString(3, customer.getContactFirstName());
			preparedStatement.setString(4, customer.getPhone());
			preparedStatement.setString(5, customer.getAddressLine1());
			preparedStatement.setString(6, customer.getAddressLine2());
			preparedStatement.setString(7, customer.getCity());
			preparedStatement.setString(8, customer.getState());
			preparedStatement.setString(9, customer.getPostalCode());
			preparedStatement.setString(10, customer.getCountry());
			preparedStatement.setInt(11, customer.getSalesRepEmployeeNumber());
			preparedStatement.setDouble(12, customer.getCreditLimit());
			preparedStatement.setInt(13, customer.getCustomerNumber());

			int rowsAffected = preparedStatement.executeUpdate();

			if (rowsAffected > 0) {
				System.out.println("El cliente ha sido actualizado correctamente.");
			} else {
				System.out.println("No se encontró ningún cliente con el id: " + customer.getCustomerNumber());
			}
			connection.commit();

		} catch (SQLException e) {
			throw new RuntimeException("Error al actualizar el cliente: " + e.getMessage());
		}
		return customer;
	}

	private boolean isSalesRepEmployeeNumberValid(int salesRepEmployeeNumber) {
		String sql = "SELECT COUNT(*) FROM employees WHERE employeeNumber = ?";
		try (Connection connection = ClassicModelsCP.getInstance().getConnection();
			 PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
			preparedStatement.setInt(1, salesRepEmployeeNumber);
			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				if (resultSet.next()) {
					return resultSet.getInt(1) > 0;
				}
			}
		} catch (SQLException e) {
			System.out.println("Error al verificar el número de representante de ventas: " + e.getMessage());
		}
		return false;
	}


	/**
	 * Elimina un cliente de la base de datos por su ID, incluyendo las entradas en otras tablas que tengan una clave foranea con el ID de el cliente.
	 *
	 * @param id el ID del cliente a eliminar, que será el 'customerNumber' en la BBDD.
	 */
	@Override
	public void deleteById(int id) {
		String deleteOrderDetailsSQL = "DELETE FROM orderdetails WHERE orderNumber IN (SELECT orderNumber FROM orders WHERE customerNumber = ?)";
		String deletePaymentsSQL = "DELETE FROM payments WHERE customerNumber = ?";
		String deleteOrdersSQL = "DELETE FROM orders WHERE customerNumber = ?";
		String deleteCustomerSQL = "DELETE FROM customers WHERE customerNumber = ?";

		try (Connection connection = ClassicModelsCP.getInstance().getConnection()) {

			try (PreparedStatement orderDetailsStatement = connection.prepareStatement(deleteOrderDetailsSQL)) {
				orderDetailsStatement.setInt(1, id);
				orderDetailsStatement.executeUpdate();
			}

			try (PreparedStatement paymentsStatement = connection.prepareStatement(deletePaymentsSQL)) {
				paymentsStatement.setInt(1, id);
				paymentsStatement.executeUpdate();
			}

			try (PreparedStatement ordersStatement = connection.prepareStatement(deleteOrdersSQL)) {
				ordersStatement.setInt(1, id);
				ordersStatement.executeUpdate();
			}

			try (PreparedStatement customerStatement = connection.prepareStatement(deleteCustomerSQL)) {
				customerStatement.setInt(1, id);
				int affectedRows = customerStatement.executeUpdate();

				if (affectedRows > 0) {
					System.out.println("El cliente y todas sus referencias han sido eliminados exitosamente.");
				} else {
					System.out.println("No se encontró ningún cliente con el id: " + id);
				}
			}
		} catch (SQLException e) {
			System.out.println("Error al eliminar el cliente: " + e.getMessage());
		}
	}
}