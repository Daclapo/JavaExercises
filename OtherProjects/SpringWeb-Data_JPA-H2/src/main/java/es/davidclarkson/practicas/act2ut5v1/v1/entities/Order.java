package es.davidclarkson.practicas.act2ut5v1.v1.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "orders")
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "order_id")
	private int orderId;

	// Clave foránea
	@ManyToOne
	@JoinColumn(name = "customer_id")
	private Customer customerId;

	@Column(name = "order_total", nullable = false)
	private double orderTotal;

	@Column(name = "order_date", nullable = false)
	private LocalDateTime orderDate;

}
