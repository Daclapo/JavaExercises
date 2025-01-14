package es.davidclarkson.practicas.act2ut5v1.v1.entities;

import jakarta.persistence.*;

import java.sql.Timestamp;

@Table(name = "orders")
public class Order {

	@Id
	@GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
	@Column(name = "order_id")
	private int orderId;

	// Clave foránea
	@ManyToOne
	@JoinColumn(name = "customer_id")
	private Customer customerId;

	@Column(name = "order_total", nullable = false)
	private double orderTotal;

	@Column(name = "order_date", nullable = false)
	private Timestamp orderDate;

}
