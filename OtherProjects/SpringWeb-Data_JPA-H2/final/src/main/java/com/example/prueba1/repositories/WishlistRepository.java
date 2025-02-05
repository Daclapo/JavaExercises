package com.example.prueba1.repositories;

import com.example.prueba1.entities.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;


public interface WishlistRepository extends JpaRepository<Wishlist, Integer> {
    // Recupera todas las listas de deseos de un cliente por el id del cliente
    List<Wishlist> findByCustomerCustomerId(int customerId);
}

