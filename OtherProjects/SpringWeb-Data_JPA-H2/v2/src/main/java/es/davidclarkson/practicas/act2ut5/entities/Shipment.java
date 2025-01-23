package es.davidclarkson.practicas.act2ut5.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDateTime;

@Setter
@Getter
@Entity
@Table(name = "shipments")
public class Shipment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "shipment_id")
	private int shipmentId;

	// Clave foránea
	@OneToOne
	@JoinColumn(name = "order_id")
	private Order order;

	@Column(name = "shipment_date", nullable = false)
	@ColumnDefault("CURRENT_TIMESTAMP")
	private LocalDateTime shipmentDate = LocalDateTime.now();

	@Column(name = "zip_code", nullable = false)
	private String zipCode;

	@Column(name = "city", nullable = false)
	private String city;

	@Column(name = "state", nullable = false)
	private String state;

	@Column(name = "address", nullable = false)
	private String address;
}
