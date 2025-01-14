package es.davidclarkson.practicas.act2ut5v1.v1.entities;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Table(name = "wishlists")
public class Wishlist {

	@Id
	@GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
	@Column(name = "wishlist_id")
	private int wishlistId;

	// Clave foránea
	private int customerId;

	@Column(name = "shared", nullable = false)
	private boolean shared;

	@Column(name = "name", nullable = false)
	private String name;

}
