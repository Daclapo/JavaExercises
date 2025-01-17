package es.davidclarkson.practicas.act2ut5v1.v1.entities;


import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "categories")
public class Category {

	@Id
	@GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
	@Column(name = "category_id")
	private int categoryId;

	@Column(name = "name", nullable = false)
	private String name;

	private String description;

	@OneToMany(mappedBy = "category")
	private List<Product> products;
}
