package es.davidclarkson.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@ToString
@Entity
@Table(name = "city")
public class City {
	@Id
	@Column(name = "city_id", nullable = false)
	private Short id;

	@Column(name = "city", nullable = false, length = 50)
	private String name;

	// El país al que pertenece
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "country_id", nullable = false)
	private es.davidclarkson.entities.Country country;

	@ColumnDefault("current_timestamp()")
	@Column(name = "last_update", nullable = false)
	private Instant lastUpdate;

	@OneToMany(mappedBy = "city")
	private Set<Address> addresses = new LinkedHashSet<>();



}