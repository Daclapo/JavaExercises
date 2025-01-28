package com.example.prueba1.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private int orderId;

    // Clave foránea
    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @Column(name = "order_total", nullable = false)
    private double orderTotal;

    @Column(name = "order_date", nullable = false)
    @ColumnDefault("CURRENT_TIMESTAMP")
    private LocalDateTime orderDate;


    @OneToOne(mappedBy = "order", optional = true)
    private Shipment shipment;

}