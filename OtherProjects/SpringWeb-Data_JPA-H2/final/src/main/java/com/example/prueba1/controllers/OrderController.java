package com.example.prueba1.controllers;

import com.example.prueba1.dto.OrderResponseDto;
import com.example.prueba1.dto.ShipmentDto;
import com.example.prueba1.dto.ShipmentResponseDto;
import com.example.prueba1.services.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    // Endpoint para completar pedido
    @PostMapping("/create/{customerId}")
    public ResponseEntity<OrderResponseDto> completeOrder(@PathVariable int customerId) {
        OrderResponseDto orderDto = orderService.completeOrder(customerId);
        return ResponseEntity.status(HttpStatus.CREATED).body(orderDto);
    }

    // Endpoint para enviar pedido
    @PostMapping("/send/{orderId}")
    public ResponseEntity<ShipmentResponseDto> sendOrder(@PathVariable int orderId,
                                                         @RequestBody ShipmentDto shipmentDto) {
        ShipmentResponseDto shipmentResponse = orderService.sendOrder(orderId, shipmentDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(shipmentResponse);
    }
}
