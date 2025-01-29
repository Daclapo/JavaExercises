package es.davidclarkson.practicas.simulacro2ev.services;

import es.davidclarkson.practicas.simulacro2ev.entities.Task;
import es.davidclarkson.practicas.simulacro2ev.repositories.TaskRepository;
import jakarta.persistence.OneToMany;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {

/*	@Autowired
	private TaskRepository taskRepository;*/

	private final TaskRepository taskRepository;

	public TaskServiceImpl(TaskRepository taskRepository) {
		this.taskRepository = taskRepository;
	}

/*	public Page<Task> getTaskByNameOrDescription(String title, String descripion, int pageNumber, int pageSize){
		return taskRepository.findTasksByDescriptionContainingOrTitleContainingOrderByTitle(descripion, title, PageRequest.of(pageNumber, pageSize));
	}*/

	@Override
	public Page<Task> getTaskByNameOrDescription(String title, String description, int pageNumber, int pageSize){
		return taskRepository.findTasksByDescriptionContainingOrTitleContainingOrderByTitle(description, title, PageRequest.of(pageNumber, pageSize));
	}


/*	V1  @Override
	public List<Task> findRevision(String text, int pageNumber, int pageSize) {
		PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
		Page<Task> pagina = taskRepository.findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCaseOrderByTitleAsc(text, text, pageRequest);
		return pagina.toList();
	}*/

	@Override
	public List<Task> findRevision(String text, int pageNumber, int pageSize) {
		PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
		Page<Task> pagina = taskRepository.findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCaseOrderByTitleAsc(text, text, pageRequest);
		return pagina.toList();
	}
}
