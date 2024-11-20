package org.actividadut02.dataaccess;

import org.actividadut02.dto.CreateOrderDetailDto;
import org.actividadut02.dto.CreateOrderDto;
import org.actividadut02.entities.Customer;
import org.actividadut02.entities.Order;

import java.sql.*;
import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

public class OrderDataAccessImpl implements OrderDataAccess {



    private static  final Customer customer = new Customer();
    /**
     * @return numero de consultas de Order
     */
    @Override
    public Long count() {
        String sql = "select count(*) from orders;";
        try (Connection connection = ClassicModelsCP.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return resultSet.getLong(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * Verificación de existencia por su id
     * @param id
     * @return boolean de existencia
     */
    @Override
    public boolean existsById(int id) {
        String sql = "select count(*) from orders where orderNumber = ?;";
        try (Connection connection = ClassicModelsCP.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int count = resultSet.getInt(1);
                    return count > 0;
//                    return resultSet.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
        return false;
    }

    /**
     * Busqueda de Order por su id
     * @param id
     * @return Optional<Order>
     * @throws SQLException
     */
    @Override
    public Optional<Order> findById(int id) throws SQLException {
        String sql = "select * from orders where orderNumber = ?;";
        Order order = null;
        try(Connection connection = ClassicModelsCP.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, id);
            try(ResultSet resultSet = preparedStatement.executeQuery()) {
                if(resultSet.next()) {
                     order =  new Order();
                    order.setOrderNumber(resultSet.getInt("orderNumber"));

//                    Manejo seguro de fechas
                    Date orderDateSql = resultSet.getDate("orderDate");
                    order.setOrderDate(orderDateSql != null ? orderDateSql.toLocalDate() : null);
//                    Si la FechaPedido no es null se convierte a LocalDate sino el campo se establece en null

                    Date requiredDateSql = resultSet.getDate("requiredDate");
                    order.setRequiredDate(requiredDateSql != null ? requiredDateSql.toLocalDate() : null);

                    Date shippedDateSql = resultSet.getDate("shippedDate");
                    order.setShippedDate(shippedDateSql != null ? shippedDateSql.toLocalDate() : null);

                    order.setStatus(resultSet.getString("status"));
                    order.setComments(resultSet.getString("comments"));

                    int customerNumber = resultSet.getInt("customerNumber");
                    Customer customer = new Customer();

                    customer.setCustomerNumber(customerNumber);
                    order.setCustomerNumber(customer);

                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error: " + e.getMessage());

        }
        return Optional.ofNullable(order);
    }


    /**
     * @return orderlist
     */
    @Override
    public List<Order> findAll() {
        List<Order> ordersList = new ArrayList<>();
        String sql = "select*from orders;";

        try(Connection connection = ClassicModelsCP.getInstance().getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while(resultSet.next()) {
                var order =  new Order();
                order.setOrderNumber(resultSet.getInt("orderNumber"));

//                Manejo seguro de fechas
                Date orderDateSql = resultSet.getDate("orderDate");

                order.setOrderDate(orderDateSql != null ? orderDateSql.toLocalDate() : null);

                Date requiredDateSql = resultSet.getDate("requiredDate");
                order.setRequiredDate(requiredDateSql != null ? requiredDateSql.toLocalDate() : null);

                Date shippedDateSql = resultSet.getDate("shippedDate");
                order.setShippedDate(shippedDateSql != null ? shippedDateSql.toLocalDate() : null);

                order.setStatus(resultSet.getString("status"));
                order.setComments(resultSet.getString("comments"));
                int customerNumber = resultSet.getInt("customerNumber");

                customer.setCustomerNumber(customerNumber);
                order.setCustomerNumber(customer);
                ordersList.add(order);

            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al recuperar pedidos de la DB" + e.getMessage());
        }
        return ordersList;
    }



    @Override
    public void deleteById(int id) throws SQLException {
        String sql = "delete from orders where orderNumber = ?;";

        try (Connection connection = ClassicModelsCP.getInstance().getConnection()) {
            connection.setAutoCommit(false);

            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

                preparedStatement.setInt(1, id);

                int rowsAffected = preparedStatement.executeUpdate();

                if (rowsAffected > 0) {
                    connection.commit();
                    System.out.println("Se ha eliminado el pedido con el id: " + id);
                } else {
                    System.out.println("No se ha eliminado el pedido con el id: " + id);
                    connection.rollback();
                }
            } catch (SQLException e) {
                System.out.println("Error: " + e.getMessage());
                connection.rollback();
            } finally {
                connection.setAutoCommit(true);
            }

        }
    }

    /**
     * Concepto: Vamos a insertar un Order en la base de datos
     * //     * @param createOrderDto
     *
     * @throws SQLException
     */

    @Override
    public void createOrder(CreateOrderDto createOrderDto) {
        String SQL_INSERT_ORDER = "INSERT INTO orders (orderDate, requiredDate, status, customerNumber) VALUES (current_timestamp(), ?, 'In Process', ?);";
        String SQL_INSERT_ORDERDETAILS = "INSERT INTO orderdetails (orderNumber, productCode, quantityOrdered, priceEach, orderLineNumber) VALUES (?, ?, ?, ?,?);";

        try (Connection connection = ClassicModelsCP.getInstance().getConnection()) {
            connection.setAutoCommit(false); // Iniciamos la transacción

            try (PreparedStatement orderStatement = connection.prepareStatement(SQL_INSERT_ORDER, Statement.RETURN_GENERATED_KEYS);
                 PreparedStatement orderDetailsStatement = connection.prepareStatement(SQL_INSERT_ORDERDETAILS)) {


                orderStatement.setDate(1, java.sql.Date.valueOf(createOrderDto.requiredDate != null ? createOrderDto.requiredDate : LocalDate.now()));
                orderStatement.setInt(2, createOrderDto.customerNumber);


                orderStatement.executeUpdate();
                try (ResultSet rs = orderStatement.getGeneratedKeys()) {
                    if (rs.next()) {
                        int generatedOrderNumber = rs.getInt(1);

                        // Insertamos cada detalle en orderdetails
                        for (CreateOrderDetailDto orderDetail : createOrderDto.orderDetails) {
                            orderDetailsStatement.setInt(1, generatedOrderNumber); // Asigna orderNumber en orderdetails
                            orderDetailsStatement.setString(2, orderDetail.getProductCode());
                            orderDetailsStatement.setInt(3, orderDetail.getQuantityOrdered());
                            orderDetailsStatement.setDouble(4, calculatePriceEach(orderDetail.getProductCode())); // Calcula precio
                            orderDetailsStatement.setInt(5, orderDetail.getOrderLineNumber());
                            orderDetailsStatement.addBatch();
                        }
                        orderDetailsStatement.executeBatch(); // Ejecuta el batch
                        connection.commit(); // Confirma la transacción
                        System.out.println("Pedido y detalles creados con éxito.");
                    } else {
                        throw new SQLException("No se pudo generar el orderNumber.");
                    }
                }
            } catch (SQLException e) {
                connection.rollback();
                throw new RuntimeException("Error al crear el pedido: " + e.getMessage(), e);
            } finally {
                connection.setAutoCommit(true); // Restablece autocommit
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error en la conexión: " + e.getMessage(), e);
        }
    }

    // Método auxiliar para calcular el precio unitario de un producto
    private double calculatePriceEach(String productCode) {

        return 29.99;
    }

}
