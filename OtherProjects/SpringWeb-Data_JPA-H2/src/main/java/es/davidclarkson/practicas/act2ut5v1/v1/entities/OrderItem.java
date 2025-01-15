package es.davidclarkson.practicas.act2ut5v1.v1.entities;

import jakarta.persistence.*;

// Clase intermedia many-to-many entre Products(product_id) y Orders(order_id)
@Entity
@Table(name = "order_items")
public class OrderItem {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "order_item_id")
	private int orderItemId;

	// Clave foránea
	private int orderId;

	// Clave foránea
	@ManyToMany
	private Product productId;

	@Column(name = "quantity", nullable = false)
	private int quantity;

	@Column(name = "unit_price", nullable = false)
	private double unitPrice;
}
