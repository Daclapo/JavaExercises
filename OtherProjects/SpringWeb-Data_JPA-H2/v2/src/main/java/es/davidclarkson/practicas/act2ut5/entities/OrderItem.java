package es.davidclarkson.practicas.act2ut5.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

// Clase intermedia many-to-many entre Products(product_id) y Orders(order_id)
@Getter
@Setter
@Entity
@Table(name = "order_items", uniqueConstraints = @UniqueConstraint(columnNames = {"order_id", "product_id"}))
public class OrderItem {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "order_item_id")
	private int orderItemId;

	// Clave foránea
	@ManyToOne
	@JoinColumn(name = "order_id", nullable = false)
	private Order order;



	// Clave foránea
	@ManyToOne
	@JoinColumn(name = "product_id", nullable = false)
	private Product product;
	// private Product productIdOrderItem;


	@Column(name = "quantity", nullable = false)
	private int quantity;

	@Column(name = "unit_price", nullable = false)
	private double unitPrice;
}
