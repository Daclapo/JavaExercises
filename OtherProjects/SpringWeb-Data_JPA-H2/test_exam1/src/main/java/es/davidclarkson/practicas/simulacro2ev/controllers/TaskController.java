package es.davidclarkson.practicas.simulacro2ev.controllers;


import es.davidclarkson.practicas.simulacro2ev.entities.Task;
import es.davidclarkson.practicas.simulacro2ev.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/task")
public class TaskController {

	@Autowired
	private TaskService taskService;

/*	@GetMapping("find/{search}/{pageNumber}/{tamanioPagina}")
	public Page<Task> getTaskByNameOrDescription(@PathVariable String title, @PathVariable String description, @PathVariable int pageNumber, @PathVariable int tamanioPagina){
		return taskService.getTaskByNameOrDescription(title, description, pageNumber, tamanioPagina);
	}*/

	@GetMapping("find/{search}/{pageNumber}/{tamanioPagina}")
	public Page<Task> getTaskByNameOrDescription(@PathVariable("search") String search, @PathVariable("pageNumber") int pageNumber, @PathVariable("tamanioPagina") int tamanioPagina){
		return taskService.getTaskByNameOrDescription(search, search, pageNumber, tamanioPagina);
	}


}
