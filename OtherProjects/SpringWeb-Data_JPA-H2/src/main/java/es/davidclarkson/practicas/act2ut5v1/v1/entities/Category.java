package es.davidclarkson.practicas.act2ut5v1.v1.entities;


import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Table(name = "categories")
public class Category {

	@Id
	@GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
	@Column(name = "category_id")
	private int categoryId;

	@Column(name = "name", nullable = false)
	private String name;

	private String description;

}
