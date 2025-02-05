package com.example.prueba1.services;

import com.example.prueba1.dto.OrderResponseDto;
import com.example.prueba1.dto.ShipmentResponseDto;
import com.example.prueba1.dto.ShipmentDto;

public interface OrderService {
    OrderResponseDto completeOrder(int customerId);
    ShipmentResponseDto sendOrder(int orderId, ShipmentDto shipmentDto);
}
