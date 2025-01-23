package es.davidclarkson.practicas.act2ut5.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


// Clase intermedia many-to-many entre Customer(customer_id) y Product(product_id)
@Getter
@Setter
@Entity
@Table(name = "cart_items", uniqueConstraints = @UniqueConstraint(columnNames = {"customer_id", "product_id"}))
public class CartItem {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cart_item_id")
	private int cartItemId;

	// Clave foránea
	@ManyToOne
	@JoinColumn(name = "customer_id", nullable = false)
	private Customer customer;

	// Clave foránea
	@ManyToOne
	@JoinColumn(name = "product_id", nullable = false)
	private Product product;

	@Column(name = "quantity", nullable = false)
	private int quantity;

}
