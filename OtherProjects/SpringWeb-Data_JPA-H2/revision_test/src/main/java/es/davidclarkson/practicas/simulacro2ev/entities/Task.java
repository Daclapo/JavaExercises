package es.davidclarkson.practicas.simulacro2ev.entities;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "task")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
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


	public int getTaskId() {
		return taskId;
	}

	public String getTitle() {
		return title;
	}

	public String getDescription() {
		return description;
	}

	public TaskCategory getTaskCategoryId() {
		return taskCategoryId;
	}

	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setTaskCategoryId(TaskCategory taskCategoryId) {
		this.taskCategoryId = taskCategoryId;
	}
}
