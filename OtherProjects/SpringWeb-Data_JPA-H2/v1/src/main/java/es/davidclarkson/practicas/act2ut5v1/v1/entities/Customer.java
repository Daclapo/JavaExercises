package es.davidclarkson.practicas.act2ut5v1.v1.entities;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "customers")
public class Customer {

	@Id
	@GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
	@Column(name = "customer_id")
	private int customerId;

	private String phoneNumber;

	@Column(nullable = false)
	private String email;

	@Column(nullable = false)
	private String password;

	@Column(nullable = false)
	private String firstName;

	@Column(nullable = false)
	private String lastName;

	@Column(nullable = false)
	private String address;


	@OneToMany(mappedBy = "customer")
	private List<Order> orders;

	@OneToMany(mappedBy = "customer")
	private List<Wishlist> wishlists;

}
