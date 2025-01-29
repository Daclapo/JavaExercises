package es.davidclarkson.practicas.simulacro2ev.services;

import es.davidclarkson.practicas.simulacro2ev.entities.Task;
import org.springframework.data.domain.Page;

import java.util.List;

public interface TaskService {

	public Page<Task> getTaskByNameOrDescription(String title, String description, int pageNumber, int pageSize);

	public List<Task> findRevision(String text, int pageNumber, int pageSize);

}
