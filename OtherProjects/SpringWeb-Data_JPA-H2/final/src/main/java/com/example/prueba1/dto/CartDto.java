package com.example.prueba1.dto;

import java.util.List;

public class CartDto {
    private List<CartItemDto> items;
    private double totalAmount;

    public CartDto() {}

    public List<CartItemDto> getItems() {
        return items;
    }
    public void setItems(List<CartItemDto> items) {
        this.items = items;
    }
    public double getTotalAmount() {
        return totalAmount;
    }
    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }
}