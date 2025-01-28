package com.example.prueba1.services;

import com.example.prueba1.entities.Product;
import com.example.prueba1.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public Page<Product> getProducts(int page, int pageSize) {
        return productRepository.findAll(PageRequest.of(page, pageSize));
    }

    public Page<Product> searchProducts(String query, int page, int pageSize) {
        // Implementar lógica de búsqueda aquí
        // return productRepository.findByNameContainingOrDescriptionContaining(query, query, PageRequest.of(page, pageSize));
        return (Page<Product>) productRepository.findAll();
    }
}