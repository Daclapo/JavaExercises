package com.example.prueba1.controllers;

import com.example.prueba1.dto.CartDto;
import com.example.prueba1.dto.AddCartItemDto;
import com.example.prueba1.services.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    // Listado del carrito
    @GetMapping("/{customerId}")
    public ResponseEntity<CartDto> getCart(@PathVariable int customerId) {
        CartDto cart = cartService.getCart(customerId);
        return ResponseEntity.ok(cart);
    }

    // Añadir un producto al carrito
    @PostMapping("/{customerId}")
    public ResponseEntity<CartDto> addProductToCart(@PathVariable int customerId,
                                                    @RequestBody AddCartItemDto addCartItemDto) {
        CartDto cart = cartService.addProductToCart(customerId, addCartItemDto);
        return ResponseEntity.ok(cart);
    }

    // Quitar un producto del carrito
    @DeleteMapping("/{customerId}/{productId}")
    public ResponseEntity<CartDto> removeProductFromCart(@PathVariable int customerId,
                                                         @PathVariable int productId) {
        CartDto cart = cartService.removeProductFromCart(customerId, productId);
        return ResponseEntity.ok(cart);
    }

    // Vaciar el carrito
    @PostMapping("/empty/{customerId}")
    public ResponseEntity<CartDto> emptyCart(@PathVariable int customerId) {
        CartDto cart = cartService.emptyCart(customerId);
        return ResponseEntity.ok(cart);
    }
}

