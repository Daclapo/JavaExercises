package org.actividadut02.dataaccess;

import org.actividadut02.dto.CreateOrderDto;
import org.actividadut02.entities.Order;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface OrderDataAccess {

    Long count();

    boolean existsById(int id);

    Optional<Order> findById(int id) throws SQLException;

    List<Order> findAll();

    void deleteById(int id) throws SQLException;

   void createOrder(CreateOrderDto createOrderDto) throws SQLException;

}
