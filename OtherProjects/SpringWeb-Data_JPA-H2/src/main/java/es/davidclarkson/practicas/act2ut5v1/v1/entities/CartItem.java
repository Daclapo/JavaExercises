package es.davidclarkson.practicas.act2ut5v1.v1.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


// Clase intermedia many-to-many entre Customer(customer_id) y Product(product_id)
@Getter
@Setter
@Entity
@Table(name = "cart_items")
public class CartItem {
	@Id
	@GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
	@Column(name = "cart_item_id")
	private int cartItemId;

	// Clave foránea
	@OneToOne
	@JoinColumn(name = "customer_id", nullable = false)
	private Customer customer;

	// Clave foránea
	@OneToMany
	@JoinColumn(name = "product_id", nullable = false)
	private List<Product> product;

	@Column(name = "quantity", nullable = false)
	private int quantity;

}
