package com.example.prueba1.controllers;

import com.example.prueba1.entities.Product;
import com.example.prueba1.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/{page}/{pageSize}")
    public Page<Product> getProducts(@PathVariable int page, @PathVariable int pageSize) {
        return productService.getProducts(page, pageSize);
    }

    @GetMapping("/search/{query}/{page}/{pageSize}")
    public Page<Product> searchProducts(@PathVariable String query, @PathVariable int page, @PathVariable int pageSize) {
        return productService.searchProducts(query, page, pageSize);
    }
}
