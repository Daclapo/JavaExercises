package es.davidclarkson.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "address")
public class Address {
	@Id
	@Column(name = "address_id", nullable = false)
	private Short id;

	@Column(name = "address", nullable = false, length = 50)
	private String address;

	@Column(name = "address2", length = 50)
	private String address2;

	@Column(name = "district", nullable = false, length = 20)
	private String district;

	// Ciudad de la dirección
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "city_id", nullable = false)
	private es.davidclarkson.entities.City city;

	@Column(name = "postal_code", length = 10)
	private String postalCode;

	@Column(name = "phone", nullable = false, length = 20)
	private String phone;

	@ColumnDefault("current_timestamp()")
	@Column(name = "last_update", nullable = false)
	private Instant lastUpdate;

}