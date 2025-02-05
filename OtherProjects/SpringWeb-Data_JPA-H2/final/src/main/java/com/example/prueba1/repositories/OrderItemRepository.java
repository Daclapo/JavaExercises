package com.example.prueba1.repositories;

import com.example.prueba1.entities.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;


public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {
}
