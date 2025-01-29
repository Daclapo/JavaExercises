package es.davidclarkson.practicas.simulacro2ev.repositories;

import es.davidclarkson.practicas.simulacro2ev.entities.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer> {

	Page<Task> findTasksByDescriptionContainingOrTitleContainingOrderByTitle(String description, String title, Pageable page);


	Page<Task> findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCaseOrderByTitleAsc(String title, String description, Pageable pageable);

}
