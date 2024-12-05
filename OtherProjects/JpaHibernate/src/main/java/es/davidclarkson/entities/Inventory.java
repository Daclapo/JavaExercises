package es.davidclarkson.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "inventory")
public class Inventory {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "inventory_id", nullable = false)
	private Integer id;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "film_id", nullable = false)
	private Film film; // La película

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "store_id", nullable = false) // Asegúrate de que el nombre de la columna coincide con la base de datos.
	private Store store; // La tienda en la que está la copia de la película

	@ColumnDefault("current_timestamp()")
	@Column(name = "last_update", nullable = false)
	private Instant lastUpdate;
}






/*
package es.davidclarkson.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "inventory")
public class Inventory {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "inventory_id", nullable = false)
	private Integer id;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "film_id", nullable = false)
	private Film film;   // La película

	@ColumnDefault("current_timestamp()")
	@Column(name = "last_update", nullable = false)
	private Instant lastUpdate;

	@ManyToOne
	@JoinColumn(name = "copy_location_store_id")
	private Store copyLocation;  //La tienda en la que está la copia de la película

//	La película
//	La tienda en la que está la copia de la película


}*/
