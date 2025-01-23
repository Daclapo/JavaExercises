package es.davidclarkson.practicas.act2ut5.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "wishlists")
public class Wishlist {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "wishlist_id")
	private int wishlistId;

	// Clave foránea
	@ManyToOne
	@JoinColumn(name = "customer_id", nullable = false)
	private Customer customer;

	@Column(name = "shared", nullable = false)
	private boolean shared;

	@Column(name = "name", nullable = false)
	private String name;

}
