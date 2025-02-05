package com.example.prueba1.repositories;

import com.example.prueba1.entities.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, Integer> {

    // Busca todos los items del carrito de un cliente ordenados por nombre de producto.
    List<CartItem> findByCustomerCustomerIdOrderByProduct_NameAsc(int customerId);


    // Busca un item del carrito para un cliente y un producto.
    Optional<CartItem> findByCustomerCustomerIdAndProductProductId(int customerId, int productId);

    // Busca los cart items asociados a un cliente dado su id
    List<CartItem> findByCustomerCustomerId(int customerId);

    // Elimina todos los cart items de un cliente
    void deleteByCustomerCustomerId(int customerId);
}
