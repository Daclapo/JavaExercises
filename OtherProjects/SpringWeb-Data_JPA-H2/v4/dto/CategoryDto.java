package com.example.prueba1.dto;

public class CategoryDto {
    private int categoryId;
    private String name;

    // Constructor vacío
    public CategoryDto() {}

    // Constructor con parámetros
    public CategoryDto(int categoryId, String name) {
        this.categoryId = categoryId;
        this.name = name;
    }


    // Getters y Setters
    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
