package es.davidclarkson.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;
import java.util.Collection;

@Getter
@Setter
@ToString
@Entity
@Table(name = "category")
public class Category {
	@Id
	@Column(name = "category_id", nullable = false)
	private Byte id;

	@Column(name = "name", nullable = false, length = 25)
	private String name;

	@ColumnDefault("current_timestamp()")
	@Column(name = "last_update", nullable = false)
	private Instant lastUpdate;

	@ManyToMany
	@JoinTable(name = "film_category", joinColumns = {@JoinColumn(name = "category_id")}, inverseJoinColumns = {@JoinColumn(name = "film_id")})
	private Collection<Film> categories;


	// Sin propiedades de navegación (añadidas)
}