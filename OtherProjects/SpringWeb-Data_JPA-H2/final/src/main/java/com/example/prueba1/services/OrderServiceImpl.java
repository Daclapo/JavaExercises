package com.example.prueba1.services;

import com.example.prueba1.dto.OrderResponseDto;
import com.example.prueba1.dto.ShipmentDto;
import com.example.prueba1.dto.ShipmentResponseDto;
import com.example.prueba1.entities.CartItem;
import com.example.prueba1.entities.Order;
import com.example.prueba1.entities.OrderItem;
import com.example.prueba1.entities.Shipment;
import com.example.prueba1.entities.Customer;
import com.example.prueba1.entities.Product;
import com.example.prueba1.repositories.CartItemRepository;
import com.example.prueba1.repositories.CustomerRepository;
import com.example.prueba1.repositories.OrderItemRepository;
import com.example.prueba1.repositories.OrderRepository;
import com.example.prueba1.repositories.ProductRepository;
import com.example.prueba1.repositories.ShipmentRepository;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class OrderServiceImpl implements OrderService {

    private final CustomerRepository customerRepository;
    private final CartItemRepository cartItemRepository;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ShipmentRepository shipmentRepository;
    private final ProductRepository productRepository;

    public OrderServiceImpl(CustomerRepository customerRepository,
                            CartItemRepository cartItemRepository,
                            OrderRepository orderRepository,
                            OrderItemRepository orderItemRepository,
                            ShipmentRepository shipmentRepository,
                            ProductRepository productRepository) {
        this.customerRepository = customerRepository;
        this.cartItemRepository = cartItemRepository;
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.shipmentRepository = shipmentRepository;
        this.productRepository = productRepository;
    }

    @Override
    @Transactional
    public OrderResponseDto completeOrder(int customerId) {
        // Verificar que el cliente existe
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No existe el cliente con el id " + customerId));

        // Obtener los items del carrito del cliente
        List<CartItem> cartItems = cartItemRepository.findByCustomerCustomerId(customerId);
        if (cartItems.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El carrito de compra está vacío.");
        }

        // Verificar stock suficiente para cada producto
        for (CartItem item : cartItems) {
            Product product = item.getProduct();
            if (product.getStock() < item.getQuantity()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "No hay stock suficiente para el producto: " + product.getName());
            }
        }

        // Crear y persistir el pedido
        Order order = new Order();
        order.setCustomer(customer);
        order.setOrderDate(LocalDateTime.now());
        order = orderRepository.save(order);  // Guardar el pedido para que sea persistente

        double total = 0.0;
        for (CartItem item : cartItems) {
            Product product = item.getProduct();
            double unitPrice = product.getPrice(); // Precio obtenido de la BD
            total += unitPrice * item.getQuantity();

            // Creacion del OrderItem asociado al pedido
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(product);
            orderItem.setQuantity(item.getQuantity());
            orderItem.setUnitPrice(unitPrice);
            orderItemRepository.save(orderItem);

            // Reducir el stock del producto
            product.setStock(product.getStock() - item.getQuantity());
            productRepository.save(product);
        }
        order.setOrderTotal(total);
        order = orderRepository.save(order);  // Actualizar el pedido con el total

        // Vaciar el carrito del cliente
        cartItemRepository.deleteByCustomerCustomerId(customerId);

        // Mapear el pedido a DTO de respuesta
        OrderResponseDto responseDto = new OrderResponseDto();
        responseDto.setOrderId(order.getOrderId());
        responseDto.setOrderTotal(order.getOrderTotal());
        responseDto.setOrderDate(order.getOrderDate());
        // Como el pedido está pendiente de envío, el shipment es null
        responseDto.setShipment(null);

        return responseDto;
    }

    @Override
    @Transactional
    public ShipmentResponseDto sendOrder(int orderId, ShipmentDto shipmentDto) {
        // Buscar el pedido
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No existe el pedido con id " + orderId));

        // Verificar si el pedido ya tiene un envío asociado
        if (order.getShipment() != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El pedido " + orderId + " ya había sido enviado.");
        }

        // Crear el envío
        Shipment shipment = new Shipment();
        shipment.setOrder(order);
        shipment.setShipmentDate(LocalDateTime.now());
        shipment.setAddress(shipmentDto.getAddress());
        shipment.setCity(shipmentDto.getCity());
        shipment.setState(shipmentDto.getState());
        shipment.setZipCode(shipmentDto.getZipCode());
        shipment = shipmentRepository.save(shipment);

        // Asociar el envío al pedido
        order.setShipment(shipment);
        orderRepository.save(order);

        // Mapear el envío a DTO de respuesta
        ShipmentResponseDto shipmentResponse = new ShipmentResponseDto();
        shipmentResponse.setShipmentId(shipment.getShipmentId());
        shipmentResponse.setShipmentDate(shipment.getShipmentDate());
        shipmentResponse.setAddress(shipment.getAddress());
        shipmentResponse.setCity(shipment.getCity());
        shipmentResponse.setState(shipment.getState());
        shipmentResponse.setZipCode(shipment.getZipCode());

        return shipmentResponse;
    }
}
