-- Insertar categorías
INSERT INTO categories (category_id, description, name) VALUES
                                                            (1, 'Productos de tecnología avanzada', 'Electrónica'),
                                                            (2, 'Artículos para el hogar', 'Hogar'),
                                                            (3, 'Equipos y accesorios deportivos', 'Deporte'),
                                                            (4, 'Ropa y accesorios de moda', 'Moda');

-- Insertar productos
INSERT INTO products (product_id, price, stock, description, name, sku) VALUES
                                                                            (1, 699.99, 50, 'Teléfono móvil inteligente', 'Smartphone', 'SKU001'),
                                                                            (2, 999.99, 30, 'Ordenador portátil', 'Portátil', 'SKU002'),
                                                                            (3, 499.99, 20, 'Televisión inteligente', 'Smart TV', 'SKU003'),
                                                                            (4, 299.99, 15, 'Aspiradora robot', 'Aspiradora', 'SKU004'),
                                                                            (5, 149.99, 40, 'Cafetera espresso', 'Cafetera', 'SKU005'),
                                                                            (6, 29.99, 100, 'Camiseta deportiva', 'Camiseta deportiva', 'SKU006'),
                                                                            (7, 79.99, 25, 'Raqueta ligera de aluminio', 'Raqueta de tenis', 'SKU007'),
                                                                            (8, 89.99, 70, 'Zapatillas deportivas para correr', 'Zapatillas running', 'SKU008'),
                                                                            (9, 199.99, 10, 'Bolso elegante de cuero auténtico', 'Bolso de cuero', 'SKU009'),
                                                                            (10, 149.99, 20, 'Reloj clásico con correa de cuero', 'Reloj de pulsera', 'SKU010'),
                                                                            (11, 199.99, 35, 'Auriculares inalámbricos con cancelación de ruido', 'Auriculares', 'SKU011'),
                                                                            (12, 399.99, 40, 'Dispositivo portátil de pantalla táctil', 'Tablet', 'SKU012');

-- Relación ManyToMany: Productos y Categorías
INSERT INTO product_categories (product_id, category_id) VALUES
                                                             (1, 1), (2, 1), (3, 1), (4, 2), (5, 2), (6, 3),
                                                             (7, 3), (8, 3), (9, 4), (10, 4), (11, 1), (12, 1);

-- Insertar clientes
INSERT INTO customers (customer_id, address, email, first_name, last_name, password, phone_number) VALUES
                                                                                                       (1, 'Calle Falsa 123, Ciudad', 'maria.lopez@example.com', 'María', 'López', 'password1', '123456789'),
                                                                                                       (2, 'Av. Siempre Viva 742, Ciudad', 'juan.garcia@example.com', 'Juan', 'García', 'password2', '987654321'),
                                                                                                       (3, 'Paseo del Prado 5, Ciudad', 'ana.martinez@example.com', 'Ana', 'Martínez', 'password3', '456789123'),
                                                                                                       (4, 'Calle Mayor 10, Ciudad', 'luis.fernandez@example.com', 'Luis', 'Fernández', 'password4', '789123456');

-- Insertar un pedido para un cliente con tres productos distintos
INSERT INTO orders (order_id, customer_id, order_total, order_date) VALUES
    (1, 1, 1249.97, CURRENT_DATE);

INSERT INTO order_items (order_item_id, order_id, product_id, quantity, unit_price) VALUES
                                                                                        (1, 1, 1, 1, 699.99),
                                                                                        (2, 1, 3, 1, 499.99),
                                                                                        (3, 1, 5, 1, 149.99);

-- Insertar una lista de deseos para un cliente con tres productos
INSERT INTO wishlists (wishlist_id, customer_id, name, shared) VALUES
    (1, 2, 'Lista de deseos de Juan', false);

INSERT INTO wishlist_products (wishlist_id, product_id) VALUES
                                                            (1, 2), (1, 8), (1, 10);

-- Insertar productos en el carrito de un cliente
INSERT INTO cart_items (cart_item_id, customer_id, product_id, quantity) VALUES
                                                                             (1, 3, 2, 1),
                                                                             (2, 3, 6, 2),
                                                                             (3, 3, 11, 1),
                                                                             (4, 3, 12, 1);

-- Insertar un envío para un pedido
INSERT INTO shipments (shipment_id, order_id, shipment_date, address, city, state, zip_code) VALUES
    (1, 1, CURRENT_DATE, 'Calle Falsa 123', 'Ciudad', 'Estado', '12345');
