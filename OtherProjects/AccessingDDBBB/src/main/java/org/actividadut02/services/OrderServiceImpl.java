package org.actividadut02.services;

import org.actividadut02.dataaccess.OrderDataAccess;
import org.actividadut02.dto.CreateOrderDto;
import org.actividadut02.entities.Order;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class OrderServiceImpl implements OrderService {

    private final OrderDataAccess orderDataAccess;
    public OrderServiceImpl(OrderDataAccess orderDataAccess) {
        this.orderDataAccess = orderDataAccess;
    }


    @Override
    public Long count() {
        return orderDataAccess.count();
    }

    @Override
    public boolean existsById(int id) {
       return orderDataAccess.existsById(id);
    }

    @Override
    public Optional<Order> findById(int id) throws SQLException {
        return orderDataAccess.findById(id);
    }

    @Override
    public List<Order> findAll() {
        List<Order> orders = orderDataAccess.findAll();
        if(orders.isEmpty()){
            throw new RuntimeException("No existen pedidos");
        }

        return orders;
    }


    @Override
    public void deleteById(int id) throws SQLException {
        if(!orderDataAccess.existsById(id)) {
            throw new RuntimeException("El pedido no existe");
        }
          orderDataAccess.deleteById(id);

    }

    @Override
    public CreateOrderDto createOrder(CreateOrderDto createOrderDto) throws SQLException {
        orderDataAccess.createOrder(createOrderDto);
        return createOrderDto;
    }
}
