package es.davidclarkson.practicas.act2ut5v1.v1.entities;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.sql.Timestamp;

@Table(name = "shipments")
public class Shipment {

	@Id
	@GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
	@Column(name = "shipment_id")
	private int shipmentId;

	// Clave foránea
	private int orderId;

	@Column(name = "shipment_date", nullable = false)
	private Timestamp shipmentDate;

	@Column(name = "zip_code", nullable = false)
	private String zipCode;

	@Column(name = "city", nullable = false)
	private String city;

	@Column(name = "state", nullable = false)
	private String state;

	@Column(name = "address", nullable = false)
	private String address;
}
