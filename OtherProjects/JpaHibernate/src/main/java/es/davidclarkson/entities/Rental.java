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
@Table(name = "rental")
public class Rental {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "rental_id", nullable = false)
	private Integer id;

	@Column(name = "rental_date", nullable = false)
	private Instant rentalDate;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "inventory_id", nullable = false)
	private Inventory inventory;     //	El elemento de inventario al que está asociado el alquiler

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "customer_id", nullable = false)
	private Customer customer;       //	El cliente que realizó el alquiler

	@Column(name = "return_date")
	private Instant returnDate;

	@ColumnDefault("current_timestamp()")
	@Column(name = "last_update", nullable = false)
	private Instant lastUpdate;




	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "staff_id")
	private Staff rentor;        //	El empleado que realizó el alquiler

	@OneToMany(mappedBy = "rental")
	private Collection<Payment> paymentHistory;   // Los pagos realizados asociados al alquiler


//	El empleado que realizó el alquiler
//	El elemento de inventario al que está asociado el alquiler
//	Los pagos realizados asociados al alquiler
//	El cliente que realizó el alquiler



}