package com.example.prueba1.services;


import com.example.prueba1.dto.ProductDto;
import com.example.prueba1.dto.CategoryDto;
import com.example.prueba1.entities.Product;
import com.example.prueba1.entities.Category;
import com.example.prueba1.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Page<ProductDto> getPaginatedProducts(int page, int pageSize) {
        Page<Product> productsPage = productRepository.findAllByOrderByNameAsc(PageRequest.of(page, pageSize));
        return productsPage.map(this::convertToDto);
    }

    @Override
    public Page<ProductDto> searchProducts(String query, int page, int pageSize) {
        Page<Product> productsPage = productRepository.findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(
                query, query, PageRequest.of(page, pageSize));
        return productsPage.map(this::convertToDto);
    }

    // Convertir una entidad Product a ProductDto
    private ProductDto convertToDto(Product product) {
        ProductDto dto = new ProductDto();
        dto.setProductId(product.getProductId());
        dto.setName(product.getName());
        dto.setPrice(product.getPrice());
        dto.setStock(product.getStock());
        dto.setSku(product.getSku());
        dto.setDescription(product.getDescription());

        // Convertir la lista de Category a una lista de CategoryDto
        List<CategoryDto> categories = product.getCategories().stream()
                .map(this::convertCategoryToDto)
                .collect(Collectors.toList());
        dto.setCategories(categories);

        return dto;
    }

    // Convertir una entidad Category a CategoryDto
    private CategoryDto convertCategoryToDto(Category category) {
        CategoryDto dto = new CategoryDto();
        dto.setCategoryId(category.getCategoryId());
        dto.setName(category.getName());
        return dto;
    }
}
