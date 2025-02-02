package com.example.prueba1.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

// Clase intermedia many-to-many entre Products(product_id) y Orders(order_id)
// @Getter
// @Setter
@Entity
@Table(name = "order_items", uniqueConstraints = @UniqueConstraint(columnNames = {"order_id", "product_id"}))
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int orderItemId;

    // Clave foránea
    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;



    // Clave foránea
    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;
    // private Product productIdOrderItem;


    @Column(nullable = false)
    private int quantity;


    @Column(nullable = false) // precision = 5, scale = 3 | No se como interpretar el diagrama, por lo que lo el valor dejo por defecto
    private double unitPrice;


    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public int getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(int orderItemId) {
        this.orderItemId = orderItemId;
    }
}