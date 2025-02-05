package com.example.prueba1.services;

import com.example.prueba1.dto.CartDto;
import com.example.prueba1.dto.CartItemDto;
import com.example.prueba1.dto.AddCartItemDto;
import com.example.prueba1.entities.CartItem;
import com.example.prueba1.entities.Customer;
import com.example.prueba1.entities.Product;
import com.example.prueba1.repositories.CartItemRepository;
import com.example.prueba1.repositories.CustomerRepository;
import com.example.prueba1.repositories.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartService {

    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final CustomerRepository customerRepository;

    public CartServiceImpl(CartItemRepository cartItemRepository,
                           ProductRepository productRepository,
                           CustomerRepository customerRepository) {
        this.cartItemRepository = cartItemRepository;
        this.productRepository = productRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    public CartDto getCart(int customerId) {
        List<CartItem> items = cartItemRepository.findByCustomerCustomerIdOrderByProduct_NameAsc(customerId);
        List<CartItemDto> itemDtos = items.stream()
                .map(this::convertToCartItemDto)
                .collect(Collectors.toList());
        double total = itemDtos.stream().mapToDouble(CartItemDto::getSubtotal).sum();
        CartDto cartDto = new CartDto();
        cartDto.setItems(itemDtos);
        cartDto.setTotalAmount(total);
        return cartDto;
    }

    @Transactional
    @Override
    public CartDto addProductToCart(int customerId, AddCartItemDto addCartItemDto) {
        Optional<CartItem> optionalCartItem = cartItemRepository.findByCustomerCustomerIdAndProductProductId(customerId, addCartItemDto.getProductId());
        CartItem cartItem;
        if (optionalCartItem.isPresent()) {

            // El producto ya está en el carrito, por lo que se imcrementa la cantidad
            cartItem = optionalCartItem.get();
            cartItem.setQuantity(cartItem.getQuantity() + addCartItemDto.getQuantity());
        } else {
            // Se crea un CartItem nuevo
            Customer customer = customerRepository.findById(customerId)
                    .orElseThrow(() -> new RuntimeException("Customer not found"));
            Product product = productRepository.findById(addCartItemDto.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));
            cartItem = new CartItem();
            cartItem.setCustomer(customer);
            cartItem.setProduct(product);
            cartItem.setQuantity(addCartItemDto.getQuantity());
        }
        cartItemRepository.save(cartItem);
        return getCart(customerId);
    }

    @Transactional
    @Override
    public CartDto removeProductFromCart(int customerId, int productId) {
        Optional<CartItem> optionalCartItem = cartItemRepository.findByCustomerCustomerIdAndProductProductId(customerId, productId);
        optionalCartItem.ifPresent(cartItemRepository::delete);
        return getCart(customerId);
    }

    @Transactional
    @Override
    public CartDto emptyCart(int customerId) {
        List<CartItem> items = cartItemRepository.findByCustomerCustomerIdOrderByProduct_NameAsc(customerId);
        cartItemRepository.deleteAll(items);
        return getCart(customerId);
    }

    // Metodo para convertir una entidad CartItem a CartItemDto.
    private CartItemDto convertToCartItemDto(CartItem cartItem) {
        CartItemDto dto = new CartItemDto();
        Product product = cartItem.getProduct();
        dto.setProductId(product.getProductId());
        dto.setProductName(product.getName());
        dto.setProductPrice(product.getPrice());
        dto.setQuantity(cartItem.getQuantity());
        dto.setSubtotal(product.getPrice() * cartItem.getQuantity());
        return dto;
    }
}
