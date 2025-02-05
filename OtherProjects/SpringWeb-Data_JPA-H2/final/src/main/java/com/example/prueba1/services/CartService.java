package com.example.prueba1.services;

import com.example.prueba1.dto.AddCartItemDto;
import com.example.prueba1.dto.CartDto;

public interface CartService {

    CartDto getCart(int customerId);
    CartDto addProductToCart(int customerId, AddCartItemDto addCartItemDto);
    CartDto removeProductFromCart(int customerId, int productId);
    CartDto emptyCart(int customerId);
}
