-- Insertar categorías
INSERT INTO categories (name, description) VALUES
                                               ('Electrónica', 'Productos de tecnología avanzada'),
                                               ('Hogar', 'Artículos para el hogar'),
                                               ('Deporte', 'Equipos y accesorios deportivos'),
                                               ('Moda', 'Ropa y accesorios de moda');

-- Insertar productos
INSERT INTO products (name, description, price, stock, sku) VALUES
                                                                ('Smartphone X', 'Teléfono móvil inteligente', 699.99, 50, 'SKU001'),
                                                                ('Portátil Pro', 'Ordenador portátil de alto rendimiento', 999.99, 30, 'SKU002'),
                                                                ('Smart TV 55"', 'Televisión 4K con Android TV', 499.99, 20, 'SKU003'),
                                                                ('Aspiradora Robot', 'Aspiradora automática con mapeo', 299.99, 15, 'SKU004'),
                                                                ('Cafetera Premium', 'Cafetera espresso automática', 149.99, 40, 'SKU005'),
                                                                ('Camiseta Running', 'Camiseta técnica transpirable', 29.99, 100, 'SKU006'),
                                                                ('Raqueta Tenis', 'Raqueta profesional carbono', 79.99, 25, 'SKU007'),
                                                                ('Zapatillas Running', 'Zapatillas con amortiguación', 89.99, 70, 'SKU008'),
                                                                ('Bolso Elegante', 'Bolso de cuero genuino', 199.99, 10, 'SKU009'),
                                                                ('Reloj Clásico', 'Reloj analógico de acero inoxidable', 149.99, 20, 'SKU010'),
                                                                ('Auriculares Inalámbricos', 'Cancelación de ruido activa', 199.99, 35, 'SKU011'),
                                                                ('Tablet 10"', 'Tablet con lápiz digital', 399.99, 40, 'SKU012'),
                                                                ('Altavoz Bluetooth', 'Sonido 360° con bass boost', 129.99, 60, 'SKU013'),
                                                                ('Mochila Trekking', 'Mochila impermeable 30L', 59.99, 45, 'SKU014'),
                                                                ('Gafas De Sol', 'Protección UV400 polarizada', 89.99, 80, 'SKU015'),
                                                                ('Silla Oficina', 'Ergonómica con soporte lumbar', 199.99, 25, 'SKU016'),
                                                                ('Bicicleta Montaña', 'Cuadro de aluminio 21 velocidades', 499.99, 10, 'SKU017'),
                                                                ('Horno Eléctrico', 'Horno convección 35L', 129.99, 30, 'SKU018'),
                                                                ('Set Herramientas', 'Juego de 24 piezas profesionales', 89.99, 50, 'SKU019'),
                                                                ('Cámara DSLR', 'Cámara 24MP con lente 18-55mm', 599.99, 15, 'SKU020');

-- Relaciones ManyToMany (usando product_categories)
INSERT INTO product_categories (product_id, category_id) VALUES
-- 10 productos en 1 categoría
(1, 1), (2, 1), (3, 1), (11, 1), (12, 1), (13, 1), (17, 1), (18, 1), (19, 1), (20, 1),
-- 10 productos en 2 categorías
(4, 2), (4, 3), (5, 2), (5, 4), (6, 3), (6, 4), (7, 3), (7, 1), (8, 3), (8, 4), (9, 4), (9, 2), (10, 4), (10, 1), (14, 3), (14, 4), (15, 4), (15, 1), (16, 2), (16, 3);
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