package com.example.prueba1.dto;

public class AddCartItemDto {
    private int productId;
    private int quantity;

    public AddCartItemDto() {}

    public int getProductId() {
        return productId;
    }
    public void setProductId(int productId) {
        this.productId = productId;
    }
    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
