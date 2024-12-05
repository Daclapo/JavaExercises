package es.davidclarkson.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "payment")
public class Payment {
	@Id
	@Column(name = "payment_id", nullable = false)
	private Short id;

	@ManyToOne
	@JoinColumn(name = "customer_id")
	private Customer customer;

	@ManyToOne
	@JoinColumn(name = "staff_id")
	private Staff staff;

	@ManyToOne
	@JoinColumn(name = "rental_id")
	private Rental rental;

	@Column(name = "amount", nullable = false, precision = 5, scale = 2)
	private BigDecimal amount;

	@Column(name = "payment_date", nullable = false)
	private Instant paymentDate;

	@ColumnDefault("current_timestamp()")
	@Column(name = "last_update", nullable = false)
	private Instant lastUpdate;


	// Sin propiedades de navegación

}