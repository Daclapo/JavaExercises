package es.davidclarkson.practicas.simulacro2ev.controllers;


import es.davidclarkson.practicas.simulacro2ev.entities.Task;
import es.davidclarkson.practicas.simulacro2ev.services.TaskServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/task")
public class TaskController {


/*
	@Autowired
	private TaskServiceImpl taskService;
*/

	private final TaskServiceImpl taskService;

	public TaskController(TaskServiceImpl taskService) {
		this.taskService = taskService;
	}


/*	@GetMapping("find/{search}/{pageNumber}/{tamanioPagina}")
	public Page<Task> getTaskByNameOrDescription(@PathVariable String title, @PathVariable String description, @PathVariable int pageNumber, @PathVariable int tamanioPagina){
		return taskService.getTaskByNameOrDescription(title, description, pageNumber, tamanioPagina);
	}*/


/* Mi Version
	@GetMapping("find/{search}/{pageNumber}/{tamanioPagina}")
	public Page<Task> getTaskByNameOrDescription(@PathVariable("search") String search, @PathVariable("pageNumber") int pageNumber, @PathVariable("tamanioPagina") int tamanioPagina){
		return taskService.getTaskByNameOrDescription(search, search, pageNumber, tamanioPagina);
	}*/

	// Revision:

/*	V1  @GetMapping("/find/{search}/{pageNumber}/{pageSize}")
	public ResponseEntity<List<Task>> findTasks(@PathVariable String text,
												@PathVariable int pageNumber,
												@PathVariable int pageSize){
		return ResponseEntity.ok(taskService.findRevision(text, pageNumber, pageSize));
	}*/

	@GetMapping("/find/{text}/{pageNumber}/{pageSize}")
	public ResponseEntity<List<Task>> findTasks(@PathVariable String text,
	                                            @PathVariable int pageNumber,
	                                            @PathVariable int pageSize) {
		try {
			List<Task> tasks = taskService.findRevision(text, pageNumber, pageSize);
			if (tasks.isEmpty()) {
				return ResponseEntity.noContent().build();
			}
			return ResponseEntity.ok(tasks);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

}
