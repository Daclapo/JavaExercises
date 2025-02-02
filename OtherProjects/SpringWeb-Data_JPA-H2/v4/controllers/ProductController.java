package com.example.prueba1.controllers;

import com.example.prueba1.dto.ProductDto;
import com.example.prueba1.services.ProductService;
import com.example.prueba1.services.ProductServiceImpl;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
public class ProductController {


    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // Endpoint para obtener productos paginados ordenados por nombre
    @GetMapping("/{page}/{pageSize}")
    public ResponseEntity<Page<ProductDto>> getPaginatedProducts(@PathVariable int page,
                                                                 @PathVariable int pageSize) {
        Page<ProductDto> productPage = productService.getPaginatedProducts(page, pageSize);
        return ResponseEntity.ok(productPage);
    }

    // Endpoint para buscar productos por query en nombre o descripción
    @GetMapping("/search/{query}/{page}/{pageSize}")
    public ResponseEntity<Page<ProductDto>> searchProducts(@PathVariable String query,
                                                           @PathVariable int page,
                                                           @PathVariable int pageSize) {
        Page<ProductDto> productPage = productService.searchProducts(query, page, pageSize);
        return ResponseEntity.ok(productPage);
    }
}
