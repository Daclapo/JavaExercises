package es.davidclarkson.practicas.act2ut5v1.pruebas1.repositories;

import es.davidclarkson.practicas.act2ut5v1.pruebas1.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Integer> {}
