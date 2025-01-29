package es.davidclarkson.practicas.simulacro2ev.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "task_category")
public class TaskCategory {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "task_category_id", nullable = false)
	private int taskCategoryId;

	@Column(name = "name", nullable = false)
	private String name;


	public int getTaskCategoryId() {
		return taskCategoryId;
	}

	public String getName() {
		return name;
	}

	public void setTaskCategoryId(int taskCategoryId) {
		this.taskCategoryId = taskCategoryId;
	}

	public void setName(String name) {
		this.name = name;
	}
}
