package es.davidclarkson.practicas.act2ut5.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "products")
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "product_id")
	private int productId;

	@Column(name = "price", nullable = false)
	private double price;

	@Column(name = "stock", nullable = false)
	private int stock;

	@Column(name = "sku", nullable = false)
	private String sku;

	@Column(name = "name", nullable = false)
	private String name;

	private String description;

	@ManyToMany(mappedBy = "products")
	private List<Category> categories;
}
