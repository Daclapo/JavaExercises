package org.actividadut02.dataaccess;

import org.actividadut02.entities.Product;
import org.actividadut02.enums.ProductLine;

import java.sql.*;
import java.util.*;

/**
 *  Clase Principal. Manejo de conexiones relacionadas con la tabla 'products' de la base de datos 'classicmodels'.
 *
 * @Author: Alejandra y David
 * @Version: 1.0
 * @Date: 2024-10-09
 */
public class ProductDataAccessImpl implements ProductDataAccess {

	/**
	 * Cuenta el número total de productos en la base de datos.
	 *
	 * @return el número total de productos.
	 * @throws RuntimeException si ocurre un error al acceder a la base de datos.
	 */
	@Override
	public Long count() {
		String sql = "select count(*) from products";
		try (Connection connection = ClassicModelsCP.getInstance().getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			ResultSet resultSet = preparedStatement.executeQuery()){
			resultSet.next();
			return resultSet.getLong(1);

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Verifica si un producto existe en la base de datos por su ID.
	 *
	 * @param id el ID del producto a verificar, que será el 'productCode' en la BBDD.
	 * @return true si el producto existe, false en caso contrario.
	 */
	@Override
	public boolean existsById(String id) {
		String sql = "SELECT COUNT(*) FROM products WHERE productCode = ?;";
		try (Connection connection = ClassicModelsCP.getInstance().getConnection();
		     PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

			preparedStatement.setString(1, id);

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				if (resultSet.next()) {
					return resultSet.getInt(1) > 0;
				}
			}
		} catch (SQLException e) {
			System.out.println("Error al verificar la existencia del producto: " + e.getMessage());
		}
		return false;
	}


	/**
	 * Busca un producto en la base de datos por su ID.
	 *
	 * @param id el ID del producto a buscar, que será el 'productCode' en la BBDD.
	 * @return un Optional que contiene el producto si se encuentra, o un Optional vacío si no.
	 * @throws SQLException si ocurre un error al acceder a la base de datos.
	 */
	@Override
	public Optional<Product> findById(String id) throws SQLException {
		String sql = "SELECT * FROM PRODUCTS WHERE productCode = ?;";
		Product product = null;

		try(Connection connection = ClassicModelsCP.getInstance().getConnection()) {
			PreparedStatement statement = connection.prepareStatement(sql);

			statement.setString(1, id);

			try(ResultSet resultSet = statement.executeQuery()) {
				if (resultSet.next()) {
					product = new Product();
					product.setProductCode(resultSet.getString("productCode"));
					product.setProductName(resultSet.getString("productName"));
					product.setProductLine(ProductLine.fromString(resultSet.getString("productLine")));
					product.setProductScale(resultSet.getString("productScale"));
					product.setProductVendor(resultSet.getString("productVendor"));
					product.setProductDescription(resultSet.getString("productDescription"));
					product.setQuantityInStock(resultSet.getInt("quantityInStock"));
					product.setBuyPrice(resultSet.getDouble("buyPrice"));
					product.setMSRP(resultSet.getDouble("MSRP"));

				}

			}

		}catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return Optional.ofNullable(product);
	}

	/**
	 * Busca todos los productos en la base de datos.
	 *
	 * @return una lista con todos los productos.
	 * @throws RuntimeException si ocurre un error al acceder a la base de datos.
	 */
	@Override
	public List<Product> findAll() {
		List<Product> productsList = new ArrayList<>();
		String sql = "select*from products;";

		try(Connection connection = ClassicModelsCP.getInstance().getConnection()) {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);

			while (resultSet.next()) {
				Product product = new Product();
				product.setProductCode(resultSet.getString("productCode"));
				product.setProductName(resultSet.getString("productName"));
				product.setProductLine(ProductLine.fromString(resultSet.getString("productLine")));
				product.setQuantityInStock(resultSet.getInt("quantityInStock"));
				product.setProductScale(resultSet.getString("productScale"));
				product.setProductVendor(resultSet.getString("productVendor"));
				product.setBuyPrice(resultSet.getDouble("buyPrice"));
				product.setMSRP(resultSet.getDouble("MSRP"));

				productsList.add(product);
			}
		} catch (SQLException e) {
			System.out.println("Error al recuperar productos de la base de datos: " + e.getMessage());
		}
		return productsList;
	}

	/**
	 * Guarda un producto en la base de datos. Si el id del producto no existe, inserta un nuevo producto; si el id ya existe, lo actualiza, modificando sus atributos.
	 *
	 * @param product el producto a guardar.
	 * @return el producto guardado.
	 * @throws SQLException si ocurre un error al acceder a la base de datos.
	 */
	@Override
	public Product save(Product product) throws SQLException {
		if(!existsById(product.getProductCode())) {
			return insertProduct(product);
		}else {
			return updateProduct(product);
		}
	}

//		Metodo añadido para la inserción de un nuevo producto
	private Product insertProduct(Product product) throws SQLException {
		String SQL_INSERT_PRODUCT = "insert into products values (?,?,?,?,?,?,?,?,?);";

		try (Connection connection = ClassicModelsCP.getInstance().getConnection()) {
			connection.setAutoCommit(false);

			try(PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT_PRODUCT, Statement.RETURN_GENERATED_KEYS)) { // Este variable nos permite obtener el ID generado por la BD
				preparedStatement.setString(1, product.getProductCode());
				preparedStatement.setString(2, product.getProductName());
				preparedStatement.setString(3, product.getProductLine().getDisplayName());
				preparedStatement.setString(4, product.getProductScale());
				preparedStatement.setString(5, product.getProductVendor());
				preparedStatement.setString(6, product.getProductDescription());
				preparedStatement.setInt(7, product.getQuantityInStock());
				preparedStatement.setDouble(8, product.getBuyPrice());
				preparedStatement.setDouble(9, product.getMSRP());


				int rowsAffected = preparedStatement.executeUpdate();

				if (rowsAffected > 0) {
					try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) { // Obtiene las claves generadas
						if (generatedKeys.next()) {
							String generatedProductCode = generatedKeys.getString(1); // Obtener la primera clave generada
							product.setProductCode(generatedProductCode); // Asigna la clave generada al producto
						}
					}
					connection.commit();
					System.out.println("El producto ha sido insertado correctamente.");
				} else {
					System.out.println("No se insertó ninguna fila.");
					connection.rollback();
				}
			} catch (SQLException ex) {
				connection.rollback(); // Realiza rollback en caso de error
				throw ex; // Lanza excepción
			} finally {
				connection.setAutoCommit(true);
			}
		}
		return product;
	}


//		Metodo para actualizar un producto
	private Product updateProduct(Product product) throws SQLException {
		String SQL_UPDATE_PRODUCT = "UPDATE products SET productName = ?, productLine = ?, productScale = ?, productVendor = ?, productDescription = ?, quantityInStock = ?, buyPrice = ?, MSRP = ? WHERE productCode = ?;";

		try (Connection connection = ClassicModelsCP.getInstance().getConnection()) {
			connection.setAutoCommit(false);

			try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_PRODUCT)) {
				preparedStatement.setString(1, product.getProductName()); // Nombre del producto
				preparedStatement.setString(2, product.getProductLine().getDisplayName()); // Línea del producto
				preparedStatement.setString(3, product.getProductScale()); // Escala del producto
				preparedStatement.setString(4, product.getProductVendor()); // Vendedor del producto
				preparedStatement.setString(5, product.getProductDescription()); // Descripción del producto
				preparedStatement.setInt(6, product.getQuantityInStock()); // Cantidad en stock
				preparedStatement.setDouble(7, product.getBuyPrice()); // Precio de compra
				preparedStatement.setDouble(8, product.getMSRP()); // Precio sugerido
				preparedStatement.setString(9, product.getProductCode()); // Código del producto (en WHERE)

				int rowsAffected = preparedStatement.executeUpdate();
				System.out.println("Filas actualizadas: " + rowsAffected);

				connection.commit();
			} catch (SQLException ex) {
				connection.rollback();
				throw ex;

			}finally {
				connection.setAutoCommit(true);
			}
		}
		return product;

	}

	/**
	 * Elimina un producto de la base de datos por su ID, incluyendo las entradas en otras tablas que tengan una clave foranea con el ID del producto.
	 *
	 * @param productCode el ID del producto a eliminar, que será el 'productCode' en la BBDD.
	 */
	@Override
	public void deleteById(String productCode) {
		String deleteOrderDetailsSQL = "DELETE FROM orderdetails WHERE productCode = ?";
		String deleteProductSQL = "DELETE FROM products WHERE productCode = ?";

		try (Connection connection = ClassicModelsCP.getInstance().getConnection()) {
			try (PreparedStatement orderDetailsStatement = connection.prepareStatement(deleteOrderDetailsSQL)) {
				orderDetailsStatement.setString(1, productCode);
				orderDetailsStatement.executeUpdate();
			}

			try (PreparedStatement productStatement = connection.prepareStatement(deleteProductSQL)) {
				productStatement.setString(1, productCode);
				int affectedRows = productStatement.executeUpdate();

				if (affectedRows > 0) {
					System.out.println("El producto y sus referencias han sido eliminados exitosamente.");
				} else {
					System.out.println("No se encontró ningún producto con el id: " + productCode);
				}
			}
		} catch (SQLException e) {
			System.out.println("Error: " + e.getMessage());
		}
	}
}
