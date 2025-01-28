package es.davidclarkson.practicas.simulacro2ev.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "task_status")
public class TaskStatus {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "task_status_id", nullable = false)
	private int taskStatusId;

	@Column(name = "name")
	private String name;
}
