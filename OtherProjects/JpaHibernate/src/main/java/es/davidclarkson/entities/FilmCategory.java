package es.davidclarkson.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "film_category")
public class FilmCategory {
	@EmbeddedId
	private FilmCategoryId id;

	@MapsId
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "film_id", nullable = false)
	private Film film;

	@ColumnDefault("current_timestamp()")
	@Column(name = "last_update", nullable = false)
	private Instant lastUpdate;

}