package com.example.prueba1.services;

import com.example.prueba1.dto.ProductDto;
import com.example.prueba1.dto.WishlistCreateDto;
import com.example.prueba1.dto.WishlistDto;
import com.example.prueba1.entities.Customer;
import com.example.prueba1.entities.Product;
import com.example.prueba1.entities.Wishlist;
import com.example.prueba1.repositories.CustomerRepository;
import com.example.prueba1.repositories.WishlistRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WishlistServiceImpl implements WishlistService {

    private final WishlistRepository wishlistRepository;
    private final CustomerRepository customerRepository;

    public WishlistServiceImpl(WishlistRepository wishlistRepository, CustomerRepository customerRepository) {
        this.wishlistRepository = wishlistRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    public List<WishlistDto> getWishlistsByCustomerId(int customerId) {
        List<Wishlist> wishlists = wishlistRepository.findByCustomerCustomerId(customerId);
        return wishlists.stream()
                .map(this::convertWishlistToDto)
                .collect(Collectors.toList());
    }

    @Override
    public WishlistDto createWishlist(int customerId, WishlistCreateDto wishlistCreateDto) {
        // Buscar el cliente
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new EntityNotFoundException("No se encontró el cliente con id " + customerId));


        // Crear la nueva lista de deseos y vincularla al cliente
        Wishlist wishlist = new Wishlist();
        wishlist.setName(wishlistCreateDto.getName());
        wishlist.setShared(wishlistCreateDto.isShared());
        wishlist.setCustomer(customer);

        Wishlist savedWishlist = wishlistRepository.save(wishlist);
        return convertWishlistToDto(savedWishlist);
    }

    @Override
    public void deleteWishlist(int wishlistId) {
        Wishlist wishlist = wishlistRepository.findById(wishlistId)
                .orElseThrow(() -> new EntityNotFoundException("No se encontró la lista de deseos con id " + wishlistId));

        // Verificar si la lista no está vacía
        if (wishlist.getProducts() != null && !wishlist.getProducts().isEmpty()) {
            throw new IllegalStateException("No se puede eliminar una lista de deseos que no está vacía");
        }

        wishlistRepository.delete(wishlist);
    }

    @Override
    public List<ProductDto> getProductsInWishlist(int wishlistId) {
        Wishlist wishlist = wishlistRepository.findById(wishlistId)
                .orElseThrow(() -> new EntityNotFoundException("No se encontró la lista de deseos con id " + wishlistId));

        // aquí se mapea la lista de productos.
        return wishlist.getProducts().stream()
                .map(this::convertProductToDto)
                .collect(Collectors.toList());
    }

    // Conversión de Wishlist a WishlistDto
    private WishlistDto convertWishlistToDto(Wishlist wishlist) {
        WishlistDto dto = new WishlistDto();
        dto.setWishlistId(wishlist.getWishlistId());
        dto.setName(wishlist.getName());
        dto.setShared(wishlist.isShared());
        return dto;
    }

    // Conversión de Product a ProductDto
    private ProductDto convertProductToDto(Product product) {
        ProductDto dto = new ProductDto();
        dto.setProductId(product.getProductId());
        dto.setName(product.getName());
        dto.setPrice(product.getPrice());
        dto.setStock(product.getStock());
        dto.setSku(product.getSku());
        dto.setDescription(product.getDescription());
        dto.setCategories(null);
        return dto;
    }
}
