package es.davidclarkson.practicas.simulacro2ev.services;

import es.davidclarkson.practicas.simulacro2ev.entities.Task;
import es.davidclarkson.practicas.simulacro2ev.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class TaskService {

	@Autowired
	private TaskRepository taskRepository;

/*	public Page<Task> getTaskByNameOrDescription(String title, String descripion, int pageNumber, int pageSize){
		return taskRepository.findTasksByDescriptionContainingOrTitleContainingOrderByTitle(descripion, title, PageRequest.of(pageNumber, pageSize));
	}*/

	public Page<Task> getTaskByNameOrDescription(String title, String description, int pageNumber, int pageSize){
		return taskRepository.findTasksByDescriptionContainingOrTitleContainingOrderByTitle(description, title, PageRequest.of(pageNumber, pageSize));
	}

}
