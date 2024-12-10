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
@Entity
@Table(name = "country")
public class Country {
	@Id
	@Column(name = "country_id", nullable = false)
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Short id;

	@Column(name = "country", nullable = false, length = 50)
	private String name;

	@ColumnDefault("current_timestamp()")
	@Column(name = "last_update", nullable = false)
	private Instant lastUpdate;


	// Sin propiedades de navegación (añadidas)
}