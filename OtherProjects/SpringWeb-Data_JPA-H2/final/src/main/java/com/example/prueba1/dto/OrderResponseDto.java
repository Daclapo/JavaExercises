package com.example.prueba1.dto;

import java.time.LocalDateTime;

public class OrderResponseDto {
    private int orderId;
    private double orderTotal;
    private LocalDateTime orderDate;
    private ShipmentResponseDto shipment;

    public OrderResponseDto() {
    }

    public int getOrderId() {
        return orderId;
    }
    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }
    public double getOrderTotal() {
        return orderTotal;
    }
    public void setOrderTotal(double orderTotal) {
        this.orderTotal = orderTotal;
    }
    public LocalDateTime getOrderDate() {
        return orderDate;
    }
    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }
    public ShipmentResponseDto getShipment() {
        return shipment;
    }
    public void setShipment(ShipmentResponseDto shipment) {
        this.shipment = shipment;
    }
}
