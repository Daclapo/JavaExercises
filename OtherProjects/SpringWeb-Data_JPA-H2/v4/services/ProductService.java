package com.example.prueba1.services;

import com.example.prueba1.dto.ProductDto;
import org.springframework.data.domain.Page;

public interface ProductService {
    Page<ProductDto> getPaginatedProducts(int page, int pageSize);
    Page<ProductDto> searchProducts(String query, int page, int pageSize);
}
