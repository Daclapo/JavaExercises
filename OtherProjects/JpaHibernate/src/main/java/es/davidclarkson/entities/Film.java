package es.davidclarkson.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "film")
public class Film {
	@Id
	@Column(name = "film_id", nullable = false)
	private Short id;

	@Column(name = "title", nullable = false, length = 128)
	private String title;

	@Lob
	@Column(name = "description")
	private String description;

	@Column(name = "release_year")
	private Integer releaseYear;

	@ColumnDefault("3")
	@Column(name = "rental_duration", nullable = false)
	private Byte rentalDuration;

	@ColumnDefault("4.99")
	@Column(name = "rental_rate", nullable = false, precision = 4, scale = 2)
	private BigDecimal rentalRate;

	@Column(name = "length")
	private Short length;

	@ColumnDefault("19.99")
	@Column(name = "replacement_cost", nullable = false, precision = 5, scale = 2)
	private BigDecimal replacementCost;

	@ColumnDefault("'G'")
	@Lob
	@Column(name = "rating")
	private String rating;

	@Lob
	@Column(name = "special_features")
	private String specialFeatures;

	@ColumnDefault("current_timestamp()")
	@Column(name = "last_update", nullable = false)
	private Instant lastUpdate;

/* Lo que se ha creado automaticamente:
	@ManyToMany(mappedBy = "categories")
	private Collection<Category> films;*/
	@ManyToMany(mappedBy = "categories")
	private Collection<Category> filmCateories;  //	Colección de categorías a las que pertenece

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "language_id", nullable = false)
	private Language language;           //	El idioma de la película

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "original_language_id")
	private Language originalLanguage;   //	El idioma original de la película

	@ManyToMany()
	@JoinTable(name = "film_actor", joinColumns = {@JoinColumn(name = "actor_id")}, inverseJoinColumns = {@JoinColumn(name = "film_id")})
	private Set<Actor> filmActors = new LinkedHashSet<>();

	@OneToMany(mappedBy = "film")
	private Set<Inventory> inventories = new LinkedHashSet<>();


//	Colección de categorías a las que pertenece
//	El idioma original de la película
//	El idioma de la película


}