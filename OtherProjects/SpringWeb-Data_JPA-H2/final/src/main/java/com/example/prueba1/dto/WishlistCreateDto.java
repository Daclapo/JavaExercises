package com.example.prueba1.dto;

public class WishlistCreateDto {
    private String name;
    private boolean shared;

    public WishlistCreateDto() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isShared() {
        return shared;
    }

    public void setShared(boolean shared) {
        this.shared = shared;
    }
}
