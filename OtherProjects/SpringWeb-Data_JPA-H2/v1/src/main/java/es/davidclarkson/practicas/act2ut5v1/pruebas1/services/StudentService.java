package es.davidclarkson.practicas.act2ut5v1.pruebas1.services;

import es.davidclarkson.practicas.act2ut5v1.pruebas1.entities.Student;

import java.util.List;
import java.util.Optional;

public interface StudentService {

	public List<Student> findAll();

	Optional<Student> findById(Integer id);

	List<Student> findAllPaged(int page, int pageSize);
}
