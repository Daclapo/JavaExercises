package es.davidclarkson.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;
import java.util.Collection;

@Getter
@Setter
@Entity
@Table(name = "customer")
public class Customer {
	@Id
	@Column(name = "customer_id", nullable = false)
	private Short id;

	@Column(name = "first_name", nullable = false, length = 45)
	private String firstName;

	@Column(name = "last_name", nullable = false, length = 45)
	private String lastName;

	@Column(name = "email", length = 50)
	private String email;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "address_id", nullable = false)
	private Address address;         //	La dirección del cliente

	@ColumnDefault("1")
	@Column(name = "active", nullable = false)
	private Boolean active = false;

	@Column(name = "create_date", nullable = false)
	private Instant createDate;

	@ColumnDefault("current_timestamp()")
	@Column(name = "last_update")
	private Instant lastUpdate;

	// --------- Clases/Columnas Añadidas ---------

	@ManyToOne
	@JoinColumn(name = "store_id")
	private Store store;                     //	La tienda a la que está asociado

	//@OneToMany
	@OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Collection<Rental> rentalHistory;    //	Los alquileres que ha realizado

	@OneToMany(mappedBy = "customer")
	private Collection<Payment> paymentHistory;  //	Los pagos que ha realizado



//	La dirección del cliente
//	La tienda a la que está asociado
//	Los alquileres que ha realizado
//	Los pagos que ha realizado

}