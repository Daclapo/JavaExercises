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
@Table(name = "staff")
public class Staff {
	@Id
	@Column(name = "staff_id", nullable = false)
	private Byte id;

	@Column(name = "first_name", nullable = false, length = 45)
	private String firstName;

	@Column(name = "last_name", nullable = false, length = 45)
	private String lastName;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "address_id", nullable = false)
	private Address address;     //	La dirección del empleado

	@Column(name = "picture")
	private byte[] picture;

	@Column(name = "email", length = 50)
	private String email;

	@ColumnDefault("1")
	@Column(name = "active", nullable = false)
	private Boolean active = false;

	@Column(name = "username", nullable = false, length = 16)
	private String username;

	@Column(name = "password", length = 40)
	private String password;

	@ColumnDefault("current_timestamp()")
	@Column(name = "last_update", nullable = false)
	private Instant lastUpdate;



	@ManyToOne
	@JoinColumn(name = "store_id")
	private Store store;     //	La tienda a la que está asociado el empleado

	@ManyToMany
	private Collection<Rental> rentals;  //	Los alquileres que ha realizado


//	La tienda a la que está asociado el empleado
//	La dirección del empleado
//	Los alquileres que ha realizado


}