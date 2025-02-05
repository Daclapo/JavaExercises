package com.example.prueba1.controllers;

import com.example.prueba1.dto.ProductDto;
import com.example.prueba1.dto.WishlistCreateDto;
import com.example.prueba1.dto.WishlistDto;
import com.example.prueba1.services.WishlistService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/wishlists")
public class WishlistController {

    private final WishlistService wishlistService;

    public WishlistController(WishlistService wishlistService) {
        this.wishlistService = wishlistService;
    }

    // Endpoint para obtener todas las listas de deseos de un cliente
    @GetMapping("/list/{customerId}")
    public ResponseEntity<List<WishlistDto>> getWishlistsByCustomer(@PathVariable int customerId) {
        List<WishlistDto> wishlists = wishlistService.getWishlistsByCustomerId(customerId);
        return ResponseEntity.ok(wishlists);
    }

    // Endpoint para crear una nueva lista de deseos para un cliente
    @PutMapping("/{customerId}")
    public ResponseEntity<WishlistDto> createWishlist(@PathVariable int customerId,
                                                      @RequestBody WishlistCreateDto wishlistCreateDto) {
        WishlistDto createdWishlist = wishlistService.createWishlist(customerId, wishlistCreateDto);
        return new ResponseEntity<>(createdWishlist, HttpStatus.CREATED);
    }

    // Endpoint para eliminar una lista de deseos (si está vacía)
    @DeleteMapping("/{wishlistId}")
    public ResponseEntity<Void> deleteWishlist(@PathVariable int wishlistId) {
        wishlistService.deleteWishlist(wishlistId);
        return ResponseEntity.ok().build();
    }

    // Endpoint para obtener los productos de una lista de deseos
    @GetMapping("/{wishlistId}")
    public ResponseEntity<List<ProductDto>> getProductsInWishlist(@PathVariable int wishlistId) {
        List<ProductDto> products = wishlistService.getProductsInWishlist(wishlistId);
        return ResponseEntity.ok(products);
    }
}
