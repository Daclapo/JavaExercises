package es.davidclarkson.practicas.act2ut5v1.v1.entities;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


// Clase intermedia many-to-many entre Customer(customer_id) y Product(product_id)
@Table(name = "cart_items")
public class CartItem {
	@Id
	@GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
	@Column(name = "cart_item_id")
	private int cartItemId;

	// Clave foránea
	private int customerId;

	// Clave foránea
	// @ForeignKey(name = "product_id")
	private int productId;

	@Column(name = "quantity", nullable = false)
	private int quantity;

}
