package es.davidclarkson.practicas.simulacro2ev.repositories;

import es.davidclarkson.practicas.simulacro2ev.entities.TaskAssignment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskAssignmentRepository extends JpaRepository<TaskAssignment, Integer> {

}
