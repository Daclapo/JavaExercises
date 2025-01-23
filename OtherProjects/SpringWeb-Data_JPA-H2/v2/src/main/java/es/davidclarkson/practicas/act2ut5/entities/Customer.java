package es.davidclarkson.practicas.act2ut5.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "customers")
public class Customer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "customer_id")
	private int customerId;

	@Column(name = "phone_number", nullable = true)
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
