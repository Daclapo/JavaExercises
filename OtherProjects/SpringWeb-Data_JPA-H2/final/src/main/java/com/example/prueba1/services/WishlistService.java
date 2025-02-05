package com.example.prueba1.services;

import com.example.prueba1.dto.ProductDto;
import com.example.prueba1.dto.WishlistCreateDto;
import com.example.prueba1.dto.WishlistDto;
import com.example.prueba1.entities.Wishlist;

import java.util.List;


public interface WishlistService {
    List<WishlistDto> getWishlistsByCustomerId(int customerId);
    WishlistDto createWishlist(int customerId, WishlistCreateDto wishlistCreateDto);
    void deleteWishlist(int wishlistId);
    List<ProductDto> getProductsInWishlist(int wishlistId);
}