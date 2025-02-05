package com.example.prueba1.repositories;

import com.example.prueba1.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ProductRepository extends JpaRepository<Product, Integer> {

    // Devuelve una pagina de productos ordenados por nombre.
    Page<Product> findAllByOrderByNameAsc(Pageable pageable);

    // Devuelve una pagina de productos que contengan en su nombre o descripcion la cadena que pone el usuario.
    Page<Product> findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(String name, String description, Pageable pageable);

}