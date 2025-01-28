package es.davidclarkson.practicas.simulacro2ev.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "task")
public class Task {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "task_id", nullable = false)
	private int taskId;

	@Column(name = "title", nullable = false)
	private String title;

	@Column(name = "description")
	private String description;


	// FK
	@ManyToOne
	@JoinColumn(name = "task_category_id", nullable = false)
	private TaskCategory taskCategoryId;
}
