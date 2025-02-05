package com.example.prueba1.dto;

import java.time.LocalDateTime;

public class ShipmentResponseDto {
    private int shipmentId;
    private LocalDateTime shipmentDate;
    private String address;
    private String city;
    private String state;
    private String zipCode;

    public ShipmentResponseDto() {
    }

    public int getShipmentId() {
        return shipmentId;
    }
    public void setShipmentId(int shipmentId) {
        this.shipmentId = shipmentId;
    }
    public LocalDateTime getShipmentDate() {
        return shipmentDate;
    }
    public void setShipmentDate(LocalDateTime shipmentDate) {
        this.shipmentDate = shipmentDate;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public String getState() {
        return state;
    }
    public void setState(String state) {
        this.state = state;
    }
    public String getZipCode() {
        return zipCode;
    }
    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }
}
